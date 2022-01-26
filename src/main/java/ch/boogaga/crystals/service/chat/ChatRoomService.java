package ch.boogaga.crystals.service.chat;

import ch.boogaga.crystals.ConfigData;
import ch.boogaga.crystals.model.persist.ChatMessagePrivate;
import ch.boogaga.crystals.model.persist.ChatMessagePublic;
import ch.boogaga.crystals.repository.chat.ChatPrivateRepository;
import ch.boogaga.crystals.repository.chat.ChatPublicRepository;
import ch.boogaga.crystals.to.ChatMessageTo;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;

import java.util.List;

import static ch.boogaga.crystals.ConfigData.*;
import static ch.boogaga.crystals.util.ChatMessageUtils.privateMessageFromTo;
import static ch.boogaga.crystals.util.ChatMessageUtils.publicMessageFromTo;

@Component
public class ChatRoomService {
    private final HazelcastInstance hazelcastInstance;
    private final ChatPrivateRepository privateRepository;
    private final ChatPublicRepository publicRepository;

    @Value("${firstMemberAddress}")
    private String firstMemberAddress;

    public ChatRoomService(@Qualifier("hazelcastInstance") HazelcastInstance hazelcastInstance,
                           ChatPrivateRepository privateRepository, ChatPublicRepository publicRepository) {
        this.hazelcastInstance = hazelcastInstance;
        this.privateRepository = privateRepository;
        this.publicRepository = publicRepository;
    }

    @PostConstruct
    public void initRooms() {
        if (firstMemberAddress.equals("none")
                || hazelcastInstance.getCluster().getLocalMember().getAddress().getHost().equals(firstMemberAddress)) {
            hazelcastInstance.getMap(CACHE_PRIVATE_ROOM_NAME).clear();
            hazelcastInstance.getMap(CACHE_PUBLIC_ROOM_RU_NAME).clear();
            hazelcastInstance.getMap(CACHE_PUBLIC_ROOM_EN_NAME).clear();
            fillCacheMessages(CACHE_PRIVATE_ROOM_NAME, privateRepository);
            fillCacheMessages(PUBLIC_ROOMS, publicRepository);
        }
    }

    @Transactional
    public Integer savePrivate(ChatMessageTo to, Integer recipientId) {
        Assert.notNull(to, "to must not be null");
        Assert.notNull(recipientId, "recipientId must not be null");
        final ChatMessagePrivate messagePrivate = privateRepository.save(privateMessageFromTo(to, recipientId));
        addMessageToCache(messagePrivate, CACHE_PRIVATE_ROOM_NAME);
        return messagePrivate.getId();
    }

    @Transactional
    public Integer savePublic(ChatMessageTo to, String localeId) {
        Assert.notNull(to, "to must not be null");
        Assert.notNull(localeId, "to must not be null");
        final ChatMessagePublic messagePublic = publicRepository.save(publicMessageFromTo(to, localeId));
        addMessageToCache(messagePublic, PUBLIC_ROOMS);
        return messagePublic.getId();
    }

    public List<ChatMessagePrivate> getPrivateMessagesByUserId(Integer userId) {
        Assert.notNull(userId, "userId must not be null");
        return privateRepository.getPrivateMessagesByUserId(userId);
    }

    public List<ChatMessagePublic> getPublicMessagesByLocaleId(String localeId) {
        Assert.notNull(localeId, "localeId must not be null");
        return publicRepository.findAllByLocaleId(localeId);
    }

    private <T> void fillCacheMessages(String roomName, PagingAndSortingRepository<T, Integer> repository) {
        Pageable pageable = PageRequest.of(0, ConfigData.SIZE_PAGEABLE_MESSAGES, Sort.by("created"));
        Slice<T> slice;
        while (true) {
            slice = repository.findAll(pageable);
            slice.get().forEach(cm -> addMessageToCache(cm, roomName));

            if (!slice.hasNext()) {
                break;
            }

            pageable = slice.nextPageable();
        }
    }

    private <T> void addMessageToCache(T message, String roomName) {
        if (roomName.equals(PUBLIC_ROOMS)) {
            final ChatMessagePublic cmp = (ChatMessagePublic) message;
            switch (cmp.getLocaleId()) {
                case ROOM_ID_LOCALE_RU: {
                    hazelcastInstance.getMap(CACHE_PUBLIC_ROOM_RU_NAME).putIfAbsent(cmp.getId(), cmp);
                    break;
                }
                case ROOM_ID_LOCALE_EN: {
                    hazelcastInstance.getMap(CACHE_PUBLIC_ROOM_EN_NAME).putIfAbsent(cmp.getId(), cmp);
                    break;
                }
            }
        } else {
            hazelcastInstance.getMap(roomName).putIfAbsent(((ChatMessagePrivate) message).getId(), message);
        }
    }
}

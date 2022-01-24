package ch.boogaga.crystals.service.chat;

import ch.boogaga.crystals.ConfigData;
import ch.boogaga.crystals.model.persist.ChatMessagePrivate;
import ch.boogaga.crystals.model.persist.ChatMessagePublic;
import ch.boogaga.crystals.repository.chat.ChatPrivateRepository;
import ch.boogaga.crystals.repository.chat.ChatPublicRepository;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ch.boogaga.crystals.ConfigData.PUBLIC_ROOMS;

@Component
public class ChatRoomService {
    private final HazelcastInstance hazelcastInstance;
    private final ChatPrivateRepository privateRepository;
    private final ChatPublicRepository publicRepository;

    public ChatRoomService(@Qualifier("hazelcastInstance") HazelcastInstance hazelcastInstance,
                           ChatPrivateRepository privateRepository, ChatPublicRepository publicRepository) {
        this.hazelcastInstance = hazelcastInstance;
        this.privateRepository = privateRepository;
        this.publicRepository = publicRepository;
    }

    @PostConstruct
    public void initRooms() {
        fillCacheMessages(ChatMessagePrivate::getSenderId, ConfigData.PRIVATE_SENDER_ROOMS, privateRepository);
        fillCacheMessages(ChatMessagePrivate::getRecipientId, ConfigData.PRIVATE_RECIPIENT_ROOMS, privateRepository);
        fillCacheMessages(ChatMessagePublic::getLocaleId, PUBLIC_ROOMS, publicRepository);
    }

    private <T, P> void fillCacheMessages(Function<T, P> fun, String roomName,
                                          PagingAndSortingRepository<T, P> repository) {
        Pageable pageable = PageRequest.of(0, ConfigData.SIZE_PAGEABLE_MESSAGES, Sort.by("created"));
        Slice<T> slice;
        while (true) {
            slice = repository.findAll(pageable);
            final Map<P, List<T>> map = slice.get().collect(Collectors.groupingByConcurrent(fun));
            if (roomName.equals(PUBLIC_ROOMS)) {
                hazelcastInstance.getMap(ConfigData.CACHE_CHAT_NAME).putAll(map);
            } else {
                hazelcastInstance.getMap(ConfigData.CACHE_CHAT_NAME).put(roomName, map);
            }

            if (!slice.hasNext()) {
                break;
            }

            pageable = slice.nextPageable();
        }
    }
}

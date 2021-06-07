package ch.boogaga.crystals.service;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationChannelInterceptor implements ChannelInterceptor {
    private final WebSocketAuthenticatorService service;
    private static final String LOGIN_HEADER = "login";
    private static final String PASSWORD_HEADER = "password";

    public AuthorizationChannelInterceptor(WebSocketAuthenticatorService service){
        this.service = service;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        assert accessor != null;
        if(accessor.getCommand() == StompCommand.CONNECT){
            final String username = accessor.getFirstNativeHeader(LOGIN_HEADER);
            final String password = accessor.getFirstNativeHeader(PASSWORD_HEADER);
            UsernamePasswordAuthenticationToken user = service.getAuthenticatedOrFail(username, password);
            accessor.setUser(user);
        }
        return message;
    }
}

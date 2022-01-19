package ch.boogaga.crystals;

public class ConfigData {
    // web socket
    public static final String APP_DESTINATION_PREFIX = "/crystals";
    public static final String END_POINT = APP_DESTINATION_PREFIX + "/stomp";
    public static final String LOGIN_HEADER = "login";
    public static final String PASSWORD_HEADER = "password";

    // caching
    public static final String CACHE_USERS_NAME = "users";
    public static final String CACHE_CHAT_NAME = "chat";

    // chat
    public static final String ROOM_ID_LOCALE_EN = "enEN";
    public static final String ROOM_ID_LOCALE_RU = "ruRU";
}

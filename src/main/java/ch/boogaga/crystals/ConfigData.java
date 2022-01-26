package ch.boogaga.crystals;

public class ConfigData {
    // web socket
    public static final String APP_DESTINATION_PREFIX = "/crystals";
    public static final String END_POINT = APP_DESTINATION_PREFIX + "/stomp";
    public static final String LOGIN_HEADER = "login";
    public static final String PASSWORD_HEADER = "password";

    // caching
    public static final String CACHE_USERS_NAME = "users";
    public static final String CACHE_PRIVATE_ROOM_NAME = "private_room";
    public static final String CACHE_PUBLIC_ROOM_RU_NAME = "public_room_ru";
    public static final String CACHE_PUBLIC_ROOM_EN_NAME = "public_room_en";

    // chat
    public static final int SIZE_PAGEABLE_MESSAGES = 1;
    public static final String ROOM_ID_LOCALE_EN = "enEN";
    public static final String ROOM_ID_LOCALE_RU = "ruRU";
    public static final String PUBLIC_ROOMS = "PUBLIC_ROOMS";
    public static final Integer EXPIRE_TIME_PRIVATE_MESSAGE = 30; // 30 days
    public static final Integer EXPIRE_TIME_PUBLIC_MESSAGE = 30; // 30 days
}

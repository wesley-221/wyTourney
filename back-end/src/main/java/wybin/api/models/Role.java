package wybin.api.models;

public enum Role {
    NORMAL(0),
    STREAMER(1),
    COMMENTATOR(2),
    REFEREE(4),
    MAPPICKER(8),
    MODERATOR(256),
    ADMINISTRATOR(512);

    private final Integer value;

    Role(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}

package hoversprite.common.external.enums;

public enum OrderSlot {
    SLOT_4("4:00 - 5:00"),
    SLOT_5("5:00 - 6:00"),
    SLOT_6("6:00 - 7:00"),
    SLOT_7("7:00 - 8:00"),
    SLOT_16("16:00 - 17:00"),
    SLOT_17("17:00 - 18:00");

    private final String description;

    OrderSlot(String description) {
        this.description = description;
    }

    public String toString() {
        return description;
    }
}

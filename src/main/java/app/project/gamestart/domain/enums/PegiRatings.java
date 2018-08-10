package app.project.gamestart.domain.enums;

public enum PegiRatings {
    PEGI_3(3),
    PEGI_7(7),
    PEGI_12(12),
    PEGI_16(16),
    PEGI_18(18);

    private int value;

    PegiRatings(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

package bntu.diploma.utils;

public enum OblastEnum {

    BRESTSKAYA(1L, "brestskaya"),
    VITEBSKAYA(2L, "vitebskaya"),
    GOMELSKAYA(3L, "gomelskaya"),
    GRODNENSKAYA(4L, "grodnenskaya"),
    MINSKAYA(5L, "minskaya"),
    MOGILEVSKAYA(6L, "mogilevskaya");

    private final long id;
    private final String oblastName;

    OblastEnum(long id, String oblastName) {
        this.id = id;
        this.oblastName = oblastName;
    }

    public long getId() {
        return id;
    }

    public String getOblastName() {
        return oblastName;
    }

    public static OblastEnum getByID(long id) {

        for (OblastEnum value : values()) {
            if (value.getId() == id) {
                return value;
            }
        }

        return null;
    }

    public static OblastEnum getByName(String name) {

        for (OblastEnum value : values()) {

            if (value.getOblastName().equalsIgnoreCase(name)) {

                return value;

            }
        }

        return null;
    }

}
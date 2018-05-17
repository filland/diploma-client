package bntu.diploma.utils;

public enum OblastEnum {

    brestskaya(1L),
    vitebskaya(2L),
    gomelskaya(3L),
    grodnenskaya(4L),
    minskaya(5L),
    mogilevskaya(6L);

    private final long id;

    OblastEnum(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}

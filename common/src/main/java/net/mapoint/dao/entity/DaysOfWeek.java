package net.mapoint.dao.entity;

public enum DaysOfWeek {

    MONDAY(0), TUESDAY(1), WEDNESDAY(2), THURSDAY(3), FRIDAY(4), SATURDAY(5), SUNDAY(6);

    private final int id;

    DaysOfWeek(int id) {
        this.id = id;
    }

    public static DaysOfWeek getValue(int value) {
        for (DaysOfWeek e : DaysOfWeek.values()) {
            if (e.id == value) {
                return e;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

}

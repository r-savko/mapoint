package net.mapoint.model;

public class IdHolder {

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{" +
            "id=" + id +
            '}';
    }
}

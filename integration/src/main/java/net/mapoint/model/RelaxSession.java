package net.mapoint.model;

public class RelaxSession {

    private long time;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "{" +
            "time=" + time +
            '}';
    }
}
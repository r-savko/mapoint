package net.mapoint.model;

public class RelaxWorkTime {

    private int day;
    private boolean isWeekend;
    private boolean fullTime;
    private long from;
    private long to;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public boolean isWeekend() {
        return isWeekend;
    }

    public void setWeekend(boolean weekend) {
        isWeekend = weekend;
    }

    public boolean isFullTime() {
        return fullTime;
    }

    public void setFullTime(boolean fullTime) {
        this.fullTime = fullTime;
    }

    public long getFrom() {
        return from;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public long getTo() {
        return to;
    }

    public void setTo(long to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "RelaxWorkTime{" +
            "day=" + day +
            ", isWeekend=" + isWeekend +
            ", fullTime=" + fullTime +
            ", from=" + from +
            ", to=" + to +
            '}';
    }
}

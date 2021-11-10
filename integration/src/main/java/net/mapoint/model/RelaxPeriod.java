package net.mapoint.model;

import java.util.List;

public class RelaxPeriod {

    private long from;
    private long to;
    private List<RelaxSession> sessions;

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

    public List<RelaxSession> getSessions() {
        return sessions;
    }

    public void setSessions(List<RelaxSession> sessions) {
        this.sessions = sessions;
    }

    @Override
    public String toString() {
        return "RelaxPeriod{" +
            "from=" + from +
            ", to=" + to +
            ", sessions=" + sessions +
            '}';
    }
}

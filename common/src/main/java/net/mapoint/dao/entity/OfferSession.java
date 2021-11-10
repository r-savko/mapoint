package net.mapoint.dao.entity;

import java.io.Serializable;
import java.sql.Time;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "offer_sessions")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "entity")
public class OfferSession implements Serializable, Comparable<OfferSession> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "time")
    private Time time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "OfferSession{" +
            "id=" + id +
            ", time=" + time +
            '}';
    }

    @Override
    public int compareTo(OfferSession o) {
        return time.compareTo(o.getTime());
    }
}

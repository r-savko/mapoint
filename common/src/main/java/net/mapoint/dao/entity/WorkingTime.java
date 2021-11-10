package net.mapoint.dao.entity;

import java.io.Serializable;
import java.sql.Time;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "location_working_times")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "entity")
public class WorkingTime implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "day_number")
    private DaysOfWeek dayNumber;
    @Column(name = "start_time")
    private Time startTime;
    @Column(name = "end_time")
    private Time endTime;
    @Column(name = "is_weekend")
    private boolean weekend;
    @Column(name = "full_time")
    private boolean fullTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public DaysOfWeek getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(DaysOfWeek dayNumber) {
        this.dayNumber = dayNumber;
    }

    public boolean isWeekend() {
        return weekend;
    }

    public void setWeekend(boolean weekend) {
        this.weekend = weekend;
    }

    public boolean isFullTime() {
        return fullTime;
    }

    public void setFullTime(boolean fullTime) {
        this.fullTime = fullTime;
    }

    @Override
    public String toString() {
        return "WorkingTime{" +
            "id=" + id +
            ", dayNumber=" + dayNumber +
            ", startTime=" + startTime +
            ", endTime=" + endTime +
            ", weekend=" + weekend +
            ", fullTime=" + fullTime +
            '}';
    }
}
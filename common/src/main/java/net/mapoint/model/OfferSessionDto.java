package net.mapoint.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class OfferSessionDto implements Comparable<OfferSessionDto> {

    private int id;
    @JsonFormat(pattern = "HH:mm")
    private Date time;

    public OfferSessionDto() {
    }

    public OfferSessionDto(int id, Date time) {
        this.id = id;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public int compareTo(OfferSessionDto o) {
        return time.compareTo(o.getTime());
    }
}

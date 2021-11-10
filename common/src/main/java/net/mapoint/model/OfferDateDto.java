package net.mapoint.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.Set;

public class OfferDateDto {

    private int id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private Set<OfferSessionDto> sessions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Set<OfferSessionDto> getSessions() {
        return sessions;
    }

    public void setSessions(Set<OfferSessionDto> sessions) {
        this.sessions = sessions;
    }
}

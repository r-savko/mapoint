package net.mapoint.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OfferDateResponse implements Comparable<OfferDateResponse> {

    private int id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    @JsonFormat(pattern = "HH:mm")
    private Set<Date> sessions;

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

    public Set<Date> getSessions() {
        return sessions;
    }

    public void setSessions(Set<Date> sessions) {
        this.sessions = sessions;
    }

    @Override
    public int compareTo(OfferDateResponse o) {
        return startDate.compareTo(o.startDate);
    }
}

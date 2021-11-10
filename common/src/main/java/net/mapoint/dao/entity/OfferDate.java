package net.mapoint.dao.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "offer_dates")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "entity")
public class OfferDate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;

    @NotFound(action = NotFoundAction.IGNORE)
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "offer_date_id")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "entity")
    private Set<OfferSession> sessions;

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

    public Set<OfferSession> getSessions() {
        return sessions;
    }

    public OfferDate setSessions(Set<OfferSession> sessions) {
        this.sessions = sessions;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OfferDate offerDate = (OfferDate) o;

        return new EqualsBuilder()
            .append(startDate, offerDate.startDate)
            .append(endDate, offerDate.endDate)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(startDate)
            .append(endDate)
            .toHashCode();
    }

    @Override
    public String toString() {
        return "OfferDate{" +
            "id=" + id +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            ", sessions=" + sessions +
            '}';
    }
}

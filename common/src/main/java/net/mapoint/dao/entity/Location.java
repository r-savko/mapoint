package net.mapoint.dao.entity;

import com.google.common.base.Objects;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Spatial;
import org.hibernate.search.annotations.SpatialMode;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.spatial.Coordinates;

@Entity
@Table(name = "locations")
@Indexed
@Spatial(spatialMode = SpatialMode.RANGE)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "entity")
public class Location implements Serializable, CreatedUpdatedAt, Coordinates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @DocumentId
    private int id;
    @Column(name = "relax_id")
    private long relaxId;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lng")
    private Double lng;

    @Column(name = "address")
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    private String address;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;
    @Column(name = "name")
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    private String name;
    @Column(name = "type")
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    private String type;

    @NotFound(action = NotFoundAction.IGNORE)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "location_id")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "entity")
    @IndexedEmbedded(includeEmbeddedObjectId = true)
    private Set<Fact> facts;

    @NotFound(action = NotFoundAction.IGNORE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "location_id")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "entity")
    @IndexedEmbedded(includeEmbeddedObjectId = true)
    private Set<Offer> offers;

    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @CollectionTable(name = "location_phones", joinColumns = @JoinColumn(name = "location_id"))
    @Column(name = "phone_number")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "entity")
    private Set<String> phones;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "location_id")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "entity")
    private Set<WorkingTime> workingTimes;

    @Transient
    private double distance;

    public Location() {
    }

    public Location(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    @Transient
    @Field(name = "has_approved_offers", index = Index.YES, store = Store.YES)
    public boolean getApprovedOffers() {
        return offers != null && offers.stream().anyMatch(Offer::isApproved);
    }

    @Transient
    @Field(name = "has_approved_facts", index = Index.YES, store = Store.YES)
    public boolean getApprovedFacts() {
        return facts != null && facts.stream().anyMatch(Fact::isApproved);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getRelaxId() {
        return relaxId;
    }

    public void setRelaxId(long relaxId) {
        this.relaxId = relaxId;
    }

    @Override
    public Double getLatitude() {
        return lat;
    }

    public void setLatitude(Double lat) {
        this.lat = lat;
    }

    @Override
    public Double getLongitude() {
        return lng;
    }

    public void setLongitude(Double lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Fact> getFacts() {
        return facts;
    }

    public void setFacts(Set<Fact> facts) {
        if (getFacts()!=null) {
            getFacts().clear();
            getFacts().addAll(facts);
        } else {
            this.facts = new HashSet<>(facts);
        }
    }

    public Set<Offer> getOffers() {
        return offers;
    }

    public void setOffers(Set<Offer> offers) {
        if (getOffers()!=null) {
            getOffers().clear();
            getOffers().addAll(offers);
        } else {
            this.offers = new HashSet<>(offers);
        }
    }

    public Set<String> getPhones() {
        return phones;
    }

    public void setPhones(Set<String> phones) {
        this.phones = phones;
    }

    public Set<WorkingTime> getWorkingTimes() {
        return workingTimes;
    }

    public void setWorkingTimes(Set<WorkingTime> workingTimes) {
        this.workingTimes = workingTimes;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Location{" +
            "id=" + id +
            ", relaxId=" + relaxId +
            ", lat=" + lat +
            ", lng=" + lng +
            ", address='" + address + '\'' +
            ", name='" + name + '\'' +
            ", type='" + type + '\'' +
            ", phones=" + phones +
            ", workingTimes=" + workingTimes +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Location location = (Location) o;
        return id == location.id
            && Objects.equal(lat, location.lat)
            && Objects.equal(lng, location.lng)
            && Objects.equal(address, location.address);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, lat, lng, address);
    }
}

package net.mapoint.dao.entity;

import com.google.common.collect.Sets;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

@Entity
@Table(name = "offers")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "entity")
@Indexed
public class Offer implements Serializable, CreatedUpdatedAt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @DocumentId
    private int id;
    @Column(name = "relax_id")
    private long relaxId;
    @Column(name = "text")
    private String text;

    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "offer_id")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "entity")
    private Set<OfferDate> dates;

    @Column(name = "link")
    private String link;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;
    @Column(name = "likes")
    private int likes;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "location_id")
    @Fetch(FetchMode.JOIN)
    @ContainedIn
    private Location location;


    @Column(name = "full_text")
    private String fullText;

    @Column(name = "approved")
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    private boolean approved;

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(
        name = "subcategories_offers",
        joinColumns = {@JoinColumn(name = "offer_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "subcategory_id", referencedColumnName = "id")}
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "entity")
    private Set<Subcategory> subcategories;

    @Transient
    private Type type = Type.OFFER;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public Set<Subcategory> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(Set<Subcategory> subcategories) {
        this.subcategories = subcategories;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public Set<OfferDate> getDates() {
        return dates;
    }

    public void setDates(Set<OfferDate> dates) {
        if (this.dates != null) {
            this.dates.clear();
        } else {

            this.dates = Sets.newHashSet();
        }
        if (dates != null && !dates.isEmpty()) {
            this.dates.addAll(dates);
        }

    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "Offer{" +
            "id=" + id +
            ", relaxId=" + relaxId +
            ", text='" + text + '\'' +
            ", dates=" + dates +
            ", link='" + link + '\'' +
            ", fullText='" + fullText + '\'' +
            ", type=" + type +
            '}';
    }
}

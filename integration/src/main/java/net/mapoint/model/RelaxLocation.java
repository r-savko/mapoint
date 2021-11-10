package net.mapoint.model;

import java.util.List;
import java.util.Set;

public class RelaxLocation {

    private long id;
    private String title;
    private String shortTitle;
    private Set<String> phones;
    private RelaxAddress address;
    private List<RelaxWorkTime> worktimes;
    private String type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public Set<String> getPhones() {
        return phones;
    }

    public void setPhones(Set<String> phones) {
        this.phones = phones;
    }

    public RelaxAddress getAddress() {
        return address;
    }

    public void setAddress(RelaxAddress address) {
        this.address = address;
    }

    public List<RelaxWorkTime> getWorktimes() {
        return worktimes;
    }

    public void setWorktimes(List<RelaxWorkTime> worktimes) {
        this.worktimes = worktimes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "RelaxLocation{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", shortTitle='" + shortTitle + '\'' +
            ", phones=" + phones +
            ", address=" + address +
            ", worktimes=" + worktimes +
            ", type='" + type + '\'' +
            '}';
    }
}
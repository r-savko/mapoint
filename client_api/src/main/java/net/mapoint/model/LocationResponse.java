package net.mapoint.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationResponse implements Comparable<LocationResponse> {

    private int id;
    private Double lat;
    private Double lng;
    private String address;
    private String name;
    private String type;
    private Set<FactResponse> facts;
    private Set<OfferResponse> offers;
    private Set<String> phones;
    private Set<WorkingTimeResponse> workingTimes;
    private Long distance;

    public int getId() {
        return id;
    }

    public LocationResponse setId(int id) {
        this.id = id;
        return this;
    }

    public Double getLat() {
        return lat;
    }

    public LocationResponse setLat(Double lat) {
        this.lat = lat;
        return this;
    }

    public Double getLng() {
        return lng;
    }

    public LocationResponse setLng(Double lng) {
        this.lng = lng;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public LocationResponse setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getName() {
        return name;
    }

    public LocationResponse setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public LocationResponse setType(String type) {
        this.type = type;
        return this;
    }

    public Set<FactResponse> getFacts() {
        return facts;
    }

    public LocationResponse setFacts(Set<FactResponse> facts) {
        this.facts = facts;
        return this;
    }

    public Set<OfferResponse> getOffers() {
        return offers;
    }

    public LocationResponse setOffers(Set<OfferResponse> offers) {
        this.offers = offers;
        return this;
    }

    public Set<String> getPhones() {
        return phones;
    }

    public LocationResponse setPhones(Set<String> phones) {
        this.phones = phones;
        return this;
    }

    public Set<WorkingTimeResponse> getWorkingTimes() {
        return workingTimes;
    }

    public LocationResponse setWorkingTimes(Set<WorkingTimeResponse> workingTimes) {
        this.workingTimes = workingTimes;
        return this;
    }

    public Long getDistance() {
        return distance;
    }

    public LocationResponse setDistance(Long distance) {
        this.distance = distance;
        return this;
    }

    @Override
    public int compareTo(LocationResponse o) {
        return id - o.getId();
    }
}

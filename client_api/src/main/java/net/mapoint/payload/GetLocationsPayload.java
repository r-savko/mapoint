package net.mapoint.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;

public class GetLocationsPayload {

    @JsonProperty("include_facts")
    private boolean includeFacts;
    @JsonProperty("subcategories")
    private Set<Integer> subcategories;
    @JsonProperty("time_range")
    private String timeRange;

    public Set<Integer> getSubcategories() {
        return subcategories;
    }

    public boolean isIncludeFacts() {
        return includeFacts;
    }

    public String getTimeRange() {
        return timeRange;
    }


}

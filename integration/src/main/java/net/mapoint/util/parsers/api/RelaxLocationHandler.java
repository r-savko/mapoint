package net.mapoint.util.parsers.api;

import com.google.gson.Gson;
import java.util.List;
import java.util.stream.Collectors;
import net.mapoint.model.RelaxLocation;
import net.mapoint.model.RelaxLocationHolder;
import net.mapoint.model.RelaxLocationIdsHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RelaxLocationHandler {

    private static final Logger LOGGER = LogManager.getLogger(RelaxLocationHandler.class);

    private static final String LOCATION_IDS_URL = "http://api.relax.by/v3/json/afisha/getEventsAndPlaces/rubricId/1813253/cityId/1/";
    private static final String LOCATION_INFO_URL_MASC = "http://api.relax.by/v4/json/catalog/getPlace/placeId/%s/";

    public static List<RelaxLocation> getRelaxLocations() {
        String locations = UrlReader.readUrl(LOCATION_IDS_URL);
        Gson gson = new Gson();
        RelaxLocationIdsHolder locationsList = gson.fromJson(locations, RelaxLocationIdsHolder.class);

        return locationsList.getPlaces().stream()
            .map(place -> gson.fromJson(UrlReader.readUrl(String.format(LOCATION_INFO_URL_MASC, place.getId())), RelaxLocationHolder.class))
            .map(RelaxLocationHolder::getPlace)
            .collect(Collectors.toList());
    }
}
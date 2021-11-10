package net.mapoint.util.parsers.api;

import com.google.gson.Gson;
import java.util.List;
import java.util.stream.Collectors;
import net.mapoint.model.RelaxOffer;
import net.mapoint.model.RelaxOfferHolder;
import net.mapoint.model.RelaxOfferIdsHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RelaxOfferHandler {

    private static final Logger LOGGER = LogManager.getLogger(RelaxOfferHandler.class);

    private static final String MINSK_ID = "1";
    private static final String OFFER_IDS_URL = "http://api.relax.by/v4/json/afisha/getEventsList/rubricId/1813253/cityId/1/offset/0/count/1000000000/";
    private static final String OFFER_INFO_URL_MASC = "http://api.relax.by/v3/json/afisha/getEvent/eventId/%s/cityId/%s/";

    public static List<RelaxOffer> getRelaxOffers() {
        String offers = UrlReader.readUrl(OFFER_IDS_URL);
        Gson gson = new Gson();
        RelaxOfferIdsHolder offersList = gson.fromJson(offers, RelaxOfferIdsHolder.class);

        return offersList.getEvents().stream()
            .map(event -> gson
                .fromJson(UrlReader.readUrl(String.format(OFFER_INFO_URL_MASC, event.getId(), MINSK_ID)), RelaxOfferHolder.class))
            .map(RelaxOfferHolder::getEvent)
            .collect(Collectors.toList());
    }
}
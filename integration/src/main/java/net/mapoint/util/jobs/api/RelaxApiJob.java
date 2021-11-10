package net.mapoint.util.jobs.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;
import net.mapoint.dao.LocationDao;
import net.mapoint.dao.OfferDao;
import net.mapoint.dao.entity.Location;
import net.mapoint.dao.entity.Offer;
import net.mapoint.util.converters.RelaxEntitiesConverter;
import net.mapoint.util.parsers.api.RelaxLocationHandler;
import net.mapoint.util.parsers.api.RelaxOfferHandler;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RelaxApiJob {

    private static final Logger LOGGER = LogManager.getLogger(RelaxApiJob.class);

    private static final String MODERATOR_LOCATION_SET = "Moderator has to set location for offer with relax id: %d";

    private Map<Long, Offer> offersMap;
    private Map<Long, Location> locationsMap;
    private Map<Long, Integer> offersToDelete;

    @Autowired
    private OfferDao offerDao;
    @Autowired
    private LocationDao locationDao;

    @Transactional
    //@Scheduled(cron = "0 0 5 1/1 * ?") //Every day at 5 a.m.
    @Scheduled(cron = "0 0 0/6 1/1 * ?") //Every 6 hours starting from the 00:00
    public void persistData() {
        LOGGER.log(Level.INFO, "Start Relax data uploading");
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
        offersMap = getOffersMap();
        locationsMap = getLocationsMap();
        if (MapUtils.isNotEmpty(offersMap)) {
            offersToDelete = offersMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getId()));
        }

        persistLocations();
        persistOffers();

        //Testing...
        if (MapUtils.isNotEmpty(offersToDelete)) {
            //offerDao.deleteOffers(offersToDelete);
            offersToDelete.values().forEach(id -> offerDao.delete(id));
        }

        LOGGER.log(Level.INFO, "End Relax data uploading");
    }

    private void persistLocations() {
        parseLocations().forEach(l -> {
            Location newLocation = locationDao.add(l);
            locationsMap.put(newLocation.getRelaxId(), newLocation);
        });
    }

    private void persistOffers() {
        List<Offer> offers = new ArrayList<>(parseOffers());
        if (CollectionUtils.isNotEmpty(offers)) {
            offers.forEach(o -> {
                if (!offersMap.keySet().contains(o.getRelaxId())) {
                    Offer offer = offerDao.add(o);
                    offersMap.put(o.getRelaxId(), offer);
                } else {
                    Offer detachedOffer = offersMap.get(o.getRelaxId());
                    if (!o.getDates().equals(detachedOffer.getDates())) {
                        //detachedOffer.setDates(o.getDates());
                        detachedOffer.getDates().clear();
                        detachedOffer.getDates().addAll(o.getDates());
                        offerDao.update(detachedOffer);
                    }
                    if (MapUtils.isNotEmpty(offersToDelete)) {
                        offersToDelete.remove(o.getRelaxId());
                    }
                }
            });
        }
    }

    private List<Location> parseLocations() {
        return RelaxLocationHandler.getRelaxLocations().stream()
            .filter(l -> !locationsMap.keySet().contains(l.getId()))
            .map(RelaxEntitiesConverter::toNewLocation)
            .collect(Collectors.toList());
    }

    private List<Offer> parseOffers() {
        return RelaxOfferHandler.getRelaxOffers().stream()
            .filter(o -> o.getTimetable() != null && o.getTimetable().getPlace() != null)
            .map(r -> {
                Offer offer = RelaxEntitiesConverter.toNewOffer(r);
                long locationId = r.getTimetable().getPlace().getId();
                if (locationsMap.keySet().contains(locationId)) {
                    offer.setLocation(locationsMap.get(locationId));
                } else {
                    LOGGER.info(String.format(MODERATOR_LOCATION_SET, r.getId()));
                }
                return offer;
            })
            .collect(Collectors.toList());
    }

    private Map<Long, Location> getLocationsMap() {
        Map<Long, Location> locationMap = new HashMap<>();
        List<Location> locationList = locationDao.getAll();
        locationList.forEach(l -> locationMap.put(l.getRelaxId(), l));
        return locationMap;
    }

    private Map<Long, Offer> getOffersMap() {
        Map<Long, Offer> offerMap = new HashMap<>();
        List<Offer> offerList = offerDao.getAll();
        offerList.forEach(o -> offerMap.put(o.getRelaxId(), o));
        return offerMap;
    }
}
package net.mapoint.util.converters;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;
import net.mapoint.dao.entity.DaysOfWeek;
import net.mapoint.dao.entity.Location;
import net.mapoint.dao.entity.Offer;
import net.mapoint.dao.entity.OfferDate;
import net.mapoint.dao.entity.OfferSession;
import net.mapoint.dao.entity.WorkingTime;
import net.mapoint.model.RelaxLocation;
import net.mapoint.model.RelaxOffer;
import net.mapoint.model.RelaxParam;
import net.mapoint.model.RelaxTimeTable;
import net.mapoint.model.RelaxWorkTime;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RelaxEntitiesConverter {

    private static final Logger LOGGER = LogManager.getLogger(RelaxEntitiesConverter.class);

    private static final String PARAM_DESCRIPTION = "Описание";
    private static final String MODERATOR_OFFER_DATE = "Offer dates should be set by moderator in offer with relaxId: %d";
    private static final String MODERATOR_OFFER_DATE_SESSION = "Offer dates session should be set by moderator in offer with relaxId: %d";
    private static final String MODERATOR_LOCATION_CHECK_COORDINATES = "Coordinates should be checked by moderator in offer with relaxId: %d";
    private static final long L_1000 = 1000L;

    private static SimpleDateFormat DATE_FORMAT_LOCAL = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
    private static SimpleDateFormat DATE_FORMAT_GMT = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");

    static {
        DATE_FORMAT_LOCAL.setTimeZone(TimeZone.getTimeZone("Europe/Minsk"));
        DATE_FORMAT_GMT.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public static Location toNewLocation(RelaxLocation relaxLocation) {
        Location location = new Location();
        location.setRelaxId(relaxLocation.getId());
        location.setName(relaxLocation.getTitle());
        location.setAddress(relaxLocation.getAddress().getText());
        if (relaxLocation.getAddress().getLatitude() == 0.0 && relaxLocation.getAddress().getLongitude() == 0.0) {
            LOGGER.info(String.format(MODERATOR_LOCATION_CHECK_COORDINATES, relaxLocation.getId()));
        }
        location.setLatitude(relaxLocation.getAddress().getLatitude());
        location.setLongitude(relaxLocation.getAddress().getLongitude());
        location.setPhones(relaxLocation.getPhones());
        location.setWorkingTimes(convertRelaxWorkTime(relaxLocation.getWorktimes()));
        String type = relaxLocation.getType();
        if (StringUtils.isBlank(type)) {
            type = "N/A";
        }
        location.setType(type);
        return location;
    }

    public static Offer toNewOffer(RelaxOffer relaxOffer) {
        Offer offer = new Offer();
        offer.setRelaxId(relaxOffer.getId());
        offer.setText(StringUtils.trim(relaxOffer.getTitle()));
        String fullText = relaxOffer.getParams().stream()
            .filter(p -> StringUtils.equalsIgnoreCase(p.getKey(), PARAM_DESCRIPTION))
            .map(RelaxParam::getValue)
            .findFirst().orElse(null);
        if (StringUtils.isNotBlank(fullText)) {
            offer.setFullText(StringUtils.trim(fullText));
        }
        offer.setLink(relaxOffer.getUrl());
        offer.setApproved(true);
        if (relaxOffer.getTimetable() != null) {
            offer.setDates(convertRelaxTimetable(relaxOffer.getTimetable(), relaxOffer.getId()));
        } else {
            LOGGER.info(String.format(MODERATOR_OFFER_DATE, relaxOffer.getId()));
        }
        return offer;
    }

    private static Set<WorkingTime> convertRelaxWorkTime(List<RelaxWorkTime> relaxWorkTime) {
        return relaxWorkTime.stream()
            .map(r -> {
                WorkingTime time = new WorkingTime();
                time.setDayNumber(DaysOfWeek.getValue(r.getDay()));
                time.setStartTime(new Time(r.getFrom() * L_1000));
                time.setEndTime(new Time(r.getTo() * L_1000));
                time.setWeekend(r.isWeekend());
                time.setFullTime(r.isFullTime());
                return time;
            })
            .sorted(Comparator.comparingInt(w -> w.getDayNumber().getId()))
            .collect(Collectors.toSet());
    }

    private static Set<OfferDate> convertRelaxTimetable(RelaxTimeTable relaxTimeTable, long relaxId) {
        if (CollectionUtils.isNotEmpty(relaxTimeTable.getDates())) {
            return relaxTimeTable.getDates().stream()
                .map(r -> {
                    OfferDate offerDate = new OfferDate();
                    try {
                        java.util.Date startDate = DATE_FORMAT_GMT
                            .parse(DATE_FORMAT_LOCAL.format(new java.util.Date(r.getFrom() * L_1000)));
                        java.util.Date endDate = DATE_FORMAT_GMT.parse(DATE_FORMAT_LOCAL.format(new java.util.Date(r.getTo() * L_1000)));
                        offerDate.setStartDate(new Date(startDate.getTime()));
                        offerDate.setEndDate(new Date(endDate.getTime()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (CollectionUtils.isNotEmpty(r.getSessions())) {
                        offerDate.setSessions(r.getSessions().stream()
                            .map(s -> {
                                long millisToSet = s.getTime() * L_1000;
                                Time timeToSet = new Time(millisToSet);
                                OfferSession offerSession = new OfferSession();
                                offerSession.setTime(timeToSet);
                                return offerSession;
                            })
                            .collect(Collectors.toSet()));
                    } else {
                        LOGGER.info(String.format(MODERATOR_OFFER_DATE_SESSION, relaxId));
                        offerDate.setSessions(null);
                    }
                    return offerDate;
                })
                .sorted(Comparator.comparing(OfferDate::getStartDate))
                .collect(Collectors.toSet());
        } else {
            LOGGER.info(String.format(MODERATOR_OFFER_DATE, relaxId));
            return null;
        }
    }
}
package net.mapoint.transformer;

import com.google.common.collect.Sets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import net.mapoint.model.LocationDto;
import net.mapoint.model.OfferDateDto;
import net.mapoint.model.OfferDto;
import net.mapoint.model.OfferSessionDto;
import org.springframework.stereotype.Component;

@Component
public class TimeOffersTransformer {

    public void transform(LocationDto location) {
        LocalTime localDateTime = LocalDateTime.now().plusMinutes(30).toLocalTime();

        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.set(Calendar.HOUR_OF_DAY, 0);
        todayCalendar.set(Calendar.MINUTE, 0);
        todayCalendar.set(Calendar.SECOND, 0);
        todayCalendar.set(Calendar.MILLISECOND, 0);
        Date today = todayCalendar.getTime();

        Set<OfferDto> offers = location.getOffers();
        Set<OfferDto> offersForExclude = Sets.newHashSet();

        for (OfferDto offer : offers) {
            Set<OfferDateDto> datesForExclude = Sets.newHashSet();
            for (OfferDateDto offerDate : offer.getDates()) {
                if (isBetween(offerDate.getStartDate(), offerDate.getEndDate(), today)) {
                    Set<OfferSessionDto> sessionsForExclude = offerDate.getSessions().stream()
                        .filter(s -> {
                            LocalDateTime sessionTime = convertToLocalDateTimeViaMilisecond(s.getTime());
                            return localDateTime.isAfter(sessionTime.toLocalTime());
                        }).collect(Collectors.toSet());
                    if (sessionsForExclude.size() == offerDate.getSessions().size()) {
                        datesForExclude.add(offerDate);
                    }
                }
            }
            offer.getDates().removeAll(datesForExclude);
            if (offer.getDates().isEmpty()) {
                offersForExclude.add(offer);
            }
        }

        offers.removeAll(offersForExclude);
    }

    private LocalDateTime convertToLocalDateTimeViaMilisecond(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();
    }

    private boolean isBetween(Date a, Date b, Date d) {
        return a.compareTo(d) * d.compareTo(b) >= 0;
    }

}

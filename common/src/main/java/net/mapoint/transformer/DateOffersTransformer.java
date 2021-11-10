package net.mapoint.transformer;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import net.mapoint.model.LocationDto;
import net.mapoint.model.OfferDto;
import org.springframework.stereotype.Component;

@Component
public class DateOffersTransformer implements Transformer<LocationDto> {

    private static final int HOURS_IN_DAY = 24;

    @Override
    public void transform(LocationDto location, int dayIndexFromToday) {
        Calendar futureCalendar = Calendar.getInstance();
        futureCalendar.set(Calendar.HOUR_OF_DAY, 0);
        futureCalendar.set(Calendar.MINUTE, 0);
        futureCalendar.set(Calendar.SECOND, 0);
        futureCalendar.set(Calendar.MILLISECOND, 0);
        futureCalendar.add(Calendar.HOUR, HOURS_IN_DAY * dayIndexFromToday);
        Date future = futureCalendar.getTime();

        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.set(Calendar.HOUR_OF_DAY, 0);
        todayCalendar.set(Calendar.MINUTE, 0);
        todayCalendar.set(Calendar.SECOND, 0);
        todayCalendar.set(Calendar.MILLISECOND, 0);
        Date today = todayCalendar.getTime();

        Set<OfferDto> offers = location.getOffers();

        Set<OfferDto> offersForRemoval =
            offers.stream()
                .filter(o ->
                    o.getDates().stream().noneMatch(d ->
                        (d.getStartDate() != null && d.getEndDate() != null)
                            && (isBetween(today, future, d.getStartDate()) || isBetween(today, future, d.getEndDate()))
                    )
                )
                .collect(Collectors.toSet());

        offers.removeAll(offersForRemoval);
    }

    private boolean isBetween(Date a, Date b, Date d) {
        return a.compareTo(d) * d.compareTo(b) >= 0;
    }
}

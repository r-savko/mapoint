package net.mapoint.service;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.StrictMath.sqrt;

import com.google.common.collect.Sets;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import net.mapoint.converter.FactDtoConverter;
import net.mapoint.converter.LocationDtoConverter;
import net.mapoint.converter.OfferDtoConverter;
import net.mapoint.dao.FactDao;
import net.mapoint.dao.LocationDao;
import net.mapoint.dao.OfferDao;
import net.mapoint.dao.entity.Fact;
import net.mapoint.dao.entity.Location;
import net.mapoint.dao.entity.Offer;
import net.mapoint.model.FactDto;
import net.mapoint.model.LocationDto;
import net.mapoint.model.OfferDto;
import net.mapoint.transformer.DateOffersTransformer;
import net.mapoint.transformer.TimeOffersTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LocationService implements CommonService<LocationDto> {

    private static final double EARTH_RADIUS = 6371.; // Earth radius

    @Autowired
    private LocationDao locationDao;
    @Autowired
    private FactDao factDao;
    @Autowired
    private OfferDao offerDao;
    @Autowired
    private LocationDtoConverter locationDtoConverter;
    @Autowired
    private SearchService searchService;
    @Autowired
    private DateOffersTransformer dateOffersTransformer;
    @Autowired
    private TimeOffersTransformer timeOffersTransformer;
    @Autowired
    private FactDtoConverter factDtoConverter;
    @Autowired
    private OfferDtoConverter offerDtoConverter;

    @Resource(name = "offersTransformers")
    private Map<String, Integer> offersTimeTransformerMap;

    // Distance between locations as compared with radius
    private static LocationDto setDistance(LocationDto from, LocationDto destination) {
        double dlng = deg2rad(from.getLng() - destination.getLng());
        double dlat = deg2rad(from.getLat() - destination.getLat());
        final double a = sin(dlat / 2) * sin(dlat / 2) + cos(deg2rad(destination.getLat()))
            * cos(deg2rad(from.getLat())) * sin(dlng / 2) * sin(dlng / 2);
        final double c = 2 * atan2(sqrt(a), sqrt(1 - a));
        double distance = c * EARTH_RADIUS;
        destination.setDistance(c * EARTH_RADIUS);
        destination.getFacts().forEach(f -> f.getLocation().setDistance(distance));
        destination.getOffers().forEach(o -> o.getLocation().setDistance(distance));
        return destination;
    }

    /**
     * Convert degrees to radians
     */
    private static double deg2rad(final double degree) {
        return degree * (Math.PI / 180);
    }

    public List<LocationDto> getLocationsByRadius(LocationDto fromPoint, Double radius) {
        return getLocationsWithApprovedContent(fromPoint, radius).stream()
            .map(l -> setDistance(fromPoint, l))
            .sorted(Comparator.comparingDouble(LocationDto::getDistance))
            .collect(Collectors.toList());
    }

    public List<LocationDto> getLocationsByRadiusAndSubcategories(LocationDto fromPoint, Double radius, Set<Integer> subcategoryIds,
        boolean includeFacts, String timeRangeCode) {
        List<LocationDto> locations = getLocationsByRadius(fromPoint, radius);
        if (subcategoryIds != null && !subcategoryIds.isEmpty()) {
            locations.forEach(l -> filterOffers(l, subcategoryIds));
        }
        if (!includeFacts) {
            locations.forEach(l -> l.getFacts().clear());
        }
        if (timeRangeCode != null && offersTimeTransformerMap.containsKey(timeRangeCode)) {
            Integer dayIndex = offersTimeTransformerMap.get(timeRangeCode);
            if (dayIndex != null) {
                locations.forEach(o -> dateOffersTransformer.transform(o, dayIndex));
                locations.forEach(o -> timeOffersTransformer.transform(o));
            }
        }
        return locations.stream().filter(location -> !location.getFacts().isEmpty() || !location.getOffers().isEmpty())
            .collect(Collectors.toList());

    }

    private void filterOffers(LocationDto locationDto, Set<Integer> subcategoryIds) {
        Iterator<OfferDto> iterator = locationDto.getOffers().iterator();
        while (iterator.hasNext()) {
            OfferDto offer = iterator.next();
            long count = offer.getSubcategories().stream().filter(s -> subcategoryIds.contains(s.getId())).count();
            if (count == 0) {
                iterator.remove();
            }
        }

    }

    private Set<LocationDto> getLocationsWithApprovedContent(LocationDto fromPoint, Double radius) {

        Optional<Set<LocationDto>> optional = searchService.search(fromPoint.getLat(), fromPoint.getLng(), radius);

        if (optional.isPresent()) {
            Set<LocationDto> locations = optional.get();
            optional.get().forEach(l -> {
                Set<FactDto> factSet = l.getFacts().stream().filter(FactDto::isApproved).collect(Collectors.toCollection(TreeSet::new));
                Set<OfferDto> offerSet = l.getOffers().stream().filter(OfferDto::isApproved).collect(Collectors.toCollection(TreeSet::new));
                l.setOffers(offerSet);
                l.setFacts(factSet);
            });
            return locations;
        }

        return Sets.newHashSet();

    }

    @Override
    public LocationDto add(LocationDto locationDto) {
        Location location = locationDtoConverter.toEntity(locationDto);
        return locationDtoConverter.toDto(locationDao.add(location));
    }

    public LocationDto add(LocationDto locationDto, FactDto factDto, OfferDto offerDto) {
        return null;
    }

    public void add(LocationDto locationDto, FactDto factDto) {
        Location location = locationDtoConverter.toEntity(locationDto);
        locationDao.add(location);
        Fact fact = factDtoConverter.toEntity(factDto);
        fact.setLocation(location);
        factDao.add(fact);
    }

    public void add(LocationDto locationDto, OfferDto offerDto) {
        Location location = locationDtoConverter.toEntity(locationDto);
        locationDao.add(location);
        Offer offer = offerDtoConverter.toEntity(offerDto);
        offer.setLocation(location);
        offerDao.add(offer);
    }

    @Override
    public LocationDto get(int id) {
        return locationDtoConverter.toDto(locationDao.get(id));
    }

    @Override
    public Set<LocationDto> getAll() {
        return locationDao.getAll().stream().map(c -> locationDtoConverter.toDto(c)).collect(Collectors.toCollection(TreeSet::new));

    }

    @Override
    public LocationDto update(LocationDto locationDto) {
        Location location = locationDtoConverter.toEntity(locationDto);
        return locationDtoConverter.toDto(locationDao.update(location));
    }

    @Override
    public void delete(int id) {
        locationDao.delete(id);
    }
}
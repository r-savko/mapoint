package net.mapoint.converter;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import net.mapoint.model.LocationDto;
import net.mapoint.model.LocationResponse;
import net.mapoint.model.WorkingTimeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LocationResponseDtoConverter implements Converter<LocationDto, LocationResponse> {

    @Autowired
    private FactResponseDtoConverter factResponseDtoConverter;
    @Autowired
    private OfferResponseDtoConverter offerResponseDtoConverter;
    @Autowired
    private WorkingTimeResponseDtoConverter workingTimeResponseDtoConverter;

    @Override
    public LocationResponse convert(LocationDto source) {
        LocationResponse locationResponse = new LocationResponse()
            .setId(source.getId()).setLat(source.getLat()).setLng(source.getLng())
            .setName(source.getName()).setType(source.getType())
            .setDistance(Math.round(source.getDistance() * 1000))
            .setAddress(source.getAddress());
        if (source.getFacts() != null) {
            locationResponse.setFacts(
                source.getFacts().stream().map(f -> factResponseDtoConverter.convert(f)).collect(Collectors.toCollection(TreeSet::new)));
        }
        if (source.getOffers() != null) {
            locationResponse.setOffers(
                source.getOffers().stream().map(o -> offerResponseDtoConverter.convert(o)).collect(Collectors.toCollection(TreeSet::new)));
        }
        locationResponse.setPhones(source.getPhones());
        Set<WorkingTimeDto> workingTimes = source.getWorkingTimes();
        if (workingTimes != null) {
            locationResponse.setWorkingTimes(
                workingTimes.stream().map(w -> workingTimeResponseDtoConverter.convert(w)).collect(Collectors.toCollection(TreeSet::new)));
        }

        return locationResponse;
    }


    public LocationResponse convertWithLocationInfoOnly(LocationDto source) {
        return new LocationResponse()
            .setId(source.getId()).setLat(source.getLat()).setLng(source.getLng())
            .setName(source.getName()).setType(source.getType())
            .setDistance(Math.round(source.getDistance() * 1000))
            .setAddress(source.getAddress());
    }
}

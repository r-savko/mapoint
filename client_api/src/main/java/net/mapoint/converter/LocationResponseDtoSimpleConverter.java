package net.mapoint.converter;

import net.mapoint.model.LocationDto;
import net.mapoint.model.LocationResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LocationResponseDtoSimpleConverter implements Converter<LocationDto, LocationResponse> {

    @Override
    public LocationResponse convert(LocationDto source) {
        LocationResponse locationResponse = new LocationResponse();
        locationResponse.setId(source.getId());
        locationResponse.setLat(source.getLat());
        locationResponse.setLng(source.getLng());
        locationResponse.setName(source.getName());
        locationResponse.setType(source.getType());
        locationResponse.setAddress(source.getAddress());
        locationResponse.setPhones(source.getPhones());
        if (source.getDistance() != null) {
            locationResponse.setDistance(Math.round(source.getDistance() * 1000));
        }
        return locationResponse;
    }
}

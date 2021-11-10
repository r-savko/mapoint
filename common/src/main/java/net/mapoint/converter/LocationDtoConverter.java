package net.mapoint.converter;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import net.mapoint.dao.LocationDao;
import net.mapoint.dao.entity.Location;
import net.mapoint.dao.entity.WorkingTime;
import net.mapoint.model.LocationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocationDtoConverter {

    @Autowired
    private OfferDtoConverter offerRequestConverter;
    @Autowired
    private FactDtoConverter factRequestConverter;
    @Autowired
    private LocationDao locationDao;
    @Autowired
    private WorkingTimeDtoConverter workingTimeDtoConverter;

    public LocationDto toLocationInfo(Location location) {
        LocationDto locationDto = new LocationDto();
        locationDto.setId(location.getId());
        locationDto.setLat(location.getLatitude());
        locationDto.setLng(location.getLongitude());
        locationDto.setName(location.getName());
        locationDto.setType(location.getType());
        locationDto.setAddress(location.getAddress());
        locationDto.setPhones(location.getPhones());
        return locationDto;
    }

    public LocationDto toDto(Location location) {
        LocationDto locationDto = new LocationDto();
        locationDto.setId(location.getId());
        locationDto.setLat(location.getLatitude());
        locationDto.setLng(location.getLongitude());
        locationDto.setName(location.getName());
        locationDto.setType(location.getType());
        locationDto.setAddress(location.getAddress());
        if (location.getFacts() != null) {
            locationDto.setFacts(
                location.getFacts().stream().map(f -> factRequestConverter.toDto(f)).collect(Collectors.toCollection(TreeSet::new)));
        }
        if (location.getOffers() != null) {
            locationDto.setOffers(
                location.getOffers().stream().map(o -> offerRequestConverter.toDto(o)).collect(Collectors.toCollection(TreeSet::new)));
        }
        locationDto.setPhones(location.getPhones());
        Set<WorkingTime> workingTimes = location.getWorkingTimes();
        if (workingTimes != null) {
            locationDto.setWorkingTimes(
                workingTimes.stream().map(w -> workingTimeDtoConverter.toDto(w)).collect(Collectors.toCollection(TreeSet::new)));
        }

        return locationDto;
    }

    public Location toEntity(LocationDto dto) {
        Location location = (dto.getId() != 0) ? locationDao.get(dto.getId()) : new Location();
        location.setLatitude(dto.getLat());
        location.setLongitude(dto.getLng());
        location.setAddress(dto.getAddress());
        location.setName(dto.getName());
        Set<WorkingTime> workingTimes = location.getWorkingTimes();
        if (workingTimes != null) {
            location.setWorkingTimes(dto.getWorkingTimes().stream().map(w -> workingTimeDtoConverter.toEntity(w))
                .collect(Collectors.toCollection(TreeSet::new)));
        }
        if (dto.getFacts() != null) {
            location.setFacts(dto.getFacts().stream().map(f -> factRequestConverter.toEntity(f)).collect(Collectors.toSet()));
        }
        if (dto.getOffers() != null) {
            location.setOffers(dto.getOffers().stream().map(o -> offerRequestConverter.toEntity(o)).collect(Collectors.toSet()));
        }
        location.setPhones(dto.getPhones());

        return location;
    }

    public Location toNewEntity(LocationDto dto) {
        Location location = new Location();
        location.setLatitude(dto.getLat());
        location.setLongitude(dto.getLng());
        location.setAddress(dto.getAddress());
        location.setName(dto.getName());
        location.setPhones(dto.getPhones());
        return location;
    }
}

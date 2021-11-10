package net.mapoint.service;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import net.mapoint.converter.LocationDtoConverter;
import net.mapoint.converter.OfferDtoConverter;
import net.mapoint.dao.LocationDao;
import net.mapoint.dao.OfferDao;
import net.mapoint.dao.entity.Location;
import net.mapoint.dao.entity.Offer;
import net.mapoint.model.OfferDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OfferService implements CommonService<OfferDto> {

    @Autowired
    private OfferDao offerDao;
    @Autowired
    private LocationDao locationDao;
    @Autowired
    private OfferDtoConverter offerDtoConverter;
    @Autowired
    private LocationDtoConverter locationDtoConverter;

    public void updateStatus(int id, boolean status) {
        Offer offer = offerDao.get(id);
        offer.setApproved(status);
        offerDao.update(offer);
    }

    @Override
    public OfferDto add(OfferDto offerDto) {

        Offer offer = offerDtoConverter.toEntity(offerDto);

        if (offer.getLocation().getId() == 0) {
            Location location = locationDtoConverter.toEntity(offerDto.getLocation());
            location = locationDao.add(location);
            offer.setLocation(location);
        }

        return offerDtoConverter.toDto(offerDao.add(offer));
    }

    @Override
    public OfferDto get(int id) {
        return offerDtoConverter.toDto(offerDao.get(id));
    }

    @Override
    public Set<OfferDto> getAll() {
        return offerDao.getAll().stream().map(c -> offerDtoConverter.toDto(c)).collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public OfferDto update(OfferDto offerDto) {
        Offer offer = offerDtoConverter.toEntity(offerDto);
        return offerDtoConverter.toDto(offerDao.update(offer));
    }

    @Override
    public void delete(int id) {
        offerDao.delete(id);
    }

    public void likeOffer(int id) {
        offerDao.like(id);
    }
}
package net.mapoint.converter;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import net.mapoint.dao.LocationDao;
import net.mapoint.dao.OfferDao;
import net.mapoint.dao.entity.Location;
import net.mapoint.dao.entity.Offer;
import net.mapoint.dao.entity.OfferDate;
import net.mapoint.model.OfferDateDto;
import net.mapoint.model.OfferDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OfferDtoConverter {

    @Autowired
    private OfferDao offerDao;
    @Autowired
    private LocationDao locationDao;
    @Autowired
    private LocationDtoConverter locationDtoConverter;
    @Autowired
    private SubcategoryDtoConverter subcategoryDtoConverter;
    @Autowired
    private OfferDateDtoConverter offerDateDtoConverter;

    public Offer toEntity(OfferDto request) {
        Offer offer = request.getId() == 0 ? new Offer() : offerDao.get(request.getId());
        offer.setFullText(request.getFullText());
        offer.setText(request.getText());
        if (request.getSubcategories() != null) {
            offer.setSubcategories(
                request.getSubcategories().stream().map(subcategoryDtoConverter::toEntity).collect(Collectors.toCollection(TreeSet::new)));
        }
        offer.setLink(request.getLink());
        offer.setApproved(request.isApproved());
        Set<OfferDateDto> offerDates = request.getDates();
        if (offerDates != null) {
            offer.setDates(offerDates.stream()
                .map(offerDateDtoConverter::toEntity)
                .collect(Collectors.toSet()));
        }

        if (request.getLocation()!=null) {
            if (request.getLocation().getId() != 0) {
                offer.setLocation(locationDao.get(request.getLocation().getId()));
            } else {
                offer.setLocation(locationDtoConverter.toNewEntity(request.getLocation()));
            }
        }

        return offer;

    }

    public OfferDto toDto(Offer offer) {
        Location location = offer.getLocation();
        OfferDto dto = new OfferDto();
        dto.setLocation(locationDtoConverter.toLocationInfo(location));
        dto.setId(offer.getId());
        dto.setLink(offer.getLink());
        dto.setText(offer.getText());
        dto.setFullText(offer.getFullText());
        if (offer.getSubcategories() != null) {
            dto.setSubcategories(offer.getSubcategories().stream().map(subcategoryDtoConverter::toDto)
                .sorted()
                .collect(Collectors.toList()));
        }
        dto.setApproved(offer.isApproved());

        Set<OfferDate> offerDates = offer.getDates();
        if (offerDates != null) {
            dto.setDates(offerDates.stream()
                .map(offerDateDtoConverter::toDto)
                .collect(Collectors.toSet()));
        }
        dto.setLikes(offer.getLikes());

        return dto;
    }

}

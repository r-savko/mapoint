package net.mapoint.converter;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import net.mapoint.model.LocationDto;
import net.mapoint.model.OfferDateDto;
import net.mapoint.model.OfferDto;
import net.mapoint.model.OfferResponse;
import net.mapoint.model.SubcategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OfferResponseDtoConverter implements Converter<OfferDto, OfferResponse> {

    @Autowired
    private LocationResponseDtoSimpleConverter responseDtoSimpleConverter;
    @Autowired
    private OfferDateResponseDtoConverter offerDateResponseDtoConverter;

    @Override
    public OfferResponse convert(OfferDto source) {
        LocationDto location = source.getLocation();
        OfferResponse dto = new OfferResponse();
        dto.setLocation(responseDtoSimpleConverter.convert(location));
        dto.setId(source.getId());
        dto.setLink(source.getLink());
        dto.setText(source.getText());
        dto.setFullText(source.getFullText());
        if (source.getSubcategories() != null) {
            dto.setSubcategories(source.getSubcategories().stream()
                .map(SubcategoryDto::getName)
                .collect(Collectors.toList()));
        }

        Set<OfferDateDto> offerDates = source.getDates();
        if (offerDates != null) {
            dto.setDates(offerDates.stream()
                .map(offerDateResponseDtoConverter::convert)
                .collect(Collectors.toCollection(TreeSet::new)));
        }
        dto.setLikes(source.getLikes());

        return dto;
    }
}

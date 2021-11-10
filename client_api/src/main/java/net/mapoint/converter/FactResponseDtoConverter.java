package net.mapoint.converter;

import net.mapoint.model.FactDto;
import net.mapoint.model.FactResponse;
import net.mapoint.model.LocationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FactResponseDtoConverter implements Converter<FactDto, FactResponse> {

    @Autowired
    private LocationResponseDtoSimpleConverter locationResponseDtoSimpleConverter;

    @Override
    public FactResponse convert(FactDto source) {
        LocationDto location = source.getLocation();
        FactResponse factResponse = new FactResponse();
        factResponse.setLocation(locationResponseDtoSimpleConverter.convert(location));
        factResponse.setLink(source.getLink());
        factResponse.setId(source.getId());
        factResponse.setText(source.getText());
        factResponse.setLikes(source.getLikes());
        return factResponse;
    }
}


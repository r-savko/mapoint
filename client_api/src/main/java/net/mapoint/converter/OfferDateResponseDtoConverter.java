package net.mapoint.converter;

import com.google.common.collect.Sets;
import java.util.Date;
import java.util.Set;
import net.mapoint.model.OfferDateDto;
import net.mapoint.model.OfferDateResponse;
import net.mapoint.model.OfferSessionDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OfferDateResponseDtoConverter implements Converter<OfferDateDto, OfferDateResponse> {

    @Override
    public OfferDateResponse convert(OfferDateDto source) {
        OfferDateResponse offerDateDto = new OfferDateResponse();
        offerDateDto.setId(source.getId());
        offerDateDto.setStartDate(source.getStartDate());
        offerDateDto.setEndDate(source.getEndDate());
        Set<Date> sessions = Sets.newTreeSet();
        for (OfferSessionDto session : source.getSessions()) {
            sessions.add(session.getTime());
        }
        offerDateDto.setSessions(sessions);
        return offerDateDto;
    }
}

package net.mapoint.converter;

import com.google.common.collect.Sets;
import java.sql.Time;
import java.util.Date;
import java.util.Set;
import net.mapoint.dao.entity.OfferDate;
import net.mapoint.dao.entity.OfferSession;
import net.mapoint.model.OfferDateDto;
import net.mapoint.model.OfferSessionDto;
import org.springframework.stereotype.Component;

@Component
public class OfferDateDtoConverter {

    OfferDate toEntity(OfferDateDto dateDto) {
        OfferDate offerDate = new OfferDate();
        offerDate.setId(dateDto.getId());
        if (dateDto.getStartDate() != null) {
            offerDate.setStartDate(new java.sql.Date(dateDto.getStartDate().getTime()));
        }
        if (dateDto.getEndDate() != null) {
            offerDate.setEndDate(new java.sql.Date(dateDto.getEndDate().getTime()));
        }
        Set<OfferSession> sessions = Sets.newTreeSet();
        for (OfferSessionDto sessionDto : dateDto.getSessions()) {
            OfferSession session = new OfferSession();
            session.setId(sessionDto.getId());
            session.setTime(new Time(sessionDto.getTime().getTime()));
            sessions.add(session);
        }
        offerDate.setSessions(sessions);
        return offerDate;
    }

    OfferDateDto toDto(OfferDate date) {
        OfferDateDto offerDateDto = new OfferDateDto();
        offerDateDto.setId(date.getId());
        offerDateDto.setStartDate(date.getStartDate());
        offerDateDto.setEndDate(date.getEndDate());
        Set<OfferSessionDto> sessions = Sets.newTreeSet();
        for (OfferSession session : date.getSessions()) {
            OfferSessionDto sessionDto = new OfferSessionDto(session.getId(), new Date(session.getTime().getTime()));
            sessions.add(sessionDto);
        }
        offerDateDto.setSessions(sessions);
        return offerDateDto;
    }

}

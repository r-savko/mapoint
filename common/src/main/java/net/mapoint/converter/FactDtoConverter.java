package net.mapoint.converter;

import net.mapoint.dao.FactDao;
import net.mapoint.dao.LocationDao;
import net.mapoint.dao.entity.Fact;
import net.mapoint.dao.entity.Location;
import net.mapoint.model.FactDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FactDtoConverter {

    @Autowired
    private FactDao factDao;
    @Autowired
    private LocationDao locationDao;
    @Autowired
    private LocationDtoConverter locationDtoConverter;

    public Fact toEntity(FactDto request) {
        Fact fact = request.getId() == 0 ? new Fact() : factDao.get(request.getId());
        fact.setLink(request.getLink());
        fact.setText(request.getText());
        fact.setApproved(request.isApproved());
        if (request.getLocation()!=null && request.getLocation().getId() != 0) {
            fact.setLocation(locationDao.get(request.getLocation().getId()));
        } else {
            if (request.getLocation()!=null) {
                fact.setLocation(locationDtoConverter.toNewEntity(request.getLocation()));
            }
        }
        return fact;
    }

    public Fact toNewEntity(FactDto request) {
        Fact fact = new Fact();
        fact.setLink(request.getLink());
        fact.setText(request.getText());
        fact.setLocation(locationDao.get(request.getLocation().getId()));
        fact.setApproved(request.isApproved());
        return fact;
    }

    public FactDto toDto(Fact fact) {
        Location location = fact.getLocation();
        FactDto factDto = new FactDto();
        factDto.setLocation(locationDtoConverter.toLocationInfo(location));
        factDto.setLink(fact.getLink());
        factDto.setId(fact.getId());
        factDto.setText(fact.getText());
        factDto.setApproved(fact.isApproved());
        factDto.setLikes(fact.getLikes());
        return factDto;
    }

}

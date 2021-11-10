package net.mapoint.service;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import net.mapoint.converter.FactDtoConverter;
import net.mapoint.converter.LocationDtoConverter;
import net.mapoint.dao.FactDao;
import net.mapoint.dao.LocationDao;
import net.mapoint.dao.entity.Fact;
import net.mapoint.dao.entity.Location;
import net.mapoint.model.FactDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FactService implements CommonService<FactDto> {

    @Autowired
    private LocationDao locationDao;
    @Autowired
    private FactDao factDao;
    @Autowired
    private FactDtoConverter factDtoConverter;
    @Autowired
    private LocationDtoConverter locationDtoConverter;

    @Override
    public FactDto add(FactDto factDto) {
        Fact fact = factDtoConverter.toEntity(factDto);

        if (fact.getLocation().getId() == 0) {
            Location location = locationDtoConverter.toEntity(factDto.getLocation());
            location = locationDao.add(location);
            fact.setLocation(location);
        }

        return factDtoConverter.toDto(factDao.add(fact));
    }

    @Override
    public FactDto get(int id) {
        return factDtoConverter.toDto(factDao.get(id));
    }

    @Override
    public Set<FactDto> getAll() {
        return factDao.getAll().stream().map(c -> factDtoConverter.toDto(c)).collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public FactDto update(FactDto factDto) {
        Fact fact = factDtoConverter.toEntity(factDto);
        return factDtoConverter.toDto(factDao.update(fact));
    }

    @Override
    public void delete(int id) {
        factDao.delete(id);
    }

    public List<FactDto> getAllFactsOrderByApproved() {
        return factDao.getAllFactsOrderByApproved().stream().map(c -> factDtoConverter.toDto(c)).collect(Collectors.toList());
    }

    public void updateStatus(int id, boolean status) {
        Fact fact = factDao.get(id);
        fact.setApproved(status);
        factDao.update(fact);
    }

    public void likeFact(int id) {
        factDao.like(id);
    }
}
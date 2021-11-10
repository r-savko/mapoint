package net.mapoint.service;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import net.mapoint.converter.SubcategoryDtoConverter;
import net.mapoint.dao.SubcategoryDao;
import net.mapoint.dao.entity.Subcategory;
import net.mapoint.model.SubcategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SubcategoryService implements CommonService<SubcategoryDto> {

    @Autowired
    private SubcategoryDao subcategoryDao;
    @Autowired
    private SubcategoryDtoConverter subcategoryDtoConverter;

    @Override
    public SubcategoryDto add(SubcategoryDto subcategoryDto) {
        Subcategory subcategory = subcategoryDtoConverter.toNewEntity(subcategoryDto);
        return subcategoryDtoConverter.toDto(subcategoryDao.add(subcategory));
    }

    @Override
    public SubcategoryDto get(int id) {
        return subcategoryDtoConverter.toDto(subcategoryDao.get(id));

    }

    @Override
    public Set<SubcategoryDto> getAll() {
        return subcategoryDao.getAll().stream().map(c -> subcategoryDtoConverter.toDto(c)).collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public SubcategoryDto update(SubcategoryDto subcategoryDto) {
        Subcategory subcategory = subcategoryDtoConverter.toEntity(subcategoryDto);
        return subcategoryDtoConverter.toDto(subcategoryDao.update(subcategory));
    }

    @Override
    public void delete(int id) {
        subcategoryDao.delete(id);
    }
}
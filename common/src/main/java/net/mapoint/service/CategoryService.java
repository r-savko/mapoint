package net.mapoint.service;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import net.mapoint.converter.CategoryDtoConverter;
import net.mapoint.dao.CategoryDao;
import net.mapoint.dao.entity.Category;
import net.mapoint.model.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryService implements CommonService<CategoryDto> {

    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private CategoryDtoConverter categoryDtoConverter;

    @Override
    public CategoryDto add(CategoryDto categoryDto) {
        Category category = categoryDtoConverter.toNewEntity(categoryDto);
        return categoryDtoConverter.toDto(categoryDao.add(category));
    }

    @Override
    public CategoryDto get(int id) {
        return categoryDtoConverter.toDto(categoryDao.get(id));
    }

    @Override
    public Set<CategoryDto> getAll() {
        return categoryDao.getAll().stream().map(c -> categoryDtoConverter.toDto(c)).collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto) {
        Category category = categoryDtoConverter.toEntity(categoryDto);
        return categoryDtoConverter.toDto(categoryDao.update(category));
    }

    @Override
    public void delete(int id) {
        categoryDao.delete(id);
    }
}
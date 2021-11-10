package net.mapoint.converter;

import java.util.stream.Collectors;
import net.mapoint.dao.CategoryDao;
import net.mapoint.dao.entity.Category;
import net.mapoint.model.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryDtoConverter {

    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private SubcategoryDtoConverter subcategoryDtoConverter;

    public CategoryDto toDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setColor(category.getColor());
        dto.setIcon(category.getIcon());
        if (category.getSubcategories() != null) {
            dto.setSubcategories(
                category.getSubcategories().stream().map(s -> subcategoryDtoConverter.toDto(s))
                    .sorted()
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    public Category toNewEntity(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setColor(categoryDto.getColor());
        category.setIcon(categoryDto.getIcon());
        return category;
    }

    public Category toEntity(CategoryDto request) {
        Category category = categoryDao.get(request.getId());
        category.setName(request.getName());
        category.setColor(request.getColor());
        category.setIcon(request.getIcon());
        return category;
    }

}

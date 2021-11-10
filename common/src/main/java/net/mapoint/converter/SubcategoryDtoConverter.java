package net.mapoint.converter;

import net.mapoint.dao.SubcategoryDao;
import net.mapoint.dao.entity.Subcategory;
import net.mapoint.model.SubcategoryDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubcategoryDtoConverter {

    @Autowired
    private SubcategoryDao subcategoryDao;

    public SubcategoryDto toDto(Subcategory subcategory) {
        SubcategoryDto dto = new SubcategoryDto();
        dto.setId(subcategory.getId());
        dto.setName(subcategory.getName());
        dto.setCategoryId(subcategory.getCategoryId());
        return dto;
    }

    public Subcategory toEntity(SubcategoryDto request) {
        Subcategory subcategory = subcategoryDao.get(request.getId());
        if (StringUtils.isNotEmpty(request.getName())) {
            subcategory.setName(request.getName());
        }
        return subcategory;
    }

    public Subcategory toNewEntity(SubcategoryDto request) {
        Subcategory subcategory = new Subcategory();
        subcategory.setName(request.getName());
        subcategory.setCategoryId(request.getCategoryId());
        return subcategory;
    }


}

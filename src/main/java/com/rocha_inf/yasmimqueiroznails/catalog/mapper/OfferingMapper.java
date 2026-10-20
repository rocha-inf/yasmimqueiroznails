package com.rocha_inf.yasmimqueiroznails.catalog.mapper;

import com.rocha_inf.yasmimqueiroznails.catalog.dto.request.OfferingRequest;
import com.rocha_inf.yasmimqueiroznails.catalog.dto.response.OfferingResponse;
import com.rocha_inf.yasmimqueiroznails.catalog.entity.Category;
import com.rocha_inf.yasmimqueiroznails.catalog.entity.Offering;
import com.rocha_inf.yasmimqueiroznails.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring")
public interface OfferingMapper {

    @Mappings({
            @Mapping(target = "category", source = "category"),
            @Mapping(target = "createdBy", source = "createdBy"),
            @Mapping(target = "name", source = "request.name"),
            @Mapping(target = "description", source = "request.description"),
    })
    Offering toEntity(OfferingRequest request, Category category, User createdBy);

    @Mapping(target = "categoryId", source = "category.id")
    OfferingResponse toResponse(Offering offering);

}

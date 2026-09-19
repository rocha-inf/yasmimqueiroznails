package com.rocha_inf.yasmimqueiroznails.catalog.mapper;

import com.rocha_inf.yasmimqueiroznails.catalog.dto.request.OfferingRequest;
import com.rocha_inf.yasmimqueiroznails.catalog.dto.response.OfferingResponse;
import com.rocha_inf.yasmimqueiroznails.catalog.entity.Category;
import com.rocha_inf.yasmimqueiroznails.catalog.entity.Offering;
import com.rocha_inf.yasmimqueiroznails.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;


@Mapper(componentModel = "spring")
public interface OfferingMapper {


    default Offering toEntity(OfferingRequest request, Category category, User createdBy){
        if (request == null || category == null || createdBy == null) {
            return null;
        }

        return new Offering(createdBy, category, request.name(), request.description(), request.price(), request.durationMinutes());
    }

    @Mapping(target = "categoryId", source = "category.id")
    OfferingResponse toResponse(Offering offering);
}

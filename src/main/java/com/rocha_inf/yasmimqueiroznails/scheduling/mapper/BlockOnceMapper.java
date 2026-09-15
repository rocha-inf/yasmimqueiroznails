package com.rocha_inf.yasmimqueiroznails.scheduling.mapper;


import com.rocha_inf.yasmimqueiroznails.scheduling.dto.request.BlockOnceRequest;
import com.rocha_inf.yasmimqueiroznails.scheduling.dto.response.BlockOnceResponse;
import com.rocha_inf.yasmimqueiroznails.scheduling.entity.BlockOnce;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BlockOnceMapper {

    BlockOnce toEntity(BlockOnceRequest blockOnceRequest);
    BlockOnceResponse toResponse(BlockOnce blockOnce);
}

package com.rocha_inf.yasmimqueiroznails.block.mapper;


import com.rocha_inf.yasmimqueiroznails.block.dto.request.BlockRequest;
import com.rocha_inf.yasmimqueiroznails.block.dto.response.BlockResponse;
import com.rocha_inf.yasmimqueiroznails.block.entity.Block;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BlockMapper {

    Block toEntity(BlockRequest blockRequest);
    BlockResponse toResponse(Block block);
}

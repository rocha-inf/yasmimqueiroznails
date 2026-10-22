package com.rocha_inf.yasmimqueiroznails.block.mapper;


import com.rocha_inf.yasmimqueiroznails.block.dto.request.BlockRequest;
import com.rocha_inf.yasmimqueiroznails.block.dto.request.BlockUpdateRequest;
import com.rocha_inf.yasmimqueiroznails.block.dto.response.BlockResponse;
import com.rocha_inf.yasmimqueiroznails.block.entity.Block;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BlockMapper {

    Block toEntity(BlockRequest blockRequest);
    Block toEntity(BlockUpdateRequest blockUpdateRequest);
    BlockResponse toResponse(Block block);
    List<BlockResponse> toResponseList(List<Block> blocks);

}

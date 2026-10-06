package com.rocha_inf.yasmimqueiroznails.block.service;

import com.rocha_inf.yasmimqueiroznails.block.dto.request.BlockRequest;
import com.rocha_inf.yasmimqueiroznails.block.dto.response.BlockResponse;
import com.rocha_inf.yasmimqueiroznails.block.entity.Block;
import com.rocha_inf.yasmimqueiroznails.block.exceptions.BlockPeriodConflictException;
import com.rocha_inf.yasmimqueiroznails.block.exceptions.BlockPeriodTooShortException;
import com.rocha_inf.yasmimqueiroznails.block.exceptions.BlockStartInPastException;
import com.rocha_inf.yasmimqueiroznails.block.exceptions.InvalidBlockPeriodException;
import com.rocha_inf.yasmimqueiroznails.block.mapper.BlockMapper;
import com.rocha_inf.yasmimqueiroznails.block.repository.BlockRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BlockService {

    private final BlockRepository blockRepository;
    private final BlockMapper blockMapper;

    public BlockService(BlockRepository blockRepository, BlockMapper blockMapper) {
        this.blockRepository = blockRepository;
        this.blockMapper = blockMapper;
    }

    public BlockResponse create(BlockRequest request){

        validatePeriod(request.startsAt(), request.endsAt());
        validateConflict(request.startsAt(), request.endsAt());

        Block block = blockMapper.toEntity(request);
        Block blockSaved = blockRepository.save(block);

        return blockMapper.toResponse(blockSaved);

    }

    private void validatePeriod(LocalDateTime startsAt, LocalDateTime endsAt){

        if (startsAt.isBefore(LocalDateTime.now())) {
            throw new BlockStartInPastException("A data e hora do início do bloqueio não podem estar no passado");
        }

        if (!startsAt.isBefore(endsAt)) {
            throw new InvalidBlockPeriodException("A data e hora do início do bloqueio devem ser anteriores à data e hora do fim do bloqueio");
        }

        if (endsAt.isBefore(startsAt.plusMinutes(5))) {
            throw new BlockPeriodTooShortException("O bloqueio deve ter duração mínima de 5 minutos");
        }
    }

    private void validateConflict(LocalDateTime startsAt, LocalDateTime endsAt){

        if (blockRepository.existsOverLapping(startsAt, endsAt)){
            throw new BlockPeriodConflictException("Já existe um bloqueio no intervalo informado");
        }

    }

}

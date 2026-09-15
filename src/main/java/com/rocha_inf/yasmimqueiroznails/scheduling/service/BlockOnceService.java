package com.rocha_inf.yasmimqueiroznails.scheduling.service;

import com.rocha_inf.yasmimqueiroznails.scheduling.dto.request.BlockOnceRequest;
import com.rocha_inf.yasmimqueiroznails.scheduling.dto.response.BlockOnceResponse;
import com.rocha_inf.yasmimqueiroznails.scheduling.entity.BlockOnce;
import com.rocha_inf.yasmimqueiroznails.scheduling.exceptions.BlockPeriodConflictException;
import com.rocha_inf.yasmimqueiroznails.scheduling.exceptions.BlockPeriodTooShortException;
import com.rocha_inf.yasmimqueiroznails.scheduling.exceptions.BlockStartInPastException;
import com.rocha_inf.yasmimqueiroznails.scheduling.exceptions.InvalidBlockPeriodException;
import com.rocha_inf.yasmimqueiroznails.scheduling.mapper.BlockOnceMapper;
import com.rocha_inf.yasmimqueiroznails.scheduling.repository.BlockOnceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BlockOnceService {

    private final BlockOnceRepository blockOnceRepository;
    private final BlockOnceMapper blockOnceMapper;

    public BlockOnceService(BlockOnceRepository blockOnceRepository, BlockOnceMapper blockOnceMapper) {
        this.blockOnceRepository = blockOnceRepository;
        this.blockOnceMapper = blockOnceMapper;
    }

    public BlockOnceResponse create(BlockOnceRequest request){

        validatePeriod(request.startsAt(), request.endsAt());
        validateConflict(request.startsAt(), request.endsAt());

        BlockOnce blockOnce = blockOnceMapper.toEntity(request);
        BlockOnce blockOnceSaved = blockOnceRepository.save(blockOnce);

        return blockOnceMapper.toResponse(blockOnceSaved);

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

        if (blockOnceRepository.existsOverLapping(startsAt, endsAt)){
            throw new BlockPeriodConflictException("Já existe um bloqueio no intervalo informado");
        }

    }

}

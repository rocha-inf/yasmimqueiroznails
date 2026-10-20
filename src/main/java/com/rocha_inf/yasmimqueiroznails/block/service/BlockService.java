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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BlockService {

    private final BlockRepository repository;
    private final BlockMapper mapper;

    public BlockResponse create(BlockRequest request){

        this.validate(request);

        Block block = mapper.toEntity(request);
        Block blockSaved = repository.save(block);

        return mapper.toResponse(blockSaved);

    }

    private void validate(BlockRequest request){
        if (request.startsAt().isBefore(LocalDateTime.now())) {
            throw new BlockStartInPastException("A data e hora do início do bloqueio não podem estar no passado");
        }

        if (!request.startsAt().isBefore(request.endsAt())) {
            throw new InvalidBlockPeriodException("A data e hora do início do bloqueio devem ser anteriores à data e hora do fim do bloqueio");
        }

        if (request.endsAt().isBefore(request.startsAt().plusMinutes(5))) {
            throw new BlockPeriodTooShortException("O bloqueio deve ter duração mínima de 5 minutos");
        }

        if (repository.existsOverlapping(request.startsAt(), request.endsAt())){
            throw new BlockPeriodConflictException("Já existe um bloqueio no intervalo informado");
        }
    }

}

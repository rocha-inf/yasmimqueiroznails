package com.rocha_inf.yasmimqueiroznails.block.service;

import com.rocha_inf.yasmimqueiroznails.block.dto.request.BlockRequest;
import com.rocha_inf.yasmimqueiroznails.block.dto.request.BlockUpdateRequest;
import com.rocha_inf.yasmimqueiroznails.block.dto.response.BlockResponse;
import com.rocha_inf.yasmimqueiroznails.block.entity.Block;
import com.rocha_inf.yasmimqueiroznails.block.exceptions.*;
import com.rocha_inf.yasmimqueiroznails.block.mapper.BlockMapper;
import com.rocha_inf.yasmimqueiroznails.block.repository.BlockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BlockService {

    private final BlockRepository repository;
    private final BlockMapper mapper;

    public BlockResponse create(BlockRequest request){

        this.validateCreate(request);
        Block block = mapper.toEntity(request);

        return mapper.toResponse(repository.save(block));

    }

    public Page<BlockResponse> findAll(Pageable pageable){
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    public BlockResponse update(UUID id, BlockUpdateRequest request){

        Block block = repository.findById(id).orElseThrow(() -> new BlockNotFoundException("Bloqueio não encontrado"));
        this.validateUpdate(block, request);

        if (request.name() != null) block.setName(request.name());
        if (request.description() != null) block.setDescription(request.description());
        if (request.startsAt() != null) block.setStartsAt(request.startsAt());
        if (request.endsAt() != null) block.setEndsAt(request.endsAt());

        return mapper.toResponse(repository.save(block));

    }

    private void validateUpdate(Block entity, BlockUpdateRequest request){

        LocalDateTime startsAt = request.startsAt() != null ? request.startsAt() : entity.getStartsAt();
        LocalDateTime endsAt = request.endsAt() != null ? request.endsAt() : entity.getEndsAt();

        this.validatePeriod(startsAt, endsAt);

        if (repository.existsOverlappingExcludingId(entity.getId(), startsAt, endsAt)){
            throw new BlockPeriodConflictException("Já existe um bloqueio no intervalo informado");
        }

    }

    private void validateCreate(BlockRequest request){

        this.validatePeriod(request.startsAt(), request.endsAt());

        if (repository.existsOverlapping(request.startsAt(), request.endsAt())){
            throw new BlockPeriodConflictException("Já existe um bloqueio no intervalo informado");
        }
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

}

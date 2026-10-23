package com.rocha_inf.yasmimqueiroznails.catalog.service;

import com.rocha_inf.yasmimqueiroznails.catalog.dto.request.OfferingRequest;
import com.rocha_inf.yasmimqueiroznails.catalog.dto.request.OfferingUpdateRequest;
import com.rocha_inf.yasmimqueiroznails.catalog.dto.response.OfferingResponse;
import com.rocha_inf.yasmimqueiroznails.catalog.entity.Category;
import com.rocha_inf.yasmimqueiroznails.catalog.entity.Offering;
import com.rocha_inf.yasmimqueiroznails.catalog.exception.OfferingNameAlreadyExistsException;
import com.rocha_inf.yasmimqueiroznails.catalog.exception.OfferingNotFoundException;
import com.rocha_inf.yasmimqueiroznails.catalog.mapper.OfferingMapper;
import com.rocha_inf.yasmimqueiroznails.catalog.repository.OfferingRepository;
import com.rocha_inf.yasmimqueiroznails.security.principal.AuthenticatedUser;
import com.rocha_inf.yasmimqueiroznails.shared.normalization.StringNormalizer;
import com.rocha_inf.yasmimqueiroznails.user.entity.User;
import com.rocha_inf.yasmimqueiroznails.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OfferingService {

    private final OfferingMapper mapper;
    private final OfferingRepository repository;
    private final UserService userService;
    private final CategoryService categoryService;

    public Page<OfferingResponse> findAll(Pageable pageable){
        return repository.findAllByDeletedAtIsNull(pageable).map(mapper::toResponse);
    }

    private Offering findByIdAndDeletedAtIsNull(UUID id){
        return repository.findByIdAndDeletedAtIsNull(id).orElseThrow(() -> new OfferingNotFoundException("Serviço não encontrada"));
    }

    private void validateCreate(OfferingRequest request) {
        if (repository.existsByNameAndDeletedAtIsNull(request.name())) {
            throw new OfferingNameAlreadyExistsException("O nome passado para o serviço ja existe");
        }
    }

    private void validateUpdate(Offering entity, OfferingUpdateRequest request){
        if (repository.existsByNameAndDeletedAtIsNullAndIdNot(request.name(), entity.getId())){
            throw new OfferingNameAlreadyExistsException("O nome passado para o serviço ja existe");
        }
    }

    @Transactional
    public OfferingResponse create(OfferingRequest request) {

        this.validateCreate(request);
        Category category = categoryService.findByIdAndDeletedAtIsNull(request.categoryId());

        AuthenticatedUser user = (AuthenticatedUser) Objects.requireNonNull(
                SecurityContextHolder.getContext().getAuthentication(), "Autenticação não pode ser nulo"
        ).getPrincipal();

        UUID createdById = Objects.requireNonNull(user, "Usuário não pode ser nulo").id();
        User createdBy = userService.findByIdAndStatusActive(createdById);
        Offering offering = mapper.toEntity(request, category, createdBy);

        return mapper.toResponse(repository.save(offering));
    }

    @Transactional
    public OfferingResponse update(UUID id, OfferingUpdateRequest request){

        Offering offering = this.findByIdAndDeletedAtIsNull(id);
        this.validateUpdate(offering, request);

        if (request.categoryId() != null && !offering.getCategory().getId().equals(request.categoryId())) {
            Category category = categoryService.findByIdAndDeletedAtIsNull(request.categoryId());
            offering.setCategory(category);
        }

        if (request.name() != null) { offering.setName(request.name()); }
        if (request.description() != null) {
            String description = StringNormalizer.normalize(request.description());
            offering.setDescription(description);
        }

        if (request.price() != null) { offering.setPrice(request.price()); }
        if (request.durationMinutes() != null) { offering.setDurationMinutes(request.durationMinutes()); }

        return mapper.toResponse(offering);

    }

    @Transactional
    public void delete(UUID id){
        Offering offering = this.findByIdAndDeletedAtIsNull(id);
        offering.delete();
    }

}

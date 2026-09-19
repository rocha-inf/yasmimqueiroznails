package com.rocha_inf.yasmimqueiroznails.catalog.service;

import com.rocha_inf.yasmimqueiroznails.catalog.dto.request.OfferingRequest;
import com.rocha_inf.yasmimqueiroznails.catalog.dto.response.OfferingResponse;
import com.rocha_inf.yasmimqueiroznails.catalog.entity.Category;
import com.rocha_inf.yasmimqueiroznails.catalog.entity.Offering;
import com.rocha_inf.yasmimqueiroznails.catalog.exception.CategoryNotFoundException;
import com.rocha_inf.yasmimqueiroznails.catalog.mapper.OfferingMapper;
import com.rocha_inf.yasmimqueiroznails.catalog.repository.CategoryRepository;
import com.rocha_inf.yasmimqueiroznails.catalog.repository.OfferingRepository;
import com.rocha_inf.yasmimqueiroznails.security.user.AuthenticatedUser;
import com.rocha_inf.yasmimqueiroznails.user.entity.User;
import com.rocha_inf.yasmimqueiroznails.user.exception.UserNotFoundException;
import com.rocha_inf.yasmimqueiroznails.user.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class OfferingService {

    private final OfferingMapper offeringMapper;
    private final OfferingRepository offeringRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public OfferingService(OfferingMapper offeringMapper, OfferingRepository offeringRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.offeringMapper = offeringMapper;
        this.offeringRepository = offeringRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public OfferingResponse create(OfferingRequest request) {

        Category category = categoryRepository.findById(request.categoryId()).orElseThrow(()-> new CategoryNotFoundException("Categoria não encontrada"));

        AuthenticatedUser user =
                (AuthenticatedUser) Objects.requireNonNull(
                        SecurityContextHolder.getContext().getAuthentication(), "Autenticação não pode ser nulo"
                ).getPrincipal();

        UUID createdById = Objects.requireNonNull(user, "Usuário não pode ser nulo").id();
        User createdBy = userRepository.findById(createdById).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        Offering offering = offeringMapper.toEntity(request, category, createdBy);
        Offering savedOffering = offeringRepository.save(offering);

        return offeringMapper.toResponse(savedOffering);
    }

}

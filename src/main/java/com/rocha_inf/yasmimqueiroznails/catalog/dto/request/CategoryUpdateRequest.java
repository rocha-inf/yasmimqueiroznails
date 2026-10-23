package com.rocha_inf.yasmimqueiroznails.catalog.dto.request;

import com.rocha_inf.yasmimqueiroznails.shared.normalization.StringNormalizer;

public record CategoryUpdateRequest(
        String name,
        String description
) {
    public CategoryUpdateRequest{
        name = StringNormalizer.normalize(name);
    }
}

package com.rocha_inf.yasmimqueiroznails.block.dto.request;

import com.rocha_inf.yasmimqueiroznails.shared.normalization.StringNormalizer;

import java.time.LocalDateTime;

public record BlockUpdateRequest(
        String name,
        String description,
        LocalDateTime startsAt,
        LocalDateTime endsAt
) {
     public BlockUpdateRequest {
         name = StringNormalizer.normalize(name);
         description = StringNormalizer.normalize(description);
     }
}

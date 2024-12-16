package org.example.ikproje.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public record VwAsset(
        Long id,
        String name,
        String description
) {

}

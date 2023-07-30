package br.com.otavio.fastorder.model.dto;

import java.math.BigDecimal;

public record OrderProductRecordDTO(String name,
                                    BigDecimal price,
                                    Integer maximumPreparationTimeInMinutes) {
}

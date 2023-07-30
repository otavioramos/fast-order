package br.com.otavio.fastorder.model.dto;

import java.util.List;

public record OrderRecordDTO(OrderPaymentRecordDTO payment, List<OrderItemRecordDTO> items) {
}

package br.com.otavio.fastorder.model.dto;

import java.util.List;

public record CreateOrderDTO(OrderPaymentRecordDTO payment, List<OrderItemRecordDTO> items) {
}

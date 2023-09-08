package br.com.otavio.fastorder.service;

import br.com.otavio.fastorder.model.entity.OrderItem;
import br.com.otavio.fastorder.model.exception.PreparationTimeException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderStatusService {

    public Integer getTotalMinutesToPrepare(List<OrderItem> items) {
        return items.stream()
                .map(item -> item.getProduct().getMaximumPreparationTimeInMinutes() * item.getQuantity())
                .max(Integer::compareTo)
                .orElseThrow(() -> new PreparationTimeException("Nao foi possivel obter o tempo maximo de preparacao em minutos"));
    }
}

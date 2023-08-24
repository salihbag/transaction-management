package com.example.demo.service;

import com.example.demo.model.Order;
import com.example.demo.model.Payment;
import com.example.demo.repository.OrderRepository;
import java.io.FileNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final PaymentService paymentService;
    private final OrderRepository orderRepository;

    @Transactional
    public void placeOrder(Order order) throws FileNotFoundException {
        this.orderRepository.save(order);
        log.info("======> Order Id: {}", order.getId());
        Payment payment = Payment.builder()
                .price(order.getTotalPrice())
                .orderId(order.getId())
                .userId(order.getUserId())
                .build();
        this.paymentService.pay(payment);
    }
}

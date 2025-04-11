package com.Lifesens.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Lifesens.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
	List<OrderItem> findByOrderId(Integer orderId);

}

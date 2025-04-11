package com.Lifesens.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Lifesens.entity.OrderInfo;

public interface OrderInfoRepository extends JpaRepository<OrderInfo, Integer> {
	List<OrderInfo> findByUserIdOrderByCreatedAtDesc(Integer userId);
	 List<OrderInfo> findAllByOrderByCreatedAtDesc();
	 List<OrderInfo> findByStatusOrderByCreatedAtDesc(String status);

}

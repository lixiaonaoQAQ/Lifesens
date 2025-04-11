package com.Lifesens.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Lifesens.entity.InventoryInfo;
import com.Lifesens.entity.ProductInfo;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryInfo, Integer> {
	List<InventoryInfo> findByProduct(ProductInfo product);
}

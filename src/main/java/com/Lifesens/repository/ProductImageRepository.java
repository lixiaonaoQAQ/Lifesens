package com.Lifesens.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Lifesens.entity.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
	List<ProductImage> findByProductId(Integer productId);
}

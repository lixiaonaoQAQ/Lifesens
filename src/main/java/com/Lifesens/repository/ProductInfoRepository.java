package com.Lifesens.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Lifesens.entity.ProductInfo;

@Repository
public interface ProductInfoRepository extends JpaRepository<ProductInfo, Integer> {

    @Query("SELECT p FROM ProductInfo p JOIN FETCH p.category")
    List<ProductInfo> findAllWithCategory();
}

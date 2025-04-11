package com.Lifesens.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.Lifesens.entity.ProductImage;

public interface ProductImageService {

    List<ProductImage> getAllImages();

    void saveProductImage(Integer productId, MultipartFile file);

    void deleteProductImage(Integer imageId);

    List<ProductImage> getImagesByProductId(Integer productId);
    
}

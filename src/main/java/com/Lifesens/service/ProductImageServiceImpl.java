package com.Lifesens.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.Lifesens.entity.ProductImage;
import com.Lifesens.repository.ProductImageRepository;

/**
 * 商品画像のサービス実装クラス
 */
@Service
public class ProductImageServiceImpl implements ProductImageService {

	@Autowired
	private ProductImageRepository productImageRepository;

	// アップロード先ディレクトリ（application.properties で定義）
	@Value("${product.image.upload.dir}")
	private String uploadDir;

	/**
	 * すべての画像を取得
	 */
	@Override
	public List<ProductImage> getAllImages() {
		return productImageRepository.findAll();
	}

	/**
	 * 指定商品の画像をアップロードして保存
	 * @param productId 商品ID
	 * @param file アップロードされたファイル
	 */
	@Override
	@Transactional
	public void saveProductImage(Integer productId, MultipartFile file) {
		if (!file.isEmpty()) {
			try {
				// ランダムなファイル名を生成（UUIDをプレフィックスとして付加）
				String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

				// 保存先ディレクトリを確認・作成
				File dir = new File(uploadDir);
				if (!dir.exists()) {
					dir.mkdirs(); // 必要なフォルダをすべて作成
				}

				// 実際の保存先ファイルパスを構築
				File targetFile = new File(Paths.get(uploadDir, fileName).toString());

				// ファイルを指定位置に保存
				file.transferTo(targetFile);

				// DBに画像情報を保存
				ProductImage productImage = new ProductImage();
				productImage.setProductId(productId);
				productImage.setImageUrl("/uploads/" + fileName); // Web表示用パス
				productImage.setCreatedAt(LocalDateTime.now());
				productImage.setUpdatedAt(LocalDateTime.now());

				productImageRepository.save(productImage);

			} catch (IOException e) {
				throw new RuntimeException("画像アップロードに失敗しました: " + e.getMessage());
			}
		}
	}

	/**
	 * 画像IDを指定して削除
	 * @param imageId 削除対象画像のID
	 */
	@Override
	@Transactional
	public void deleteProductImage(Integer imageId) {
		productImageRepository.deleteById(imageId);
	}

	/**
	 * 商品IDで画像リストを取得
	 * @param productId 商品ID
	 * @return 該当商品の画像リスト
	 */
	@Override
	public List<ProductImage> getImagesByProductId(Integer productId) {
		return productImageRepository.findByProductId(productId);
	}
}

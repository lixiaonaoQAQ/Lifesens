package com.Lifesens.service;

import java.util.List;
import java.util.Optional;

import com.Lifesens.entity.Coupon;

/**
 * クーポンに関するサービスインターフェース
 * クーポンの取得・保存・削除などの処理を定義
 */
public interface CouponService {

    /**
     * 全てのクーポンを取得する
     * @return クーポン一覧
     */
    List<Coupon> findAll();

    /**
     * IDでクーポンを検索する
     * @param id クーポンID
     * @return 該当するクーポン（存在しない場合は空）
     */
    Optional<Coupon> findById(Integer id);

    /**
     * クーポンコードでクーポンを検索する
     * @param code クーポンコード
     * @return 該当するクーポン（存在しない場合は空）
     */
    Optional<Coupon> findByCouponCode(String code);

    /**
     * クーポンを保存・更新する
     * @param coupon 保存するクーポンエンティティ
     * @return 保存されたクーポン
     */
    Coupon save(Coupon coupon);

    /**
     * 指定されたIDのクーポンを削除する
     * @param id クーポンID
     */
    void deleteById(Integer id);
}

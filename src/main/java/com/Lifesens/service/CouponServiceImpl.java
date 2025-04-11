package com.Lifesens.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Lifesens.entity.Coupon;
import com.Lifesens.repository.CouponRepository;

/**
 * CouponService の実装クラス
 * クーポンに関するビジネスロジックを提供
 */
@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;

    /**
     * クーポンをすべて取得する
     * @return クーポンのリスト
     */
    @Override
    public List<Coupon> findAll() {
        return couponRepository.findAll();
    }

    /**
     * ID を指定してクーポンを取得する
     * @param id クーポンID
     * @return Optional<Coupon>（該当なしの場合は空）
     */
    @Override
    public Optional<Coupon> findById(Integer id) {
        return couponRepository.findById(id);
    }

    /**
     * クーポンコードからクーポンを検索する
     * @param code クーポンコード
     * @return Optional<Coupon>（該当なしの場合は空）
     */
    @Override
    public Optional<Coupon> findByCouponCode(String code) {
        return couponRepository.findByCouponCode(code);
    }

    /**
     * クーポンを保存または更新する
     * @param coupon 保存するクーポンエンティティ
     * @return 保存されたクーポン
     */
    @Override
    public Coupon save(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    /**
     * 指定されたIDのクーポンを削除する
     * @param id クーポンID
     */
    @Override
    public void deleteById(Integer id) {
        couponRepository.deleteById(id);
    }
}

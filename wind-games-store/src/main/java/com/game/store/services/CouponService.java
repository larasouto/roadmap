package com.game.store.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.game.store.repositories.CouponRepository;
import com.game.store.models.Coupon;

@Service
public class CouponService {
  private final CouponRepository couponRepository;

  public CouponService(CouponRepository couponRepository) {
    this.couponRepository = couponRepository;
  }

  public List<Coupon> getAllCoupons() {
    return couponRepository.findAll();
  }

  public Optional<Coupon> getCouponById(Long id) {
    return couponRepository.findById(id);
  }

  public Optional<Coupon> getCouponByName(String name) {
    return couponRepository.findByName(name);
  }

  public void saveCoupon(Coupon coupon) {
    couponRepository.save(coupon);
  }

  public void deleteCouponById(Long id) {
    couponRepository.deleteById(id);
  }
}

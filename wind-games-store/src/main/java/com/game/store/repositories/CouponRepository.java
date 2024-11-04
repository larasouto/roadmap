package com.game.store.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.game.store.models.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
  Optional<Coupon> findByName(String name);
}

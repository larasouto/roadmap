package com.game.store.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.game.store.models.Coupon;
import com.game.store.services.CouponService;

@Controller
@RequestMapping(value = "/coupons")
public class CouponController {
  private final CouponService couponService;

  public CouponController(CouponService couponService) {
    this.couponService = couponService;
  }

  @GetMapping("")
  public ModelAndView index() {
    List<Coupon> coupons = couponService.getAllCoupons();
    ModelAndView mv = new ModelAndView("coupons/index");
    mv.addObject("coupons", coupons);
    return mv;
  }

  @GetMapping("/new")
  public ModelAndView createNewCouponForm() {
    ModelAndView mv = new ModelAndView("coupons/new");
    mv.addObject("coupon", new Coupon());
    return mv;
  }

  @PostMapping("")
  public ModelAndView createCoupon(@Valid Coupon coupon, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      ModelAndView mv = new ModelAndView("coupons/new");
      return mv;
    } else {
      couponService.saveCoupon(coupon);
      return new ModelAndView("redirect:/coupons/");
    }
  }

  @GetMapping("/{id}")
  public ModelAndView showCoupon(@PathVariable Long id) {
    Optional<Coupon> optional = couponService.getCouponById(id);
    return optional.map(coupon -> {
      ModelAndView mv = new ModelAndView("coupons/show");
      mv.addObject("coupon", coupon);
      return mv;
  }).orElseGet(() -> returnCouponError("SHOW ERROR: Coupon #" + id + " not found in the database!"));

  }

  @GetMapping("/{id}/delete")
  public ModelAndView deleteCoupon(@PathVariable Long id) {
    ModelAndView mv = new ModelAndView("redirect:/coupons");
    try {
      couponService.deleteCouponById(id);
      mv.addObject("message", "Coupon #" + id + " deleted successfully!");
      mv.addObject("error", false);
    } catch (EmptyResultDataAccessException e) {
      mv = returnCouponError("DELETE ERROR: Coupon #" + id + " not found in the database!");
    }
    return mv;
  }

  private ModelAndView returnCouponError(String message) {
    ModelAndView mv = new ModelAndView("redirect:/coupons");
    mv.addObject("message", message);
    mv.addObject("error", true);
    return mv;
  }
}

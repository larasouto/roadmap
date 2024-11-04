package com.game.store.models;

import java.time.LocalDate;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Coupon {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate expiryDate;

  public Coupon() {
  }

  public Coupon(String name, LocalDate expiryDate) {
    this.name = name;
    this.expiryDate = expiryDate;
  }

  @Override
  public String toString() {
    return name;
  }
}

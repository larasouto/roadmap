package com.game.store.models;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

public class ReportGamePurchases {
  private String name;
  private BigDecimal purchasePrice;
  private BigInteger unit;
  private LocalDate date;

  public ReportGamePurchases(String name, BigDecimal purchasePrice, BigInteger unit) {
    this.name = name;
    this.purchasePrice = purchasePrice;
    this.unit = unit;
  }

  public ReportGamePurchases(String name, BigDecimal purchasePrice, BigInteger unit, LocalDate date) {
    this.name = name;
    this.purchasePrice = purchasePrice;
    this.unit = unit;
    this.date = date;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getPurchasePrice() {
    return purchasePrice;
  }

  public void setPurchasePrice(BigDecimal purchasePrice) {
    this.purchasePrice = purchasePrice;
  }

  public BigInteger getUnit() {
    return unit;
  }

  public void setUnit(BigInteger unit) {
    this.unit = unit;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }
}

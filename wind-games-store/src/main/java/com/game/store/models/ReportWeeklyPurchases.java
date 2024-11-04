package com.game.store.models;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ReportWeeklyPurchases {
  private String name;
  private BigInteger unit;
  private BigDecimal total;

  public ReportWeeklyPurchases(String name, BigDecimal total, BigInteger unit) {
    this.name = name;
    this.unit = unit;
    this.total = total;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigInteger getUnit() {
    return unit;
  }

  public void setUnit(BigInteger unit) {
    this.unit = unit;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public void setTotal(BigDecimal total) {
    this.total = total;
  }
}

package com.game.store.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "purchase_orders")
public class PurchaseOrder {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "purchase_id")
  private Purchase purchase;

  @Column(nullable = true)
  private LocalDate orderDate;

  @Column(nullable = false)
  private boolean paymentConfirmed;

  @Column(nullable = false)
  private boolean invoiceGenerated;

  @Column(nullable = false)
  private boolean gameReleased;

  public PurchaseOrder() {
    this.orderDate = LocalDate.now();
  }

  public PurchaseOrder(Purchase purchase) {
    this.purchase = purchase;
    this.orderDate = LocalDate.now();
  }

  @Override
  public String toString() {
    return "PurchaseOrder [id=" + id + ", purchase=" + purchase + ", orderDate=" + orderDate
        + ", paymentConfirmed=" + paymentConfirmed + ", invoiceGenerated=" + invoiceGenerated
        + ", gameReleased=" + gameReleased + "]";
  }
}

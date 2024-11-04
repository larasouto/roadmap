package com.game.store.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "purchases")
public class Purchase {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "game_id", nullable = false)
  private Game game;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User buyer;

  @Column(name = "card_holder_name", nullable = false)
  private String cardHolder;

  @Column(name = "card_number", nullable = false)
  private Long cardNumber;

  @Column(name = "card_expiration_date", nullable = false)
  private String cardExpirationDate;

  @Column(name = "card_security_code", nullable = false)
  private Integer cardCVV;

  @Column(name = "card_number_intallments", nullable = false)
  private Integer cardNumberIntallments;

  @Column(name = "purchase_date", nullable = true)
  private LocalDate purchaseDate;

  @Column(name = "purchase_status", nullable = true)
  private Boolean purchaseStatus;

  @Column(name = "purchase_price", nullable = true)
  private BigDecimal purchasePrice;

  // @ManyToOne
  // @JoinColumn(name = "coupon_id")
  // private Coupon purchaseCoupon;

  public Purchase() {
  }

  public Purchase(Game game, User buyer, String cardHolder, Long cardNumber, String cardExpirationDate, Integer cardCVV,
      Integer cardNumberIntallments, LocalDate purchaseDate, Boolean purchaseStatus) {
    this.game = game;
    this.buyer = buyer;
    this.cardHolder = cardHolder;
    this.cardNumber = cardNumber;
    this.cardExpirationDate = cardExpirationDate;
    this.cardCVV = cardCVV;
    this.cardNumberIntallments = cardNumberIntallments;
    this.purchaseDate = purchaseDate;
    this.purchaseStatus = false;
  }

  public Purchase(Game game, User buyer, String cardHolder, Long cardNumber, String cardExpirationDate, Integer cardCVV,
      Integer cardNumberIntallments, Boolean purchaseStatus) {
    this.game = game;
    this.buyer = buyer;
    this.cardHolder = cardHolder;
    this.cardNumber = cardNumber;
    this.cardExpirationDate = cardExpirationDate;
    this.cardCVV = cardCVV;
    this.cardNumberIntallments = cardNumberIntallments;
    this.purchaseStatus = purchaseStatus;
  }

  @Override
  public String toString() {
    return "Purchase [id=" + id + ", game=" + game + ", buyer=" + buyer + ", cardHolder=" + cardHolder + ", cardNumber="
        + cardNumber + ", cardExpirationDate=" + cardExpirationDate + ", cardCVV=" + cardCVV
        + ", cardNumberIntallments=" + cardNumberIntallments + ", purchaseDate=" + purchaseDate + "]";
  }

  public void confirmPurchase() {
    this.purchaseStatus = true;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Game getGame() {
    return game;
  }

  public void setGame(Game game) {
    this.game = game;
  }

  public User getBuyer() {
    return buyer;
  }

  public void setBuyer(User buyer) {
    this.buyer = buyer;
  }

  public String getCardHolder() {
    return cardHolder;
  }

  public void setCardHolder(String cardHolder) {
    this.cardHolder = cardHolder;
  }

  public Long getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(Long cardNumber) {
    this.cardNumber = cardNumber;
  }

  public String getCardExpirationDate() {
    return cardExpirationDate;
  }

  public void setCardExpirationDate(String cardExpirationDate) {
    this.cardExpirationDate = cardExpirationDate;
  }

  public Integer getCardCVV() {
    return cardCVV;
  }

  public void setCardCVV(Integer cardCVV) {
    this.cardCVV = cardCVV;
  }

  public Integer getCardNumberIntallments() {
    return cardNumberIntallments;
  }

  public void setCardNumberIntallments(Integer cardNumberIntallments) {
    this.cardNumberIntallments = cardNumberIntallments;
  }

  public LocalDate getPurchaseDate() {
    return purchaseDate;
  }

  public void setPurchaseDate(LocalDate purchaseDate) {
    this.purchaseDate = purchaseDate;
  }

  public Boolean getPurchaseStatus() {
    return purchaseStatus;
  }

  public void setPurchaseStatus(Boolean purchaseStatus) {
    this.purchaseStatus = purchaseStatus;
  }

  public void setPurchasePrice(BigDecimal purchasePrice) {
    this.purchasePrice = purchasePrice;
  }

  public BigDecimal getPurchasePrice() {
    return purchasePrice;
  }
}

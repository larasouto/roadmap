package com.game.store.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.game.store.models.Game;
import com.game.store.models.Purchase;
import com.game.store.models.User;

public class PurchaseDto {
    @NotNull
    private Long gameId;

    @NotNull
    private Long userId;

    @NotBlank
    private String cardHolder;

    @NotNull
    private Long cardNumber;

    @NotBlank
    private String cardExpirationDate;

    @NotNull
    private Integer cardCVV;

    @NotNull
    private Integer cardNumberIntallments;

    private Boolean purchaseStatus;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate purchaseDate;

   // private Coupon purchaseCoupon;

    public Purchase toPurchase(Game game, User user) {
        Purchase purchase = new Purchase();
        purchase.setGame(game);
        purchase.setBuyer(user);
        purchase.setCardHolder(this.cardHolder);
        purchase.setCardNumber(this.cardNumber);
        purchase.setCardExpirationDate(this.cardExpirationDate);
        purchase.setCardCVV(this.cardCVV);
        purchase.setCardNumberIntallments(this.cardNumberIntallments);
        purchase.setPurchaseDate(this.purchaseDate);
        purchase.setPurchaseStatus(this.purchaseStatus);
       // purchase.setPurchaseCoupon(this.purchaseCoupon);
        return purchase;
    }

    public Purchase toPurchase(Purchase purchase) {
        purchase.setCardHolder(this.cardHolder);
        purchase.setCardNumber(this.cardNumber);
        purchase.setCardExpirationDate(this.cardExpirationDate);
        purchase.setCardCVV(this.cardCVV);
        purchase.setCardNumberIntallments(this.cardNumberIntallments);
        purchase.setPurchaseDate(this.purchaseDate);
        purchase.setPurchaseStatus(this.purchaseStatus);
       // purchase.setPurchaseCoupon(this.purchaseCoupon);
        return purchase;
    }

    public void fromPurchase(Purchase purchase) {
        this.cardHolder = purchase.getCardHolder();
        this.cardNumber = purchase.getCardNumber();
        this.cardExpirationDate = purchase.getCardExpirationDate();
        this.cardCVV = purchase.getCardCVV();
        this.cardNumberIntallments = purchase.getCardNumberIntallments();
        this.purchaseDate = purchase.getPurchaseDate();
        this.purchaseStatus = purchase.getPurchaseStatus();
       // this.purchaseCoupon = purchase.getPurchaseCoupon();

    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardExpirationDate(String cardExpirationDate) {
        this.cardExpirationDate = cardExpirationDate;
    }

    public String getCardExpirationDate() {
        return cardExpirationDate;
    }

    public void setCardCVV(Integer cardCVV) {
        this.cardCVV = cardCVV;
    }

    public Integer getCardCVV() {
        return cardCVV;
    }

    public void setCardNumberIntallments(Integer cardNumberIntallments) {
        this.cardNumberIntallments = cardNumberIntallments;
    }

    public Integer getCardNumberIntallments() {
        return cardNumberIntallments;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseStatus(Boolean purchaseStatus) {
        this.purchaseStatus = purchaseStatus;
    }

    public Boolean getPurchaseStatus() {
        return purchaseStatus;
    }

    @Override
    public String toString() {
        return "PurchaseDto{" +
                "gameId=" + gameId +
                ", userId=" + userId +
                ", cardHolder='" + cardHolder + '\'' +
                ", cardNumber=" + cardNumber +
                ", cardExpirationDate='" + cardExpirationDate + '\'' +
                ", cardCVV=" + cardCVV +
                ", cardNumberIntallments=" + cardNumberIntallments +
                ", purchaseDate=" + purchaseDate +
                '}';
    }
}

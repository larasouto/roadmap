package com.game.store.dto;

import java.time.LocalDate;

public class PurchaseOrderDto {
    private Long id;
    private GameDto game;
    private UserDto buyer;
    private String cardHolder;
    private Long cardNumber;
    private String cardExpirationDate;
    private Integer cardCVV;
    private LocalDate purchaseDate;
    
    public PurchaseOrderDto() {
    }

    public void setGame(GameDto game) {
        this.game = game;
    }

    public void setBuyer(UserDto buyer) {
        this.buyer = buyer;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCardExpirationDate(String cardExpirationDate) {
        this.cardExpirationDate = cardExpirationDate;
    }

    public void setCardCVV(Integer cardCVV) {
        this.cardCVV = cardCVV;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public GameDto getGame() {
        return game;
    }

    public UserDto getBuyer() {
        return buyer;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public String getCardExpirationDate() {
        return cardExpirationDate;
    }

    public Integer getCardCVV() {
        return cardCVV;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}



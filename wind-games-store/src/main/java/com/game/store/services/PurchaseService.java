package com.game.store.services;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.game.store.models.Purchase;
import com.game.store.models.ReportGamePurchases;
import com.game.store.models.ReportWeeklyPurchases;
import com.game.store.models.User;
import com.game.store.repositories.PurchaseRepository;

@Service
public class PurchaseService {
  private final PurchaseRepository purchaseRepository;

  public PurchaseService(PurchaseRepository purchaseRepository) {
    this.purchaseRepository = purchaseRepository;
  }

  public List<Purchase> getAllPurchases() {
    return purchaseRepository.findAll();
  }

  public Optional<Purchase> getPurchaseById(Long id) {
    return purchaseRepository.findById(id);
  }

  public List<Purchase> getPurchasesByDate(LocalDate purchaseDate) {
    return purchaseRepository.findByPurchaseDate(purchaseDate);
  }

  public void savePurchase(Purchase purchase) {
    purchaseRepository.save(purchase);
  }

  public void deletePurchaseById(Long id) {
    purchaseRepository.deleteById(id);
  }

  public List<Purchase> getPurchasesBetweenDates(LocalDate startDate, LocalDate endDate) {
    return purchaseRepository.findByPurchaseDateBetween(startDate, endDate);
  }

  public List<Purchase> getPurchasesByUserId(Long userId) {
    return purchaseRepository.findByBuyerId(userId);
  }

  public Set<Purchase> getPurchasesByBuyer(User buyer) {
    return purchaseRepository.findByBuyer(buyer);
  }

  public List<ReportGamePurchases> findGamePurchase(LocalDate date) {
    List<Object[]> results = purchaseRepository.findGamePurchase(date);
    List<ReportGamePurchases> gamePurchase = new ArrayList<>();

    for (Object[] row : results) {
      String gameName = (String) row[0];
      BigDecimal gamePurchasePrice = (BigDecimal) row[1];
      BigInteger gameUnit = (BigInteger) row[2];

      ReportGamePurchases gamePurchase1 = new ReportGamePurchases(gameName, gamePurchasePrice, gameUnit);
      gamePurchase.add(gamePurchase1);
    }
    return gamePurchase;
  }

  public List<ReportWeeklyPurchases> findGenrePurchase(LocalDate startDate, LocalDate endDate) {
    List<Object[]> results = purchaseRepository.findGenrePurchase(startDate, endDate);
    List<ReportWeeklyPurchases> purchases = new ArrayList<>();

    for (Object[] row : results) {
      String name = (String) row[0];
      BigInteger unit = (BigInteger) row[1];
      BigDecimal price = (BigDecimal) row[2];

      ReportWeeklyPurchases purchase = new ReportWeeklyPurchases(name, price, unit);
      purchases.add(purchase);
    }
    return purchases;
  }

  public List<ReportWeeklyPurchases> findPlatformPurchase(LocalDate startDate, LocalDate endDate) {
    List<Object[]> results = purchaseRepository.findPlatformPurchase(startDate, endDate);
    List<ReportWeeklyPurchases> purchases = new ArrayList<>();

    for (Object[] row : results) {
      String name = (String) row[0];
      BigInteger unit = (BigInteger) row[1];
      BigDecimal price = (BigDecimal) row[2];

      ReportWeeklyPurchases purchase = new ReportWeeklyPurchases(name, price, unit);
      purchases.add(purchase);
    }
    return purchases;
  }

  public List<ReportWeeklyPurchases> findDeveloperPurchase(LocalDate startDate, LocalDate endDate) {
    List<Object[]> results = purchaseRepository.findDeveloperPurchase(startDate, endDate);
    List<ReportWeeklyPurchases> purchases = new ArrayList<>();

    for (Object[] row : results) {
      String name = (String) row[0];
      BigInteger unit = (BigInteger) row[1];
      BigDecimal price = (BigDecimal) row[2];

      ReportWeeklyPurchases purchase = new ReportWeeklyPurchases(name, price, unit);
      purchases.add(purchase);
    }
    return purchases;
  }
}

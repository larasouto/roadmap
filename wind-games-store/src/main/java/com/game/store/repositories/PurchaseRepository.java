package com.game.store.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.game.store.models.Purchase;
import com.game.store.models.User;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findByPurchaseDate(LocalDate purchaseDate);

    List<Purchase> findByPurchaseDateBetween(LocalDate startDate, LocalDate endDate);

    List<Purchase> findByBuyerId(Long buyer);

    Set<Purchase> findByBuyer(User buyer);

    @Query(value = "SELECT game.name, SUM(purchase_price), count(name) AS unit from purchases INNER JOIN game ON purchases.game_id = game.id WHERE purchases.game_id = game.id AND purchases.purchase_status = true AND purchases.purchase_date = :date GROUP BY game.name", nativeQuery = true)
    public List<Object[]> findGamePurchase(LocalDate date);

    @Query(value = "SELECT genre.name, COUNT(genre.name), SUM(price) FROM purchases INNER JOIN game ON purchases.game_id = game.id INNER JOIN genre ON game.genre_id = genre.id WHERE game.genre_id = genre.id AND purchases.purchase_date BETWEEN :startDate AND :endDate AND purchases.purchase_status = true GROUP BY genre.name", nativeQuery = true)
    public List<Object[]> findGenrePurchase(LocalDate startDate, LocalDate endDate);

    @Query(value = "SELECT device.name, COUNT(device.name), SUM(price) FROM purchases INNER JOIN game ON purchases.game_id = game.id INNER JOIN device ON game.platform_id = device.id WHERE game.platform_id = device.id AND purchases.purchase_date BETWEEN :startDate AND :endDate AND purchases.purchase_status = true GROUP BY device.name", nativeQuery = true)
    public List<Object[]> findPlatformPurchase(LocalDate startDate, LocalDate endDate);

    @Query(value = "SELECT game.developer, COUNT(game.developer), SUM(price) FROM purchases INNER JOIN game ON purchases.game_id = game.id  WHERE purchases.purchase_date BETWEEN :startDate AND :endDate AND purchases.purchase_status = true GROUP BY game.developer", nativeQuery = true)
    public List<Object[]> findDeveloperPurchase(LocalDate startDate, LocalDate endDate);

}

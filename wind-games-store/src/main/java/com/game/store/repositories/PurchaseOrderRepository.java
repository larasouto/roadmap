package com.game.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.game.store.models.PurchaseOrder;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
  
}

package com.game.store.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.game.store.models.Device;

public interface DeviceRepository extends JpaRepository<Device, Long> {
  Optional<Device> findByName(String name);
}

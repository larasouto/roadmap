package com.game.store.services;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import com.game.store.models.Device;
import com.game.store.repositories.DeviceRepository;

@Service
public class DeviceService {
  private final DeviceRepository deviceRepository;

  public DeviceService(DeviceRepository deviceRepository) {
    this.deviceRepository = deviceRepository;
  }

  public List<Device> getAllDevices() {
    return deviceRepository.findAll();
  }

  public Optional<Device> getDeviceById(Long id) {
    return deviceRepository.findById(id);
  }

  public Optional<Device> getDeviceByName(String name) {
    return deviceRepository.findByName(name);
  }

  public void saveDevice(Device device) {
    deviceRepository.save(device);
  }

  public void deletedeviceById(Long id) {
    deviceRepository.deleteById(id);
  }
}
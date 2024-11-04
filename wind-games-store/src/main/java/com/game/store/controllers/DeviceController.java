package com.game.store.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.game.store.models.Device;
import com.game.store.services.DeviceService;

@Controller
@RequestMapping(value = "/devices")
public class DeviceController {
  private final DeviceService deviceService;

  public DeviceController(DeviceService deviceService) {
    this.deviceService = deviceService;
  }

  @GetMapping("")
  public ModelAndView index() {
    List<Device> devices = deviceService.getAllDevices();
    ModelAndView mv = new ModelAndView("devices/index");
    mv.addObject("devices", devices);
    return mv;
  }

  @GetMapping("/new")
  public ModelAndView createNewDeviceForm() {
    ModelAndView mv = new ModelAndView("devices/new");
    mv.addObject("device", new Device());
    return mv;
  }

  @PostMapping("")
  public ModelAndView createDevice(@Valid Device device, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return new ModelAndView("devices/new");
  }
  
  deviceService.saveDevice(device);
  return new ModelAndView("redirect:/devices/");
  
  }

  @GetMapping("/{id}")
  public ModelAndView showDevice(@PathVariable Long id) {
    return deviceService.getDeviceById(id)
        .map(device -> {
            ModelAndView mv = new ModelAndView("devices/show");
            mv.addObject("device", device);
            return mv;
        })
        .orElseGet(() -> returnDeviceError("SHOW ERROR: device #" + id + " not found in the database!"));
  }

  @GetMapping("/{id}/edit")
  public ModelAndView editDeviceForm(@PathVariable Long id) {
    Optional<Device> optional = deviceService.getDeviceById(id);
    return optional.map(device -> {
      ModelAndView mv = new ModelAndView("devices/edit");
      mv.addObject("device", device);
      return mv;
    }).orElseGet(() -> returnDeviceError("EDIT ERROR: device #" + id + " not found in the database!"));
  }

  @PostMapping("/{id}")
  public ModelAndView updateDevice(@PathVariable Long id, @Valid Device device, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return new ModelAndView("devices/edit")
              .addObject("device", device);
  }
  
  return deviceService.getDeviceById(id)
          .map(existingDevice -> {
              existingDevice.setName(device.getName()); 
              deviceService.saveDevice(existingDevice);
              return new ModelAndView("redirect:/devices/");
          })
          .orElseGet(() -> returnDeviceError("UPDATE ERROR: device #" + id + " not found in the database!"));  
  }

  @GetMapping("/{id}/delete")
  public ModelAndView deleteDevice(@PathVariable Long id) {
    ModelAndView mv = new ModelAndView("redirect:/devices");
    try {
      deviceService.deletedeviceById(id);
      mv.addObject("message", "device #" + id + " deleted successfully!");
      mv.addObject("error", false);
    } catch (EmptyResultDataAccessException e) {
      mv = returnDeviceError("DELETE ERROR: device #" + id + " not found in the database!");
    }
    return mv;
  }

  private ModelAndView returnDeviceError(String message) {
    ModelAndView mv = new ModelAndView("redirect:/devices");
    mv.addObject("message", message);
    mv.addObject("error", true);
    return mv;
  }
}

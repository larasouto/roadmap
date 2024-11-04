package com.game.store.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.game.store.models.Purchase;
import com.game.store.models.ReportGamePurchases;
import com.game.store.models.ReportWeeklyPurchases;
import com.game.store.services.DeviceService;
import com.game.store.services.GameService;
import com.game.store.services.GenreService;
import com.game.store.services.PurchaseService;

@Controller
@RequestMapping("/reports")
public class ReportController {
  public final PurchaseService purchaseService;
  public final GameService gameService;
  public final GenreService genreService;
  public final DeviceService deviceService;

  public ReportController(PurchaseService purchaseService, GameService gameService, GenreService genreService,
      DeviceService deviceService) {
    this.purchaseService = purchaseService;
    this.gameService = gameService;
    this.genreService = genreService;
    this.deviceService = deviceService;
  }

  @GetMapping("/daily-sales")
  public ModelAndView generateDailySalesReport(
      @RequestParam(name = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {

    if (date == null) {
      date = LocalDate.now();
    }

    List<ReportGamePurchases> reportGamePurchases = purchaseService.findGamePurchase(date);
    BigDecimal totalSales = BigDecimal.ZERO;
    for (ReportGamePurchases purchase : reportGamePurchases) {
      //BigDecimal saleValue = purchase.getPurchasePrice();
      //totalSales = totalSales.add(saleValue);
    }
    System.out.println(reportGamePurchases);
    System.out.println(totalSales);
    ModelAndView mv = new ModelAndView("reports/daily-sales");
    mv.addObject("date", date);
    mv.addObject("purchases", reportGamePurchases);
    mv.addObject("totalSales", totalSales);
    return mv;
  }

  @GetMapping("/weekly-sales")
  public ModelAndView generateWeeklySalesReport(
      @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
      @RequestParam(name = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

    if (startDate == null) {
      startDate = LocalDate.now().minusDays(7);
    }

    if (endDate == null) {
      endDate = LocalDate.now();
    }

    List<Purchase> purchases = purchaseService.getPurchasesBetweenDates(startDate, endDate);

    List<ReportWeeklyPurchases> reportGenrePurchases = purchaseService.findGenrePurchase(startDate, endDate);
    BigDecimal genreTotal = getTotalSales(reportGenrePurchases);
    List<ReportWeeklyPurchases> reportPlatformPurchases = purchaseService.findPlatformPurchase(startDate, endDate);
    BigDecimal platformTotal = getTotalSales(reportPlatformPurchases);
    List<ReportWeeklyPurchases> reportDeveloperPurchases = purchaseService.findDeveloperPurchase(startDate, endDate);
    BigDecimal developerTotal = getTotalSales(reportDeveloperPurchases);

    ModelAndView mv = new ModelAndView("reports/weekly-sales");
    mv.addObject("startDate", startDate);
    mv.addObject("endDate", endDate);
    mv.addObject("gamesSoldByGenre", reportGenrePurchases);
    mv.addObject("gamesSoldByPlatform", reportPlatformPurchases);
    mv.addObject("gamesSoldByDeveloper", reportDeveloperPurchases);
    mv.addObject("genreTotal", genreTotal);
    mv.addObject("platformTotal", platformTotal);
    mv.addObject("developerTotal", developerTotal);

    return mv;
  }

  public BigDecimal getTotalSales(List<ReportWeeklyPurchases> reportGamePurchases) {
    BigDecimal totalSales = BigDecimal.ZERO;
    for (ReportWeeklyPurchases purchase : reportGamePurchases) {
      //BigDecimal saleValue = purchase.getTotal();
      //totalSales = totalSales.add(saleValue);
    }
    return totalSales;
  }
}
package com.fiap.mercadoexpressmensageria.controller;

import com.fiap.mercadoexpressmensageria.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAllAttributes(dashboardService.montarResumo());
        return "dashboard";
    }

}

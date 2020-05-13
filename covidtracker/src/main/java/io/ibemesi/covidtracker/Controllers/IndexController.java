package io.ibemesi.covidtracker.Controllers;

import io.ibemesi.covidtracker.services.CovidDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @Autowired
    CovidDataService covidDataService;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("location", covidDataService.getCovidStats());
        model.addAttribute("total", covidDataService.getTotalCovidCases());
        return "index";
    }

}

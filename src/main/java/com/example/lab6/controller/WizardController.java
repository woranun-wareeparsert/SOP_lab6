package com.example.lab6.controller;

import com.example.lab6.pojo.Wizard;
import com.example.lab6.repository.WizardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class WizardController {
    @Autowired
    private WizardService wizardService;

    @RequestMapping(value ="/wizards", method = RequestMethod.GET)
    public List<Wizard> getWizard(){
        List<Wizard> wizard = wizardService.retrieveWizard();
        return wizard;
    }

    @RequestMapping(value ="/addWizard", method = RequestMethod.POST)
    public String createWizard(@RequestBody Wizard input){
        Wizard w = wizardService.createWizard(input);
        return "Wizard has been created";
    }

    @RequestMapping(value = "/updateWizard", method = RequestMethod.POST)
    public String updateWizard(@RequestBody Wizard input){
        wizardService.updateWizard(input);
        return "Wizard has been update";
    }

    @RequestMapping(value ="/deleteWizard", method = RequestMethod.POST)
    public String deleteWizard(@RequestBody MultiValueMap<String, String> n){
        Map<String, String> d = n.toSingleValueMap();
        Wizard w = wizardService.retrieveWizardByName(d.get("name"));
        boolean status = wizardService.deleteWizard(w);
        return "Wizard has been delete";
    }
}

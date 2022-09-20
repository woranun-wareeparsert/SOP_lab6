package com.example.lab6.repository;

import com.example.lab6.pojo.Wizard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WizardService {
    @Autowired
    private WizardRepository repository;

    public WizardService(WizardRepository repository){
        this.repository = repository;
    }

    public List<Wizard> retrieveWizard(){
        return repository.findAll();
    }

    public Wizard createWizard(Wizard w){
        return repository.insert(w);
    }

    public Wizard retrieveWizardByName(String name){
        return repository.findByName(name);
    }

    public Wizard updateWizard(Wizard w){
        return repository.save(w);
    }


    public boolean deleteWizard(Wizard w){
        try{
            repository.delete(w);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}

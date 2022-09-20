package com.example.lab6.view;

import com.example.lab6.pojo.Wizard;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Route(value = "mainPage.it")
public class MainWizardView extends FormLayout {
    private TextField fullname;
    private NumberField dollars;
    private RadioButtonGroup<String> gender;
    private ComboBox<String> position, school, house;
    private Button left, create, update, delete, right;
    public int check = -1;
    public String id;

    public MainWizardView(){
        fullname = new TextField();
        fullname.setPlaceholder("Fullname");
        dollars = new NumberField("Dollars");
        dollars.setPrefixComponent(new Span("$"));
        gender = new RadioButtonGroup<>();
        gender.setLabel("Gender :");
        gender.setItems("Male", "Female");
        position = new ComboBox<>();
        position.setPlaceholder("Position");
        position.setItems("student", "teacher");
        school = new ComboBox<>();
        school.setPlaceholder("School");
        school.setItems("Hogwarts", "Beauxbatons", "Durmstrang");
        house = new ComboBox<>();
        house.setPlaceholder("House");
        house.setItems("Gryffindor", "Ravenclaw", "Hufflepuff", "Slyther");
        left = new Button("<<");
        create = new Button("Create");
        update = new Button("Update");
        delete = new Button("Delete");
        right = new Button(">>");

        HorizontalLayout hl = new HorizontalLayout();
        VerticalLayout vl = new VerticalLayout();

        hl.add(left, create, update, delete, right);
        vl.add(fullname, gender, position, dollars, school, house, hl);
        this.add(vl);

        create.addClickListener(event -> {
            String name = fullname.getValue();
            String pos = position.getValue();
            int money = dollars.getValue().intValue();
            String sch = school.getValue();
            String h = house.getValue();

            String sex = gender.getValue();
            if (sex.equals("Female")){
                sex = "f";
            }else{
                sex = "m";
            }

            String out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/addWizard")
                    .body(Mono.just(new Wizard(null, sex, name, sch, h, money, pos)), Wizard.class)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            Notification notification = Notification.show(out);
        });

        right.addClickListener(event -> {
            List<Wizard> out = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/wizards")
                    .retrieve()
                    .bodyToMono(List.class)
                    .block();
                ObjectMapper mapper = new ObjectMapper();
                Wizard pojo = new Wizard();
                if (this.check < out.size()-1) {
                    this.check++;
                    pojo = mapper.convertValue(out.get(this.check), Wizard.class);
                } else {
                    this.check = -1;
                    this.check++;
                    pojo = mapper.convertValue(out.get(this.check), Wizard.class);
                }
                fullname.setValue(pojo.getName());
                gender.setValue(pojo.getSex());
                position.setValue(pojo.getPosition());
                dollars.setValue((double) pojo.getMoney());
                school.setValue(pojo.getSchool());
                house.setValue(pojo.getHouse());
                this.id = pojo.get_id();
        });

        left.addClickListener(event -> {
            List<Wizard> out = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/wizards")
                    .retrieve()
                    .bodyToMono(List.class)
                    .block();
            ObjectMapper mapper = new ObjectMapper();
            Wizard pojo = new Wizard();
            if (this.check <= 0) {
                this.check = out.size()-1;
                pojo = mapper.convertValue(out.get(this.check), Wizard.class);
            } else {
                this.check--;
                pojo = mapper.convertValue(out.get(this.check), Wizard.class);
            }
            fullname.setValue(pojo.getName());
            gender.setValue(pojo.getSex());
            position.setValue(pojo.getPosition());
            dollars.setValue((double) pojo.getMoney());
            school.setValue(pojo.getSchool());
            house.setValue(pojo.getHouse());
            this.id = pojo.get_id();
        });

        update.addClickListener(event -> {
//            System.out.println(this.id);
//            System.out.println(gender.getValue());
            String sex = gender.getValue();
            if (sex.equals("Female")){
                sex = "f";
            }else{
                sex = "m";
            }

            String out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/updateWizard")
                    .body(Mono.just(new Wizard(this.id, sex, fullname.getValue(), school.getValue(),
                            house.getValue(), dollars.getValue().intValue(), position.getValue())), Wizard.class)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            Notification notification = Notification.show(out);
        });

        delete.addClickListener(event -> {
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("name", fullname.getValue());

            String out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/deleteWizard")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            Notification notification = Notification.show(out);
        });
    }
}

package com.api.sassila.controleur;

import com.api.sassila.modele.Individu;
import com.api.sassila.service.IndividuServiceImplement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/")
public class DefaultControleur {
    private final IndividuServiceImplement individuServiceImplement;

    @GetMapping
    public String dashboard(Model model) {
        ArrayList<Individu> individus = (ArrayList<Individu>) individuServiceImplement.recupererIndividusRecent();
        model.addAttribute("nbreIndividus", individuServiceImplement.countIndividus());
        model.addAttribute("nbreHommes", individuServiceImplement.countHommes());
        model.addAttribute("nbreFemmes", individuServiceImplement.countFemmes());
        model.addAttribute("individus", individus);
        return "dashboard";
    }
}

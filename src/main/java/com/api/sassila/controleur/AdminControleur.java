package com.api.sassila.controleur;

import com.api.sassila.modele.Femme;
import com.api.sassila.modele.Homme;
import com.api.sassila.modele.Individu;
import com.api.sassila.modele.Section;
import com.api.sassila.service.IndividuServiceImplement;
import com.api.sassila.service.SectionServiceImplement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/admin")
@Slf4j
public class AdminControleur {
    private final IndividuServiceImplement individuServiceImplement;
    private final SectionServiceImplement sectionServiceImplement;

    @GetMapping(path = "/**")
    public String dashboard(Model model) {
        ArrayList<Individu> individuArrayList = (ArrayList<Individu>) individuServiceImplement.recupererIndividus();
        Map<Integer, Individu> lastIndividus = new TreeMap<>();
        int j = 0;
        for (int i = individuArrayList.size(); i > individuArrayList.size() - 5; i--) {
            lastIndividus.put(++j, individuArrayList.get(i - 1));
        }
        model.addAttribute("nbreIndividus", individuServiceImplement.countIndividus());
        model.addAttribute("nbreHommes", individuServiceImplement.countHommes());
        model.addAttribute("nbreFemmes", individuServiceImplement.countFemmes());
        model.addAttribute("individus", lastIndividus);
        return "dashboard";
    }

    @GetMapping(path = "individus")
    public String individus(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        ArrayList<Individu> individuArrayList = (ArrayList<Individu>) individuServiceImplement.recupererIndividus();
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Individu> individuPage = individuServiceImplement.recupererIndividusPage(PageRequest.of(currentPage - 1, pageSize));

        int totalPages = individuPage.getTotalPages();
        if (totalPages> 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("currentPage", currentPage);
        }

        model.addAttribute("individus", individuPage);
        return "individus";
    }

    @GetMapping(path = "mariage/{key}")
    public String mariage(Model model, @PathVariable(name = "key") String key) {
        ArrayList<Femme> femmes = (ArrayList<Femme>) individuServiceImplement.recupererFemmes();
        model.addAttribute("hommes", individuServiceImplement.recupererHommes());
        model.addAttribute("femmes", femmes);
        return "mariage";
    }

    @GetMapping(path = "individu/{id}")
    public String individu(Model model, @PathVariable(name = "id") long id) {
        Optional<Individu> optionalIndividu = individuServiceImplement.recupererUnIndividu(id);
        if (optionalIndividu.isEmpty()) return "redirect:/admin/individus";
        Individu individu = optionalIndividu.get();
        model.addAttribute("individu", individu);
        model.addAttribute("imageSize", individu.getImageUrl() == null ? 0 : individu.getImageUrl().length);
        model.addAttribute("sections", sectionServiceImplement.recupererSectionsParIndividu(id));
        return "individu";
    }

    @GetMapping(path = "{id}/epouses")
    public String individuSpouses(Model model, @PathVariable(name = "id") long id) {
        Optional<Homme> optionalHomme = individuServiceImplement.recupererUnHomme(id);
        if (optionalHomme.isPresent()) {
            Homme epoux = optionalHomme.get();
            model.addAttribute("individu", epoux);
            model.addAttribute("imageSize", epoux.getImageUrl() == null ? 0 : epoux.getImageUrl().length);
            model.addAttribute("epouses", epoux.getEpouses());
            return "spouses";
        }
        return "redirect:/admin/individus";
    }

    @GetMapping(path = "{id}/enfants")
    public String enfants(Model model, @PathVariable(name = "id") long id) {
        Optional<Individu> optionalIndividu = individuServiceImplement.recupererUnIndividu(id);
        if (optionalIndividu.isEmpty()) return "redirect:/admin/individus";
        Individu individu = optionalIndividu.get();
        model.addAttribute("individu", individu);
        model.addAttribute("enfants", individu.getEnfants());
        return "enfants";
    }

    @GetMapping(path = "{id}/sections")
    public String individuSections(Model model, @PathVariable(name = "id") long id) {
        Optional<Individu> optionalIndividu = individuServiceImplement.recupererUnIndividu(id);
        if (optionalIndividu.isEmpty()) return "redirect:/admin/individus";
        Individu individu = optionalIndividu.get();
        model.addAttribute("individu", individu);
        model.addAttribute("imageSize", individu.getImageUrl() == null ? 0 : individu.getImageUrl().length);
        model.addAttribute("sections", sectionServiceImplement.recupererSectionsParIndividu(id));
        return "sections";
    }

    @GetMapping(path = "{id}/ajouter-enfant")
    public String ajouterEnfants(Model model, @PathVariable(name = "id") long id) {
        Optional<Individu> optionalIndividu = individuServiceImplement.recupererUnIndividu(id);
        if (optionalIndividu.isEmpty()) return "redirect:/admin/individus";
        Individu individu = optionalIndividu.get();
        model.addAttribute("individu", individu);
        Individu intermediaire = new Individu();
        intermediaire.setId(id);
        model.addAttribute("intermediaire", intermediaire);
        ArrayList<Individu> individus = (ArrayList<Individu>) individuServiceImplement.recupererIndividus();
        individus.remove(individu);
        if (individu.getKey_().startsWith("H")) individus.removeAll(((Homme) individu).getEpouses());
        else if (individu.getKey_().startsWith("F")) individus.remove(((Femme) individu).getEpoux());
        individus.remove(individu);
        for (int i = 0; i < individu.getEnfants().size(); i++) {
            individus.remove(individu.getEnfants().get(i));
        }
        model.addAttribute("enfants", individus);
        return "ajouterEnfant";
    }

    @GetMapping(path = "{id}/ajouter-section")
    public String ajouterSection(Model model, @PathVariable(name = "id") long id) {
        Optional<Individu> optionalIndividu = individuServiceImplement.recupererUnIndividu(id);
        if (optionalIndividu.isEmpty()) return "redirect:/admin/individus";
        Individu individu = optionalIndividu.get();
        model.addAttribute("individu", individu);
        model.addAttribute("section", new Section());
        return "newSection";
    }

    @PostMapping(path = "ajouter-section")
    public String addingSection(@Valid @ModelAttribute("section") Section section, BindingResult bindingResult, long individuId, Model model) {
        Optional<Individu> optionalIndividu = individuServiceImplement.recupererUnIndividu(individuId);
        if (optionalIndividu.isEmpty()) return "redirect:/admin/individus";
        Individu individu = optionalIndividu.get();
        if (bindingResult.hasErrors()) {
            model.addAttribute("section", section);
            model.addAttribute("individu", individu);
            return "newSection";
        }
        sectionServiceImplement.ajouterSection(section, individuId);
        return "redirect:/admin/"+individuId+"/sections";
    }

    @GetMapping(path = "{individuId}/editer-section/{sectionId}")
    public String editerSection(Model model, @PathVariable(name = "individuId") long individuId, @PathVariable(name = "sectionId") long sectionId) {
        Optional<Individu> optionalIndividu = individuServiceImplement.recupererUnIndividu(individuId);
        if (optionalIndividu.isEmpty()) return "redirect:/admin/individus";
        Optional<Section> optionalSection = sectionServiceImplement.recupererUneSection(sectionId);
        if (optionalSection.isEmpty()) return "redirect:/admin/individus";
        model.addAttribute("individu", optionalIndividu.get());
        model.addAttribute("section", optionalSection.get());
        return "editSection";
    }

    @PostMapping(path = "editer-section")
    public String editingSection(@Valid @ModelAttribute("section") Section section, BindingResult bindingResult, long individuId, Model model) {
        Optional<Individu> optionalIndividu = individuServiceImplement.recupererUnIndividu(individuId);
        if (optionalIndividu.isEmpty()) return "redirect:/admin/individus";
        Individu individu = optionalIndividu.get();
        if (bindingResult.hasErrors()) {
            model.addAttribute("section", section);
            model.addAttribute("individu", individu);
            return "editSection";
        }
        sectionServiceImplement.modifierSection(section.getId(), section.getTitre(),section.getContenu(),section.getOrdreAffichage());
        return "redirect:/admin/"+individuId+"/sections";
    }

    @PostMapping(path = "supprimer-section/{id}")
    public String deleteSection(@PathVariable(name = "id") long id) {
        sectionServiceImplement.supprimerSection(id);
        return "redirect:/admin/"+id+"/sections";
    }

    @PostMapping("ajouter-enfants")
    public String addingEnfants(@ModelAttribute("intermediaire") Individu intermediaire, Model model, long individuId) {
        Optional<Femme> optionalFemme = individuServiceImplement.recupererUneFemme(individuId);
        if (optionalFemme.isEmpty()) return "redirect:/admin/individus";
        Femme femme = optionalFemme.get();
        individuServiceImplement.affecterEnfants(femme, intermediaire.getEnfants());
        return "redirect:/admin/" + individuId + "/enfants";
    }

    @GetMapping(path = "{id}/ajouter-epouse")
    public String ajouterEpouses(Model model, @PathVariable(name = "id") long id) {
        Optional<Homme> optionalHomme = individuServiceImplement.recupererUnHomme(id);
        if (optionalHomme.isEmpty()) return "";
        Homme individu = optionalHomme.get();
        Homme intermediaire = new Homme();
        intermediaire.setId(id);
        model.addAttribute("individu", individu);
        model.addAttribute("intermediaire", intermediaire);
        ArrayList<Femme> individus = (ArrayList<Femme>) individuServiceImplement.recupererFemmes();
        individus.removeAll(individu.getEpouses());
        for (int i = 0; i < individu.getEnfants().size(); i++) {
            if (individu.getEnfants().get(i).getKey_().startsWith("F"))
                individus.remove(((Femme) individu.getEnfants().get(i)));
        }
        model.addAttribute("epouses", individus);
        return "mariage";
    }

    @PostMapping("ajouter-epouses")
    public String addingEpouses(@ModelAttribute("intermediaire") Homme intermediaire, Model model, long individuId) {
        Optional<Homme> optionalHomme = individuServiceImplement.recupererUnHomme(individuId);
        if (optionalHomme.isEmpty()) return "redirect:/admin/individus";
        Homme femme = optionalHomme.get();
        individuServiceImplement.affecterEpouses(femme, intermediaire.getEpouses());
        return "redirect:/admin/" + individuId + "/epouses";
    }

    @GetMapping(path = "nouvel-individu")
    public String newIndividu(Model model, @RequestParam(name = "ajoute", required = false) Integer ajoute) {
        if (ajoute!=null) model.addAttribute("ajoute", ajoute);
        model.addAttribute("individu", new Individu());
        return "newIndividu";
    }

    @PostMapping(path = "nouvel-individu")
    public String adding(@NotEmpty @DateTimeFormat(pattern = "dd-mm-yyyy") String birthDate, @DateTimeFormat(pattern = "dd-mm-yyyy") String deathDate, @Valid @ModelAttribute("individu") Individu individu, BindingResult bindingResult, MultipartFile image, String genre, Model model) {
        if (genre == null || genre.isEmpty()) {
            bindingResult.addError(new ObjectError("genre", "Veuillez sélectionner un genre"));
            model.addAttribute("genreInvalid", true);
            model.addAttribute("genreInvalidMessage", "Veuillez sélectionner un genre");
        }
        if (birthDate == null || birthDate.isEmpty()) {
            bindingResult.addError(new ObjectError("date_naissance", "Veuillez donner une date de naissance"));
            model.addAttribute("dateInvalid", true);
            model.addAttribute("dateInvalidMessage", "Veuillez donner une date de naissance");
        } else if (LocalDate.parse(birthDate).isAfter(LocalDate.now())) {
            bindingResult.addError(new ObjectError("date_naissance", "Veuillez donner une date de naissance"));
            model.addAttribute("birthDateInvalid", true);
            model.addAttribute("birthDateInvalidMessage", "La date de naissance doit être inférieure à la date du jour.");
        }
        if (!deathDate.isEmpty()) {
            if (LocalDate.parse(deathDate).isAfter(LocalDate.now())) {
                bindingResult.addError(new ObjectError("date_deces", ""));
                model.addAttribute("deathDateInvalid", true);
                model.addAttribute("deathDateInvalidMessage", "La date de décès doit être inférieure à la date du jour.");
            } else if (!birthDate.isEmpty() && LocalDate.parse(deathDate).isBefore(LocalDate.parse(birthDate))) {
                bindingResult.addError(new ObjectError("date_deces", ""));
                model.addAttribute("deathDateInvalid", true);
                model.addAttribute("deathDateInvalidMessage", "La date de décès doit être supérieure à la date de naissance.");
            }
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("individu", individu);

            return "newIndividu";
        }
        individu.setDate_naissance(LocalDate.parse(birthDate));
        if (!deathDate.isEmpty()) {
            individu.setDate_deces(LocalDate.parse(deathDate));
        }
        Individu newIndividu;
        if (Objects.equals(genre, "H")) {
            newIndividu = new Homme(individu.getNom(), individu.getPrenom(), individu.getSurnom(), individu.getDate_naissance(), individu.getLieu_naissance(), individu.getResidence_actuelle());
        } else {
            newIndividu = new Femme(individu.getNom(), individu.getPrenom(), individu.getSurnom(), individu.getDate_naissance(), individu.getLieu_naissance(), individu.getResidence_actuelle());
        }
        if (individu.getDate_deces() != null)
            newIndividu.setDate_deces(individu.getDate_deces());
        if (individu.getLieu_deces() != null)
            newIndividu.setLieu_deces(individu.getLieu_deces());
        if (individu.getCause_deces() != null)
            newIndividu.setCause_deces(individu.getCause_deces());
        if (image != null && !image.isEmpty()) {
            try {
                newIndividu.setImageUrl(image.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        individuServiceImplement.enregistrerIndividu(newIndividu);
        return "redirect:/admin/nouvel-individu?ajoute=1";
    }

    @GetMapping(path = "editer-individu/{id}")
    public String editer(Model model, @PathVariable(name = "id") long id) {
        Optional<Individu> optionalIndividu = individuServiceImplement.recupererUnIndividu(id);
        if (optionalIndividu.isEmpty()) return "redirect:/admin/individus";
        Individu individu = optionalIndividu.get();
        model.addAttribute("individu", individu);
        return "editIndividu";
    }

    @PostMapping(path = "editer-individu")
    public String editing(@NotEmpty @DateTimeFormat(pattern = "dd-mm-yyyy") String birthDate, @DateTimeFormat(pattern = "dd-mm-yyyy") String deathDate, @Valid @ModelAttribute("individu") Individu individu, BindingResult bindingResult, MultipartFile image, Model model) {
        if (birthDate == null || birthDate.isEmpty()) {
            bindingResult.addError(new ObjectError("date_naissance", "Veuillez donner une date de naissance"));
            model.addAttribute("dateInvalid", true);
            model.addAttribute("dateInvalidMessage", "Veuillez donner une date de naissance");
        } else if (LocalDate.parse(birthDate).isAfter(LocalDate.now())) {
            bindingResult.addError(new ObjectError("date_naissance", "Veuillez donner une date de naissance"));
            model.addAttribute("birthDateInvalid", true);
            model.addAttribute("birthDateInvalidMessage", "La date de naissance doit être inférieure à la date du jour.");
        }
        if (deathDate == null || deathDate.isEmpty()) {

        } else {
            if (LocalDate.parse(deathDate).isAfter(LocalDate.now())) {
                bindingResult.addError(new ObjectError("date_deces", ""));
                model.addAttribute("deathDateInvalid", true);
                model.addAttribute("deathDateInvalidMessage", "La date de décès doit être inférieure à la date du jour.");
            } else {
                //assert birthDate != null : "controller editing birthdate is null";
                if (!(birthDate != null && birthDate.isEmpty()) && LocalDate.parse(deathDate).isBefore(LocalDate.parse(birthDate))) {
                    bindingResult.addError(new ObjectError("date_deces", ""));
                    model.addAttribute("deathDateInvalid", true);
                    model.addAttribute("deathDateInvalidMessage", "La date de décès doit être supérieure à la date de naissance.");
                } else {
                    individu.setDate_naissance(LocalDate.parse(birthDate));
                    individu.setDate_deces(LocalDate.parse(deathDate));
                }
            }
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("individu", individu);

            return "editIndividu";
        }
        if (image != null && !image.isEmpty()) {
            try {
                individu.setImageUrl(image.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        individuServiceImplement.modifierIndividu(individu);
        return "redirect:/admin/individus";
    }

    @GetMapping(path = "supprimer-individu/{id}")
    public String supprimer(@PathVariable(name = "id") long id) {
        individuServiceImplement.supprimerIndividu(id);
        return "redirect:/admin/individus";
    }
}

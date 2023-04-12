package com.api.sassila.service;

import com.api.sassila.modele.Femme;
import com.api.sassila.modele.Homme;
import com.api.sassila.modele.Individu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IndividuService {
    boolean enregistrerIndividu(Individu individu);

    List<Individu> recupererIndividus();

    Page<Individu> recupererIndividusPage(Pageable pageable);

    Page<Individu> recupererIndividusPageParNomEtPrenom(Pageable pageable, String nom, String prenom);

    List<Individu> recupererIndividusRecent();

    List<Homme> recupererHommes();

    List<Femme> recupererFemmes();

    Optional<Individu> recupererUnIndividu(Long id);

    Optional<Homme> recupererUnHomme(long id);

    Optional<Femme> recupererUneFemme(long id);

    List<Individu> recupererSansParents();

    void affecterEnfants(Individu individu, List<Individu> enfants);

    void affecterEnfants(Individu individu, List<Individu> enfants, Individu conjoint);

    void retirerEnfant(Individu individu, Individu enfant);

    void divorcer(Homme epoux, Femme epouse);

    void affecterEpouses(Homme homme, List<Femme> epouses);

    List<Individu> recupererEnfants();

    boolean supprimerIndividu(Long id);

    boolean modifierIndividu(Individu edited);
    long countIndividus();

    long countHommes();

    long countFemmes();
}

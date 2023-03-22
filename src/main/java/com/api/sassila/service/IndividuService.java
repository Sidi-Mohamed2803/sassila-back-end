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

    List<Individu> recupererHommes();

    List<Femme> recupererFemmes();

    Optional<Individu> recupererUnIndividu(Long id);

    Optional<Homme> recupererUnHomme(long id);

    Optional<Femme> recupererUneFemme(long id);

    void affecterEnfants(Femme femme, List<Individu> enfants);

    void affecterEpouses(Homme homme, List<Femme> epouses);

    List<Individu> recupererEnfants();

    boolean supprimerIndividu(Long id);

    boolean modifierIndividu(Individu edited);
    long countIndividus();

    long countHommes();

    long countFemmes();
}

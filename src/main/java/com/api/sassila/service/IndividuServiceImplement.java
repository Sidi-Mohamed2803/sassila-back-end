package com.api.sassila.service;

import com.api.sassila.customRepo.CustomRepository;
import com.api.sassila.modele.Femme;
import com.api.sassila.modele.Homme;
import com.api.sassila.modele.Individu;
import com.api.sassila.repository.FemmeRepository;
import com.api.sassila.repository.HommeRepository;
import com.api.sassila.repository.IndividuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class IndividuServiceImplement implements IndividuService{
    private final IndividuRepository individuRepository;
    private final FemmeRepository femmeRepository;
    private final HommeRepository hommeRepository;

    public String newKey(Individu individu){
        String genre = String.valueOf(individu.getKey_().charAt(0));

        String[] prenoms = individu.getPrenom().split(" ");
        String jour = individu.getDate_naissance().getDayOfMonth() <10 ? "0"+individu.getDate_naissance().getDayOfMonth() : String.valueOf(individu.getDate_naissance().getDayOfMonth());
        String mois = individu.getDate_naissance().getMonthValue() <10 ? "0"+individu.getDate_naissance().getMonthValue() : String.valueOf(individu.getDate_naissance().getMonthValue());
        individu.setKey_(genre
                + jour
                + mois
                + individu.getDate_naissance().getYear()
                + individu.getLieu_naissance().toUpperCase().substring(0,3));
        for (String p :
                prenoms) {
            individu.setKey_(individu.getKey_() + p.toUpperCase().substring(0,2));
        }
        return individu.getKey_() + individu.getNom().toUpperCase().substring(0,2);
    }

    @Override
    public boolean enregistrerIndividu(Individu individu) {
        individuRepository.save(individu);
        return true;
    }

    @Override
    public List<Individu> recupererIndividus() {
        return individuRepository.findAll();
    }

    @Override
    public Page<Individu> recupererIndividusPage(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Individu> list;
        List<Individu> individus = individuRepository.findAll();

        if(individus.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, individus.size());
            list = individus.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage,pageSize),individus.size());
    }

    @Override
    public List<Individu> recupererHommes() {
        return individuRepository.findAllHommes();
    }

    @Override
    public List<Femme> recupererFemmes() {
        return femmeRepository.findAll();
    }

    @Override
    public Optional<Individu> recupererUnIndividu(Long id) {
        return individuRepository.findById(id);
    }

    @Override
    public Optional<Homme> recupererUnHomme(long id) {
        return hommeRepository.findById(id);
    }

    @Override
    public Optional<Femme> recupererUneFemme(long id) {
        return femmeRepository.findById(id);
    }

    @Override
    public void affecterEnfants(Femme femme, List<Individu> enfants) {
        for (Individu enfant :
                enfants) {
            if (!femme.getEnfants().contains(enfant))
                femme.addChild(enfant);
        }
        enregistrerIndividu(femme);
        if (femme.getEpoux() != null) enregistrerIndividu(femme.getEpoux());
    }

    @Override
    public void affecterEpouses(Homme homme, List<Femme> epouses) {
        for (Femme epouse :
                epouses) {
            if (!homme.getEpouses().contains(epouse)) {
                homme.epouser(epouse);
                enregistrerIndividu(epouse);
            }
        }
        enregistrerIndividu(homme);
    }

    @Override
    public List<Individu> recupererEnfants() {
        return individuRepository.findAllWithoutParents();
    }

    @Override
    @Transactional
    public boolean supprimerIndividu(Long id) {
        Optional<Individu> optionalIndividu = individuRepository.findById(id);
        if (optionalIndividu.isEmpty()) return false;
        Individu individu = optionalIndividu.get();
//        if (individu.getKey_().startsWith("F")) {
//            femmeRepository.deleteById(id);
//        }
//        if (individu.getKey_().startsWith("H")) {
//            CustomRepository.deleteMariageRelation(individu.getKey_());
//            hommeRepository.deleteById(id);
//        }
//        CustomRepository.deleteChildrenRelation(individu.getKey_());
        individuRepository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean modifierIndividu(Individu edited) {
        boolean exists = individuRepository.existsById(edited.getId());
        if(!exists) {
            return false;
        }
        Optional<Individu> optionalIndividu = individuRepository.findById(edited.getId());
        if (optionalIndividu.isEmpty()) return false;
        Individu individu = optionalIndividu.get();
        if (edited.getPrenom() !=null && edited.getPrenom().length()>0 && !edited.getPrenom().equals(individu.getPrenom())) individu.setPrenom(edited.getPrenom());
        if (edited.getNom() !=null && edited.getNom().length()>0 && !edited.getNom().equals(individu.getNom())) individu.setNom(edited.getNom());
        if (edited.getSurnom() !=null && edited.getSurnom().length()>0 && !edited.getSurnom().equals(individu.getSurnom())) individu.setSurnom(edited.getSurnom());
        if (edited.getDate_deces() !=null && !edited.getDate_deces().isEqual(individu.getDate_deces())) individu.setDate_deces(edited.getDate_deces());
        if (edited.getResidence_actuelle() !=null && edited.getResidence_actuelle().length()>0 && !edited.getResidence_actuelle().equals(individu.getResidence_actuelle())) individu.setResidence_actuelle(edited.getResidence_actuelle());
        if (edited.getLieu_deces() !=null && !edited.getLieu_deces().equals(individu.getLieu_deces())) individu.setLieu_deces(edited.getLieu_deces());
        if (edited.getCause_deces() !=null && !edited.getCause_deces().equals(individu.getCause_deces())) individu.setCause_deces(edited.getCause_deces());
        if (edited.getImageUrl() !=null && edited.getImageUrl().length>0 && !Arrays.equals(edited.getImageUrl(), individu.getImageUrl())) {
            individu.setImageUrl(edited.getImageUrl());
        }

        individu.setKey_(this.newKey(individu));
        individuRepository.save(individu);
        return true;
    }

    @Override
    public long countIndividus() {
        return individuRepository.count();
    }

    @Override
    public long countHommes() {
        return hommeRepository.count();
    }

    @Override
    public long countFemmes() {
        return femmeRepository.count();
    }


}

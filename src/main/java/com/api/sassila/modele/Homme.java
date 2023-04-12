package com.api.sassila.modele;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@DiscriminatorValue("H")
@NoArgsConstructor
@OnDelete(action = OnDeleteAction.CASCADE)
//@Table(name = "Homme")
public class Homme extends Individu {
    @JsonManagedReference
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY, mappedBy = "epoux")
    private List<Femme> epouses = new ArrayList<>();

    public Homme(String nom, String prenom, String surnom, LocalDate date_naissance, String lieu_naissance, String residence_actuelle) {
        super(nom, prenom, surnom, date_naissance, lieu_naissance, residence_actuelle);
        String[] prenoms = this.getPrenom().split(" ");
        String jour = this.getDate_naissance().getDayOfMonth() <10 ? "0"+this.getDate_naissance().getDayOfMonth() : String.valueOf(this.getDate_naissance().getDayOfMonth());
        String mois = this.getDate_naissance().getMonthValue() <10 ? "0"+this.getDate_naissance().getMonthValue() : String.valueOf(this.getDate_naissance().getMonthValue());
        this.setKey_("H"
                + jour
                + mois
                + this.getDate_naissance().getYear()
                + this.getLieu_naissance().toUpperCase().substring(0,3));
        for (String p :
                prenoms) {
            this.setKey_(this.getKey_() + p.toUpperCase().substring(0,2));
        }
        this.setKey_(this.getKey_() + this.getNom().toUpperCase().substring(0,2));
    }

    public Homme(String nom, String prenom, String surnom, LocalDate date_naissance, String lieu_naissance, String residence_actuelle, LocalDate date_deces, String lieu_deces, String cause_deces) {
        super(nom, prenom, surnom, date_naissance, lieu_naissance, residence_actuelle, date_deces, lieu_deces, cause_deces);
        String[] prenoms = this.getPrenom().split(" ");
        String jour = this.getDate_naissance().getDayOfMonth() <10 ? "0"+this.getDate_naissance().getDayOfMonth() : String.valueOf(this.getDate_naissance().getDayOfMonth());
        String mois = this.getDate_naissance().getMonthValue() <10 ? "0"+this.getDate_naissance().getMonthValue() : String.valueOf(this.getDate_naissance().getMonthValue());
        this.setKey_("H"
                + jour
                + mois
                + this.getDate_naissance().getYear()
                + this.getLieu_naissance().toUpperCase().substring(0,3));
        for (String p :
                prenoms) {
            this.setKey_(this.getKey_() + p.toUpperCase().substring(0,2));
        }
        this.setKey_(this.getKey_() + this.getNom().toUpperCase().substring(0,2));
    }

    public List<Femme> getEpouses() {
        return epouses;
    }

    public void setEpouses(ArrayList<Femme> epouses) {
        this.epouses = epouses;
    }

    public void epouser(Femme epouse){
        if (this.getEpouses().size()<4 && !this.getEpouses().contains(epouse)) {
            this.getEpouses().add(epouse);
            epouse.setEpoux(this);
        }
    }

    public void divorcer(Femme epouse) {
        if (epouse!=null) {
            this.getEpouses().remove(epouse);
            epouse.divorce(this);
        }
    }

    @Override
    public String toString() {
        return "Homme{" +
                "\n\tid=" + getId() +
                "\n\tkey_='" + getKey_() + '\'' +
                "\n\tnom='" + getNom() + '\'' +
                "\n\tprenom='" + getPrenom() + '\'' +
                "\n\tsurnom='" + getSurnom() + '\'' +
                "\n\tdate_naissance=" + getDate_naissance() +
                "\n\tlieu_naissance='" + getLieu_naissance() + '\'' +
                "\n\tresidence_actuelle='" + getResidence_actuelle() + '\'' +
                "\n\tdate_deces=" + getDate_deces() +
                "\n\tlieu_deces='" + getLieu_deces() + '\'' +
                "\n\tcause_deces='" + getCause_deces() + '\'' +
                "\n\tepouses=" + getEpouses() +
                '}';
    }
}
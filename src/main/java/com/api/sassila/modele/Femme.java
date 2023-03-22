package com.api.sassila.modele;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Arrays;

@Entity
@DiscriminatorValue("F")
@NoArgsConstructor
@OnDelete(action = OnDeleteAction.CASCADE)
//@Table(name = "Femme")
public class Femme extends Individu{
    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private Homme epoux;

    public Homme getEpoux() {
        return epoux;
    }

    public void setEpoux(Homme epoux) {
        if(epoux.getEpouses().size()<4) {
            this.epoux = epoux;
            epoux.epouser(this);
        }
    }

    public void addChild(Individu child){
        if (child == null || this.getEnfants().contains(child)) {
            return;
        }
        this.getEnfants().add(child);
        if (this.getEpoux()!=null) {
            this.getEpoux().getEnfants().add(child);
        }
    }

    public Femme(String nom, String prenom, String surnom, LocalDate date_naissance, String lieu_naissance, String residence_actuelle) {
        super(nom, prenom, surnom, date_naissance, lieu_naissance, residence_actuelle);
        String[] prenoms = this.getPrenom().split(" ");
        String jour = this.getDate_naissance().getDayOfMonth() <10 ? "0"+this.getDate_naissance().getDayOfMonth() : String.valueOf(this.getDate_naissance().getDayOfMonth());
        String mois = this.getDate_naissance().getMonthValue() <10 ? "0"+this.getDate_naissance().getMonthValue() : String.valueOf(this.getDate_naissance().getMonthValue());
        this.setKey_("F"
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

    public Femme(String nom, String prenom, String surnom, LocalDate date_naissance, String lieu_naissance, String residence_actuelle, LocalDate date_deces, String lieu_deces, String cause_deces) {
        super(nom, prenom, surnom, date_naissance, lieu_naissance, residence_actuelle, date_deces, lieu_deces, cause_deces);
        String[] prenoms = this.getPrenom().split(" ");
        String jour = this.getDate_naissance().getDayOfMonth() <10 ? "0"+this.getDate_naissance().getDayOfMonth() : String.valueOf(this.getDate_naissance().getDayOfMonth());
        String mois = this.getDate_naissance().getMonthValue() <10 ? "0"+this.getDate_naissance().getMonthValue() : String.valueOf(this.getDate_naissance().getMonthValue());
        this.setKey_("F"
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

    @Override
    public String toString() {
        return "Femme{" +
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
                "\n\tenfants=" + getEnfants() +"\n"+
                '}';
    }
}
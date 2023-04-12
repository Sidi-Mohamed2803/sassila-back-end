package com.api.sassila.modele;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "genre")
//@IdClass(IndividuID.class)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Individu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private long id;
    private String key_;
    @NotNull(message = "Ce champ ne doit pas être null")
    @NotEmpty(message = "Ce champ ne doit pas être vide")
    private String nom;
    @NotNull(message = "Ce champ ne doit pas être null")
    @NotEmpty(message = "Ce champ ne doit pas être vide")
    private String prenom;
    private String surnom;
    private LocalDate date_naissance;
    @NotNull(message = "Ce champ ne doit pas être null")
    @NotEmpty(message = "Ce champ ne doit pas être vide")
    private String lieu_naissance;
    @NotNull(message = "Ce champ ne doit pas être null")
    @NotEmpty(message = "Ce champ ne doit pas être vide")
    private String residence_actuelle;
    private LocalDate date_deces;
    private String lieu_deces;
    private String cause_deces;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] imageUrl;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<Individu> enfants = new ArrayList<Individu>();

    public Individu(String nom, String prenom, String surnom, LocalDate date_naissance, String lieu_naissance, String residence_actuelle) {
        this.nom = nom;
        this.prenom = prenom;
        this.surnom = surnom;
        this.date_naissance = date_naissance;
        this.lieu_naissance = lieu_naissance;
        this.residence_actuelle = residence_actuelle;
    }

    public Individu(String nom, String prenom, String surnom, LocalDate date_naissance, String lieu_naissance, String residence_actuelle, LocalDate date_deces, String lieu_deces, String cause_deces) {
        this.nom = nom;
        this.prenom = prenom;
        this.surnom = surnom;
        this.date_naissance = date_naissance;
        this.lieu_naissance = lieu_naissance;
        this.residence_actuelle = residence_actuelle;
        this.date_deces = date_deces;
        this.lieu_deces = lieu_deces;
        this.cause_deces = cause_deces;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKey_() {
        return key_;
    }

    public void setKey_(String key_) {
        this.key_ = key_;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getSurnom() {
        return surnom;
    }

    public void setSurnom(String surnom) {
        this.surnom = surnom;
    }

    public LocalDate getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(LocalDate date_naissance) {
        this.date_naissance = date_naissance;
    }

    public String getLieu_naissance() {
        return lieu_naissance;
    }

    public void setLieu_naissance(String lieu_naissance) {
        this.lieu_naissance = lieu_naissance;
    }

    public String getResidence_actuelle() {
        return residence_actuelle;
    }

    public void setResidence_actuelle(String residence_actuelle) {
        this.residence_actuelle = residence_actuelle;
    }

    public LocalDate getDate_deces() {
        return date_deces;
    }

    public void setDate_deces(LocalDate date_deces) {
        this.date_deces = date_deces;
    }

    public String getLieu_deces() {
        return lieu_deces;
    }

    public void setLieu_deces(String lieu_deces) {
        this.lieu_deces = lieu_deces;
    }

    public String getCause_deces() {
        return cause_deces;
    }

    public void setCause_deces(String cause_deces) {
        this.cause_deces = cause_deces;
    }

    public byte[] getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(byte[] imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Individu> getEnfants() {
        return enfants;
    }

    public void setEnfants(List<Individu> enfants) {
        this.enfants = enfants;
    }

    public void ajouterEnfantIndependant(Individu enfant){
        if (!this.getEnfants().contains(enfant)) this.getEnfants().add(enfant);
    }

    public void retirerEnfant(Individu enfant) {
        this.enfants.remove(enfant);
    }

    @Override
    public String toString() {
        return "Individu{" +
                "\n\tid=" + id +
                "\n\tkey_='" + key_ + '\'' +
                "\n\tnom='" + nom + '\'' +
                "\n\tprenom='" + prenom + '\'' +
                "\n\tsurnom='" + surnom + '\'' +
                "\n\tdate_naissance=" + date_naissance +
                "\n\tlieu_naissance='" + lieu_naissance + '\'' +
                "\n\tresidence_actuelle='" + residence_actuelle + '\'' +
                "\n\tdate_deces=" + date_deces +
                "\n\tlieu_deces='" + lieu_deces + '\'' +
                "\n\tcause_deces='" + cause_deces + '\'' +
                "\n\tenfants=" + enfants +"\n"+
                '}';
    }
}

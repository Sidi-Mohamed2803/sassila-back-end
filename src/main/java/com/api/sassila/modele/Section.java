package com.api.sassila.modele;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Setter @Getter
@NoArgsConstructor
public class Section implements Serializable {
    @Id
    @SequenceGenerator(
            name = "section_sequence",
            sequenceName = "mySectionSeq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "section_sequence"
    )
    private Long id;
    @NotEmpty(message = "Ce champ ne doit pas être vide.")
    private String titre;
    @NotEmpty(message = "Ce champ ne doit pas être vide.")
    private String contenu;
    private String imageUrl;
    @NotNull(message = "Ce champ ne doit pas être vide.")
    @Min(value = 1, message = "Veuillez renseigner un nombre strictement positif. (Ordre d'affichage > 0)")
    private int ordreAffichage;

    @ManyToOne(fetch = FetchType.LAZY)
    private Individu individu;

    public Section(String titre, String contenu, String imageUrl, int ordreAffichage, Individu individu) {
        this.titre = titre;
        this.contenu = contenu;
        this.imageUrl = imageUrl;
        this.ordreAffichage = ordreAffichage;
        this.individu = individu;
    }

    @Override
    public String toString() {
        return "Section{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", contenu='" + contenu + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", ordre_affichage=" + ordreAffichage +
                ", individu=" + individu.getKey_() +
                '}';
    }
}

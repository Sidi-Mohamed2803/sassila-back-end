package com.api.sassila.service;

import com.api.sassila.modele.Section;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SectionService {
    void ajouterSection(Section section, long individuId);
    void supprimerSection(Long id);
    List<Section> recupererSectionsParIndividu(Long id);
    Optional<Section> recupererUneSection(long id);
    void modifierSection(Long id, String titre, String contenu, int ordre);
}

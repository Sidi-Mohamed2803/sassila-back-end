package com.api.sassila.service;

import com.api.sassila.modele.Individu;
import com.api.sassila.modele.Section;
import com.api.sassila.repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SectionServiceImplement implements SectionService{
    private final SectionRepository sectionRepository;
    private final IndividuServiceImplement individuServiceImplement;

    @Override
    public void ajouterSection(Section section, long individuId) {
        Optional<Individu> optionalIndividu = individuServiceImplement.recupererUnIndividu(individuId);
        if (optionalIndividu.isEmpty()) return;
        section.setIndividu(optionalIndividu.get());
        sectionRepository.save(section);
    }

    @Override
    public void supprimerSection(Long id) {
        sectionRepository.deleteById(id);
    }

    @Override
    public List<Section> recupererSectionsParIndividu(Long id) {
        Optional<Individu> optionalIndividu =  individuServiceImplement.recupererUnIndividu(id);
        return optionalIndividu.map(sectionRepository::findAllByIndividuOrderByOrdreAffichageAsc).orElse(null);

//        Same as:
//        return optionalIndividu.map(individu -> sectionRepository.findAllByIndividuOrderByOrdreAffichageAsc(individu)).orElse(null);

//        or:

//        if (optionalIndividu.isEmpty()) return null;
//        return sectionRepository.findAllByIndividuOrderByOrdreAffichageAsc(optionalIndividu.get());
    }

    @Override
    public Optional<Section> recupererUneSection(long id) {
        return sectionRepository.findById(id);
    }

    @Transactional
    @Override
    public void modifierSection(Long id, String titre, String contenu, int ordre) {
        Optional<Section> optionalSection = sectionRepository.findById(id);
        if (optionalSection.isEmpty()) return;

        Section section = optionalSection.get();
        if (titre!=null && titre.length()>0 && !titre.equals(section.getTitre())) section.setTitre(titre);
        if (contenu!=null && contenu.length()>0 && !contenu.equals(section.getContenu())) section.setContenu(contenu);
        if (ordre>0  && ordre != section.getOrdreAffichage()) section.setOrdreAffichage(ordre);
    }

    //    @Transactional
//    public void updateStudent(Long id, String name, String email) {
//        boolean exists = studentRepository.existsById(id);
//        if(!exists) {
//            throw new IllegalStateException("student with id "+id+" does not exists");
//        }
//        Student s = studentRepository.getById(id);
//        if(name!=null && name.length()>0 && !name.equals(s.getName()))
//            s.setName(name);
//
//        if(email!=null && email.length()>0 && !email.equals(s.getEmail())) {
//            Optional<Student> optional = studentRepository.findStudentByEmail(email);
//            if (optional.isPresent())
//                throw new IllegalStateException("Email taken");
//            s.setEmail(email);
//        }
//    }
}

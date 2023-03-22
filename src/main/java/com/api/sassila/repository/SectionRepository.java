package com.api.sassila.repository;

import com.api.sassila.modele.Individu;
import com.api.sassila.modele.Section;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.*;
import java.util.List;

public interface SectionRepository extends JpaRepository<Section,Long> {

    @Query(value = "SELECT * FROM Section s WHERE s.individu_key_ = :key", nativeQuery = true)
    List<Section> findByIndividu_key_OrderByOrdreAffichageAsc(@Param("key") String individu_key_);

    List<Section> findAllByIndividuOrderByOrdreAffichageAsc(Individu individu);
}

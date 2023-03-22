package com.api.sassila.repository;

import com.api.sassila.modele.Femme;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FemmeRepository extends IndividuBaseRepository<Femme> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Femme WHERE Femme.key_ = ?1", nativeQuery = true)
    void deleteForeignKey(String key);
}

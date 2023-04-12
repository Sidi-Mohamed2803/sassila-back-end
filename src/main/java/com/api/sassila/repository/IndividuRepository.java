package com.api.sassila.repository;

import com.api.sassila.modele.Individu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IndividuRepository extends JpaRepository<Individu, Long> {

    @Query(value = "SELECT * FROM Individu where key_ like 'H%'", nativeQuery = true)
    List<Individu> findAllHommes();

    @Query(value = "SELECT * FROM individu WHERE individu.id NOT IN (SELECT enfants_id FROM individu_enfants)", nativeQuery = true)
    List<Individu> findAllWithoutParents();

    List<Individu> findTop10ByOrderByIdDesc();

    List<Individu> findByPrenomAndNomContainsIgnoreCase(String prenom, String nom);

    List<Individu> findByPrenomContainingIgnoreCase(String prenom);

    List<Individu> findByNomContainingIgnoreCase(String nom);

}

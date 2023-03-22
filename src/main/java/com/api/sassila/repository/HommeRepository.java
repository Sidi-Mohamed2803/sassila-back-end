package com.api.sassila.repository;

import com.api.sassila.modele.Homme;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface HommeRepository extends IndividuBaseRepository<Homme> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Homme WHERE Homme.key_ = ?1", nativeQuery = true)
    void deleteForeignKey(String key);
}

package com.api.sassila.repository;

import com.api.sassila.modele.Individu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IndividuBaseRepository<T extends Individu> extends JpaRepository<T, Long> {

}

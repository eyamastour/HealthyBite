package com.esprit.pidev.repository.ConseilRecette;

import com.esprit.pidev.entities.ConseilRecette.Conseil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConseilRepository extends JpaRepository<Conseil,Long> {
}

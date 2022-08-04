package org.vaadin.covid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.covid.jpa.landkreis.LandkreisG;

public interface LandkreisGRepository extends JpaRepository<LandkreisG, Long> {
}
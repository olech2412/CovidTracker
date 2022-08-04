package org.vaadin.covid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.covid.jpa.landkreis.LandkreisMS;

public interface LandkreisMSRepository extends JpaRepository<LandkreisMS, Long> {
}
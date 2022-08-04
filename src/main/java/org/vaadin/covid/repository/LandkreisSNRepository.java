package org.vaadin.covid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.covid.jpa.landkreis.LandkreisSN;

public interface LandkreisSNRepository extends JpaRepository<LandkreisSN, Long> {
}
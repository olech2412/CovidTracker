package org.vaadin.covid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.covid.jpa.landkreis.LandkreisKfSR;

public interface LandkreisKfSRRepository extends JpaRepository<LandkreisKfSR, Long> {
}
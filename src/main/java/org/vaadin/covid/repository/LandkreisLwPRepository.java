package org.vaadin.covid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.covid.jpa.landkreis.LandkreisLwP;

public interface LandkreisLwPRepository extends JpaRepository<LandkreisLwP, Long> {
}
package org.vaadin.covid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.covid.jpa.landkreis.LandkreisNwm;

public interface LandkreisNwmRepository extends JpaRepository<LandkreisNwm, Long> {
}
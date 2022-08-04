package org.vaadin.covid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.covid.jpa.landkreis.LandkreisR;

public interface LandkreisRRepository extends JpaRepository<LandkreisR, Long> {
}
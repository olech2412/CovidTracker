package org.vaadin.covid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.covid.jpa.landkreis.LandkreisVR;

public interface LandkreisVRRepository extends JpaRepository<LandkreisVR, Long> {
}
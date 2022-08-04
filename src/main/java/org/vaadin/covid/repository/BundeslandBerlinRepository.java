package org.vaadin.covid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vaadin.covid.jpa.bundesland.BundeslandBerlin;

@Repository
public interface BundeslandBerlinRepository extends JpaRepository<BundeslandBerlin, Long> {
}

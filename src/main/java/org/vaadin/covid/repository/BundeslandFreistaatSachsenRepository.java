package org.vaadin.covid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.covid.jpa.bundesland.BundeslandFreistaatSachsen;

import java.time.LocalDate;

public interface BundeslandFreistaatSachsenRepository extends JpaRepository<BundeslandFreistaatSachsen, Long> {
    BundeslandFreistaatSachsen findByCreationDate(LocalDate creationDate);
}
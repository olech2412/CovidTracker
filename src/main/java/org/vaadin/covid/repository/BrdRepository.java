package org.vaadin.covid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.covid.jpa.Brd;

import java.util.List;

public interface BrdRepository extends JpaRepository<Brd, Long> {
}
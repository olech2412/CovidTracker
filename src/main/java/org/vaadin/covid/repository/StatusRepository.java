package org.vaadin.covid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.covid.jpa.Status;

import java.time.LocalDate;
import java.util.List;

public interface StatusRepository extends JpaRepository<Status, Long> {

    List<Status> findAllByCreationDate(LocalDate creationDate);

    Status findFirstByCreationDateBefore(LocalDate creationDate);
}
package org.vaadin.covid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vaadin.covid.jpa.authentification.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
}

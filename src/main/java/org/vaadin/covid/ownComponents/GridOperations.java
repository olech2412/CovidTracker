package org.vaadin.covid.ownComponents;

import org.vaadin.covid.jpa.authentification.Users;

public interface GridOperations {
    void save(Object saveElement);

    void delete(Object deleteElement);

    void update(Object updateElement);
}

package org.vaadin.covid.jpa;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "lastupdate", nullable = false)
    private String lastUpdate;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "creationdate", nullable = false)
    private LocalDate creationDate;

    public Status(String lastUpdate, String status, LocalDate creationDate) {
        this.lastUpdate = lastUpdate;
        this.status = status;
        this.creationDate = creationDate;
    }

    public Status() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}

package org.vaadin.covid.jpa.bundesland;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class BundeslandFreistaatSachsen {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "cases7per100k", nullable = false)
    private Double cases7per100k;

    @Column(name = "cases7", nullable = false)
    private Integer cases7;

    @Column(name = "cases100k", nullable = false)
    private Double cases100k;

    @Column(name = "death", nullable = false)
    private Integer death;

    @Column(name = "death7", nullable = false)
    private Integer death7;

    @Column(name = "cases", nullable = false)
    private Integer cases;

    @Column(name = "lastupdate", nullable = false)
    private LocalDateTime lastUpdate;

    @Column(name = "creationdate", nullable = false)
    private LocalDate creationDate;

    public BundeslandFreistaatSachsen(Double cases7per100k, Integer cases7, Double cases100k, Integer death, Integer death7, Integer cases, LocalDateTime lastUpdate, LocalDate creationDate) {
        this.cases7per100k = cases7per100k;
        this.cases7 = cases7;
        this.cases100k = cases100k;
        this.death = death;
        this.death7 = death7;
        this.cases = cases;
        this.lastUpdate = lastUpdate;
        this.creationDate = creationDate;
    }

    public BundeslandFreistaatSachsen() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCases7per100k() {
        return cases7per100k;
    }

    public void setCases7per100k(Double cases7per100k) {
        this.cases7per100k = cases7per100k;
    }

    public Integer getCases7() {
        return cases7;
    }

    public void setCases7(Integer cases7) {
        this.cases7 = cases7;
    }

    public Double getCases100k() {
        return cases100k;
    }

    public void setCases100k(Double cases100k) {
        this.cases100k = cases100k;
    }

    public Integer getDeath() {
        return death;
    }

    public void setDeath(Integer death) {
        this.death = death;
    }

    public Integer getDeath7() {
        return death7;
    }

    public void setDeath7(Integer death7) {
        this.death7 = death7;
    }

    public Integer getCases() {
        return cases;
    }

    public void setCases(Integer cases) {
        this.cases = cases;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}

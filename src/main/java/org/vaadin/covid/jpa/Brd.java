package org.vaadin.covid.jpa;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Brd {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "casestotally", nullable = false)
    private Integer casestotally;

    @Column(name = "casesnew", nullable = false)
    private Integer casesnew;

    @Column(name = "deathstotally", nullable = false)
    private Integer deathstotally;

    @Column(name = "deathsnew", nullable = false)
    private Integer deathsnew;

    @Column(name = "activenew", nullable = false)
    private Integer activenew;

    @Column(name = "activetotally", nullable = false)
    private Integer activetotally;

    @Column(name = "incidence7days", nullable = false)
    private Double incidence7days;

    @Column(name = "cases7days", nullable = false)
    private Integer cases7days;

    @Column(name = "recoveredtotally", nullable = false)
    private Integer recoveredtotally;

    @Column(name = "recoverednew", nullable = false)
    private Integer revorednew;

    @Column(name = "creationdate", nullable = false)
    private LocalDate creationDate;

    public Brd(Integer casestotally, Integer casesnew, Integer deathstotally, Integer deathsnew, Integer activenew, Integer activetotally, Double incidence7days, Integer cases7days, Integer recoveredtotally, Integer revorednew, LocalDate creationDate) {
        this.casestotally = casestotally;
        this.casesnew = casesnew;
        this.deathstotally = deathstotally;
        this.deathsnew = deathsnew;
        this.activenew = activenew;
        this.activetotally = activetotally;
        this.incidence7days = incidence7days;
        this.cases7days = cases7days;
        this.recoveredtotally = recoveredtotally;
        this.revorednew = revorednew;
        this.creationDate = creationDate;
    }

    public Brd() {

    }

    public Integer getCasestotally() {
        return casestotally;
    }

    public void setCasestotally(Integer casestotally) {
        this.casestotally = casestotally;
    }

    public Integer getCasesnew() {
        return casesnew;
    }

    public void setCasesnew(Integer casesnew) {
        this.casesnew = casesnew;
    }

    public Integer getDeathstotally() {
        return deathstotally;
    }

    public void setDeathstotally(Integer deathstotally) {
        this.deathstotally = deathstotally;
    }

    public Integer getDeathsnew() {
        return deathsnew;
    }

    public void setDeathsnew(Integer deathsnew) {
        this.deathsnew = deathsnew;
    }

    public Integer getActivenew() {
        return activenew;
    }

    public void setActivenew(Integer activenew) {
        this.activenew = activenew;
    }

    public Integer getActivetotally() {
        return activetotally;
    }

    public void setActivetotally(Integer activetotally) {
        this.activetotally = activetotally;
    }

    public Double getIncidence7days() {
        return incidence7days;
    }

    public void setIncidence7days(Double incidence7days) {
        this.incidence7days = incidence7days;
    }

    public Integer getCases7days() {
        return cases7days;
    }

    public void setCases7days(Integer cases7days) {
        this.cases7days = cases7days;
    }

    public Integer getRecoveredtotally() {
        return recoveredtotally;
    }

    public void setRecoveredtotally(Integer recoveredtotally) {
        this.recoveredtotally = recoveredtotally;
    }

    public Integer getRevorednew() {
        return revorednew;
    }

    public void setRevorednew(Integer revorednew) {
        this.revorednew = revorednew;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

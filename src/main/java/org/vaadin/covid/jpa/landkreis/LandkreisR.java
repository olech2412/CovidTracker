package org.vaadin.covid.jpa.landkreis;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class LandkreisR {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "casesperpopulation", nullable = false)
    private Double casesPerPopulation;

    @Column(name = "cases7per100k", nullable = false)
    private Double cases7per100k;

    @Column(name = "cases7", nullable = false)
    private Integer cases7;

    @Column(name = "casesper100k", nullable = false)
    private Double casesPer100k;

    @Column(name = "death7", nullable = false)
    private Integer death7;

    @Column(name = "cases", nullable = false)
    private Integer cases;

    @Column(name = "creationdate", nullable = false)
    private LocalDate creationDate;

    public LandkreisR(Double casesPerPopulation, Double cases7per100k, Integer cases7, Double casesPer100k, Integer death7, Integer cases, LocalDate creationDate) {
        this.casesPerPopulation = casesPerPopulation;
        this.cases7per100k = cases7per100k;
        this.cases7 = cases7;
        this.casesPer100k = casesPer100k;
        this.death7 = death7;
        this.cases = cases;
        this.creationDate = creationDate;
    }

    public LandkreisR() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCasesPerPopulation() {
        return casesPerPopulation;
    }

    public void setCasesPerPopulation(Double casesPerPopulation) {
        this.casesPerPopulation = casesPerPopulation;
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

    public Double getCasesPer100k() {
        return casesPer100k;
    }

    public void setCasesPer100k(Double casesPer100k) {
        this.casesPer100k = casesPer100k;
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

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}

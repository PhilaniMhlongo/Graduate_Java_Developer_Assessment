package com.enviro.assessment.grad001.philanimhlongo.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
@Entity
@Table(name = "disposal_guideline")
public class DisposalGuideline {
    // annotate the class as an entity and map to db table

    // define the fields

    // annotate the fields with db column names

    // ** set up mapping to WasteCategory entity
    

    // create constructors

    // generate getter/setter methods

    // generate toString() method
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "guideline", nullable = false)
    private String guideline;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                         CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "waste_category_id")
    @JsonBackReference
    private WasteCategory wasteCategory;

    public DisposalGuideline() {}

    public DisposalGuideline(String guideline) {
        this.guideline = guideline;
    }

   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGuideline() {
        return guideline;
    }

    public void setGuideline(String guideline) {
        this.guideline = guideline;
    }

    public WasteCategory getWasteCategory() {
        return wasteCategory;
    }

    public void setWasteCategory(WasteCategory wasteCategory) {
        this.wasteCategory = wasteCategory;
    }

    @Override
    public String toString() {
        return "DisposalGuideline{" +
                "id=" + id +
                ", guideline='" + guideline + '\'' +
                '}';
    }
    
}

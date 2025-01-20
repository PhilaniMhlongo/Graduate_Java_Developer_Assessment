package com.enviro.assessment.grad001.philanimhlongo.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
@Entity
@Table(name = "recycling_tip")
public class RecyclingTip {
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

    @Column(name = "tip", nullable = false)
    private String tip;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                         CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "waste_category_id")
    @JsonBackReference
    private WasteCategory wasteCategory;

    
    public RecyclingTip() {}

    public RecyclingTip(String tip) {
        this.tip = tip;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public WasteCategory getWasteCategory() {
        return wasteCategory;
    }

    public void setWasteCategory(WasteCategory wasteCategory) {
        this.wasteCategory = wasteCategory;
    }

    @Override
    public String toString() {
        return "RecyclingTip{" +
                "id=" + id +
                ", tip='" + tip + '\'' +
                '}';
    }
    
}

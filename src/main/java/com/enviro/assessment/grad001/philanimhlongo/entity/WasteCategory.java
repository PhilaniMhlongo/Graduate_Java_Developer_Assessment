package com.enviro.assessment.grad001.philanimhlongo.entity;
import java.util.*;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "waste_category")
public class WasteCategory {
     // annotate the class as an entity and map to db table

    // define the fields

    // annotate the fields with db column names

    // ** set up mapping to DisposalGuideline entity
    // ** set up mapping to RecyclingTip entity

    // create constructors

    // generate getter/setter methods

    // generate toString() method

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "wasteCategory", 
               cascade = {CascadeType.PERSIST, CascadeType.MERGE, 
                         CascadeType.DETACH, CascadeType.REFRESH})
    private List<DisposalGuideline> disposalGuidelines = new ArrayList<>();

    @OneToMany(mappedBy = "wasteCategory", 
               cascade = {CascadeType.PERSIST, CascadeType.MERGE, 
                         CascadeType.DETACH, CascadeType.REFRESH})
    private List<RecyclingTip> recyclingTips = new ArrayList<>();

    
    public WasteCategory() {}

    public WasteCategory(String name, String description) {
        this.name = name;
        this.description = description;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<DisposalGuideline> getDisposalGuidelines() {
        return disposalGuidelines;
    }

    public void setDisposalGuidelines(List<DisposalGuideline> disposalGuidelines) {
        this.disposalGuidelines = disposalGuidelines;
    }

    public List<RecyclingTip> getRecyclingTips() {
        return recyclingTips;
    }

    public void setRecyclingTips(List<RecyclingTip> recyclingTips) {
        this.recyclingTips = recyclingTips;
    }

    // Convenience methods for bi-directional relationship
    public void addDisposalGuideline(DisposalGuideline guideline) {
        disposalGuidelines.add(guideline);
        guideline.setWasteCategory(this);
    }

    public void addRecyclingTip(RecyclingTip tip) {
        recyclingTips.add(tip);
        tip.setWasteCategory(this);
    }

    @Override
    public String toString() {
        return "WasteCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    
}
}

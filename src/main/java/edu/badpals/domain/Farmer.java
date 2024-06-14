package edu.badpals.domain;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@Table(name = "farmer")
public class Farmer extends PanacheEntity {

    @Column(unique = true)
    public String name;

    @Column
    public String location;

    @JsonIgnore
    @OneToMany(mappedBy = "farmer")
    public Set<Fruit> fruits;
    
    public Farmer() {}

    public Farmer(String name, String location) {
        this.name = name;
        this.location = location;
    }
}

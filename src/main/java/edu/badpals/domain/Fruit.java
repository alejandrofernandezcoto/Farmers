package edu.badpals.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name="fruit") @ToString @EqualsAndHashCode
public class Fruit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
	
	@Column(name="name", unique = true)
	private String name;

    @Column(name="description")
	private String description;

    @ManyToOne
    @JoinColumn(name = "farmer_id")
    public Farmer farmer;

    public Fruit(String name, String description, Farmer farmer) {
        this.name = name;
        this.description = description;
        this.farmer = farmer;
    }

    
}

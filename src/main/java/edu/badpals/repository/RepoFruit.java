package edu.badpals.repository;

import edu.badpals.domain.Fruit;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RepoFruit implements PanacheRepository<Fruit> {
    
}

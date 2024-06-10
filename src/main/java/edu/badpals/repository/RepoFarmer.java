package edu.badpals.repository;

import edu.badpals.domain.Farmer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RepoFarmer implements PanacheRepository<Farmer> {
    
}

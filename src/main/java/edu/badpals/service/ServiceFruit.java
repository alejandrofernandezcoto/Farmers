package edu.badpals.service;

import java.util.Optional;
import java.util.Set;

import edu.badpals.domain.Fruit;
import edu.badpals.repository.Repositorio;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ServiceFruit {
    @Inject
    Repositorio repositorio;

    public Set<Fruit> list() {
        return repositorio.list();
    }

    public void add(Fruit fruit) {
        repositorio.add(fruit);
    }

    public void remove(String name) {
        repositorio.remove(name);
    }

    public Optional<Fruit> get(String name) {
        return repositorio.loadFruit(name);
    } 
    
}

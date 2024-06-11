package edu.badpals.service;

import java.util.Set;
import java.util.stream.Collectors;

import java.util.stream.Stream;

import edu.badpals.domain.Fruit;
import edu.badpals.repository.Repositorio;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ServiceFruit {
    @Inject
    Repositorio repositorio;

    public Set<Fruit> list() {
        Stream<Fruit> fruits = Fruit.streamAll();
        return fruits.collect(Collectors.toSet());
    }
}

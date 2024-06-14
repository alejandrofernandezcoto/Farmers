package edu.badpals.service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.badpals.domain.Farmer;
import edu.badpals.domain.Fruit;
import edu.badpals.repository.RepoFarmer;
import edu.badpals.repository.RepoFruit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ServiceFruit {
    @Inject
	RepoFarmer repoFarmer;

	@Inject
	RepoFruit repoFruit;

    public ServiceFruit() { 
    }

	@Transactional
	public Set<Fruit> list() {
        Stream<Fruit> fruits = repoFruit.streamAll();
        Set<Fruit> fruitSet = fruits.collect(Collectors.toSet());

        return fruitSet;
    }

	public Optional<Farmer> loadSupplier(Fruit fruit){
		return this.repoFarmer.find("name", fruit.farmer.name).firstResultOptional();
	}

	public Optional<Fruit> loadFruit(String nameFruit){
		return this.repoFruit.find("name", nameFruit).firstResultOptional();
	}

	@Transactional
    public void add(Fruit fruit) {
        Optional<Farmer> supplier = loadSupplier(fruit);
        if (supplier.isPresent()) {
            fruit.setFarmer(supplier.get());
        } else {
            repoFarmer.persist(fruit.getFarmer());
        }
        repoFruit.persist(fruit);
    }

	@Transactional
    public void remove(String name) {
       Optional<Fruit> fruit= loadFruit(name);
        if (fruit.isPresent()) {
            repoFruit.delete(fruit.get());
        }
    }

    public Optional<Fruit> getFruit(String name) {
        return name.isBlank()? 
            Optional.empty(): 
            repoFruit.find("name", name).firstResultOptional();
    }
    
}

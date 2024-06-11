package edu.badpals.repository;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.badpals.domain.Farmer;
import edu.badpals.domain.Fruit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class Repositorio {
    @Inject
	RepoFarmer repoFarmer;

	@Inject
	RepoFruit repoFruit;

	public Set<Fruit> list() {
        Stream<Fruit> fruits = Fruit.streamAll();
        return fruits.collect(Collectors.toSet());
    }

	public Optional<Farmer> loadSupplier(Fruit fruit){
		return this.repoFarmer.find("name", fruit.farmer.getName()).firstResultOptional();
	}

	public Optional<Fruit> loadFruit(String nameFruit){
		return this.repoFruit.find("name", nameFruit).firstResultOptional();
	}

    public void add(Fruit fruit) {
        Optional<Farmer> supplier =loadSupplier(fruit);
        if (supplier.isPresent()) { 
            fruit.farmer = supplier.get();
        } else {
            fruit.farmer.persist();
        }
        repoFruit.persist(fruit);
    }

    public void remove(String name) {
       Optional<Fruit> fruit= loadFruit(name);
        if (fruit.isPresent()) {
            fruit.get().delete();
        }
    }

    public Optional<Fruit> getFruit(String name) {
        return name.isBlank()? 
            Optional.ofNullable(null) : 
            Fruit.find("name", name).firstResultOptional();
    }

}

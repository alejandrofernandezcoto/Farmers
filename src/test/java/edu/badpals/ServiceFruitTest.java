package edu.badpals;

import java.util.Optional;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import edu.badpals.domain.Farmer;
import edu.badpals.domain.Fruit;
import edu.badpals.service.ServiceFruit;
import io.quarkus.test.junit.QuarkusTest;
import edu.badpals.repository.RepoFarmer;
import edu.badpals.repository.RepoFruit;

/**
 * Component Unit testing
 */

@QuarkusTest
@Transactional
public class ServiceFruitTest {

    @Inject
    ServiceFruit service;

    @Inject
    RepoFarmer repoFarmer;

    @Inject
    RepoFruit repoFruit;

    // @Test de jupiter, no el de junit
    @Test
    public void testList() {
        Assertions.assertThat(service.list()).hasSize(2);
    }

    @Test
    public void containsTest() {
        Assertions.assertThat(service.list().stream().anyMatch(f -> f.getName().equals("Apple"))).isTrue();
    }   
    
    @Test
    public void addTest() {
        service.add(new Fruit("Banana", 
                              "And an attached Gorilla",
                              new Farmer("Farmer Rick", "Sa Pobla")));
        Assertions.assertThat(service.list()).hasSize(3);
        Assertions.assertThat(service.list().stream().anyMatch(f -> f.getName().equals("Banana"))).isTrue();
        Assertions.assertThat(repoFruit.count()).isEqualTo(2L);

        // handmade rollback gracias al antipatron ActiveRecord ;)
        Fruit fruit = repoFruit.find("name", "Banana").firstResult();
        repoFruit.delete(fruit);
        Assertions.assertThat(repoFruit.count()).isEqualTo(2L);
        Assertions.assertThat(repoFarmer.count()).isEqualTo(2L);
    }

    // CORREGIR ESTE TEST PORQUE ES NUEVO 
    @Test
    public void addFarmerTest() {
        service.add(new Fruit("Navel Late", 
                              "Crist en pel", 
                              new Farmer("Jerrys Bites", "Es Pla")));
        Assertions.assertThat(service.list()).hasSize(3);
        Assertions.assertThat(service.list().stream().anyMatch(f -> f.getName().equals("Navel Late"))).isTrue();
        // hay un nuevo registro en la tabla Farmer
        Assertions.assertThat(repoFarmer.count()).isEqualTo(3L);

        // handmade rollback gracias al antipatron ActiveRecord ;)
        Fruit fruit = repoFruit.find("name", "Navel Late").firstResult();
        repoFruit.delete(fruit);
        Assertions.assertThat(repoFruit.count()).isEqualTo(2L);
        Farmer farmer = repoFarmer.find("name", "Jerrys Bites").firstResult();
        repoFarmer.delete(farmer);
        Assertions.assertThat(repoFarmer.count()).isEqualTo(2L);
    }


    @Test
    public void removeTest(){
        service.remove("Apple");
        Assertions.assertThat(service.list()).hasSize(1);
        Assertions.assertThat(service.list().stream().anyMatch(f -> f.getName().equals("Apple"))).isFalse();
        Assertions.assertThat(repoFruit.count()).isEqualTo(1L);
        Assertions.assertThat(repoFarmer.count()).isEqualTo(2L);

        Optional<Farmer> supplier = repoFarmer.find("name", "Farmer Rick").firstResultOptional();
        Assertions.assertThat(supplier).isNotEmpty();

        // handmade rollback gracias al antipatron ActiveRecord ;)
        Fruit fruit = new Fruit("Apple", "Winter fruit", supplier.get());
        repoFruit.persist(fruit);
        Assertions.assertThat(repoFruit.count()).isEqualTo(2);
    }

    @Test
    public void getFruitTest() {
        Assertions.assertThat(service.getFruit("Apple")).get().hasFieldOrPropertyWithValue("name", "Apple").hasFieldOrPropertyWithValue("description", "Winter fruit").extracting("farmer").toString().compareTo("Farmer Rick, Sa Pobla");
        Assertions.assertThat(service.getFruit("Mandarina")).isEmpty();
    }    
}

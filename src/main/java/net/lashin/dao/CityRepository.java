package net.lashin.dao;

import net.lashin.beans.City;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lashi on 09.02.2017.
 */
public interface CityRepository extends JpaRepository<City, Integer> {
    City findByName(String name);
}

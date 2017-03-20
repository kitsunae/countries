package net.lashin.core.services;

import net.lashin.core.beans.City;
import net.lashin.core.beans.Country;
import net.lashin.core.dao.CityRepository;
import net.lashin.core.dao.CountryRepository;
import net.lashin.core.filters.CityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by lashi on 14.03.2017.
 */
@Service
public class CityServiceImpl implements CityService {

    private CityRepository cityRepository;
    private CountryRepository countryRepository;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository, CountryRepository countryRepository) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public List<City> getCitiesByName(String name) {
        return cityRepository.findByName(name);
    }

    @Override
    public List<City> getWorldCapitals() {
        return countryRepository.findAll()
                .stream()
                .map(Country::getCapital)
                .filter(city -> city!=null)
                .collect(Collectors.toList());
    }

    @Override
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @Override
    public City edit(City city, Long cityId, String countryCode) {
        if (!Objects.equals(city.getId(), cityId))
            return null; //TODO throw exception
        Country country = countryRepository.findOne(countryCode);
        city.setCountry(country);
        return cityRepository.save(city);
    }

    @Override
    public City save(City city, String countryCode) {
        Country country = countryRepository.findOne(countryCode);
        city.setCountry(country);
        return cityRepository.save(city);
    }

    @Override
    public List<City> getCitiesByCountryCode(String countryCode) {
        return cityRepository.findByCountryCode(countryCode);
    }

    @Override
    public List<City> getCitiesByCountry(Country country) {
        return cityRepository.findByCountryCode(country.getCode());
    }

    @Override
    public City getCityById(long id) {
        return cityRepository.findOne(id);
    }

    @Override
    public void remove(Long id) {
        List<Country> countries = countryRepository.findByCapitalId(id);
        countries.forEach(country -> country.setCapital(null));
        countryRepository.save(countries);
        cityRepository.delete(id);
    }

    @Override
    public List<City> filterCities(CityFilter filter) {
        if (filter.getCountry()!=null){
            return cityRepository.findByCountryCode(filter.getCountry().getCode())
                    .stream()
                    .filter(city -> filter.getContinent() == null || city.getCountry().getContinent()==filter.getContinent())
                    .filter(city -> filter.getRegion()==null || filter.getRegion().equals(city.getCountry().getRegion()))
                    .filter(city -> city.getPopulation()<=filter.getMaxPopulation() && city.getPopulation()>=filter.getMinPopulation())
                    .collect(Collectors.toList());
        }
        if (filter.getContinent()!=null){
            return cityRepository.findByCountry_Continent(filter.getContinent())
                    .stream()
                    .filter(city -> filter.getRegion() == null || filter.getRegion().equals(city.getCountry().getRegion()))
                    .filter(city -> city.getPopulation()<=filter.getMaxPopulation() && city.getPopulation()>=filter.getMinPopulation())
                    .collect(Collectors.toList());
        }
        if (filter.getRegion()!=null){
            return cityRepository.findByCountry_Region(filter.getRegion())
                    .stream()
                    .filter(city -> city.getPopulation()<=filter.getMaxPopulation() && city.getPopulation()>=filter.getMinPopulation())
                    .collect(Collectors.toList());
        }
        return cityRepository.findAll()
                .stream()
                .filter(city -> city.getPopulation()<=filter.getMaxPopulation() && city.getPopulation()>=filter.getMinPopulation())
                .collect(Collectors.toList());
    }
}

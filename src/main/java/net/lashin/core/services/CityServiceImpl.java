package net.lashin.core.services;

import net.lashin.core.beans.City;
import net.lashin.core.beans.Country;
import net.lashin.core.dao.CityRepository;
import net.lashin.core.dao.CountryRepository;
import net.lashin.core.filters.CityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public Page<City> getCitiesByName(String name, Pageable pageRequest) {
        return cityRepository.findByName(name, pageRequest);
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
    public Page<City> getWorldCapitals(Pageable pageRequest) {
        List<City> all = getWorldCapitals();
        List<City> result = all.stream()
                .skip(pageRequest.getPageNumber()*pageRequest.getPageSize())
                .limit(pageRequest.getPageSize())
                .collect(Collectors.toList());
        return new PageImpl<>(result, pageRequest, all.size());
    }

    @Override
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @Override
    public Page<City> getAllCities(Pageable pageRequest) {
        return cityRepository.findAll(pageRequest);
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
    public Page<City> getCitiesByCountryCode(String countryCode, Pageable pageRequest) {
        return cityRepository.findByCountryCode(countryCode, pageRequest);
    }

    @Override
    public List<City> getCitiesByCountry(Country country) {
        return cityRepository.findByCountryCode(country.getCode());
    }

    @Override
    public Page<City> getCitiesByCountry(Country country, Pageable pageRequest) {
        return cityRepository.findByCountryCode(country.getCode(), pageRequest);
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
            return cityRepository.findByCountryCode(filter.getCountry())
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
        return cityRepository.filterCities(filter.getMinPopulation(), filter.getMaxPopulation());
    }

    @Override
    public Page<City> filterCities(CityFilter filter, Pageable pageRequest) {
        List<City> all = filterCities(filter);
        List<City> result = all.stream()
                .skip(pageRequest.getPageNumber()*pageRequest.getPageSize())
                .limit(pageRequest.getPageSize())
                .collect(Collectors.toList());
        return new PageImpl<>(result, pageRequest, all.size());
    }
}

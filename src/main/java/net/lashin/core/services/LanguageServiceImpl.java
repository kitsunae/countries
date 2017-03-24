package net.lashin.core.services;

import net.lashin.core.beans.CountryLanguage;
import net.lashin.core.beans.CountryLanguageId;
import net.lashin.core.dao.CountryLanguageRepository;
import net.lashin.core.filters.LanguageFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lashi on 14.03.2017.
 */
@Service
public class LanguageServiceImpl implements LanguageService {

    private CountryLanguageRepository languageRepository;

    @Autowired
    public LanguageServiceImpl(CountryLanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Override
    public List<CountryLanguage> getAllLanguages() {
        return languageRepository.findAll();
    }

    @Override
    public Page<CountryLanguage> getAllLanguages(Pageable pageRequest) {
        return languageRepository.findAll(pageRequest);
    }

    @Override
    public List<CountryLanguage> getLanguagesByCountryCode(String countryId) {
        return languageRepository.findByCountry_Code(countryId);
    }

    @Override
    public Page<CountryLanguage> getLanguagesByCountryCode(String countryCode, Pageable pageRequest) {
        return languageRepository.findByCountry_Code(countryCode, pageRequest);
    }

    @Override
    public List<CountryLanguage> getLanguagesByCountryAndOfficialty(String countryCode, boolean isOfficial) {
        return languageRepository.findByCountry_Code(countryCode)
                .stream()
                .filter(countryLanguage -> countryLanguage.isOfficial()==isOfficial)
                .collect(Collectors.toList());
    }

    @Override
    public Page<CountryLanguage> getLanguagesByCountryAndOfficialty(String countryCode, boolean isOfficial, Pageable pageRequest) {
        List<CountryLanguage> list = getLanguagesByCountryAndOfficialty(countryCode, isOfficial);
        List<CountryLanguage> result = list.stream()
                .skip(pageRequest.getPageNumber()*pageRequest.getPageSize())
                .limit(pageRequest.getPageSize())
                .collect(Collectors.toList());
        return new PageImpl<>(result, pageRequest, list.size());
    }

    @Override
    public CountryLanguage getLanguageByNameAndCountry(String language, String countryCode) {
        CountryLanguageId id = new CountryLanguageId(countryCode, language);
        return languageRepository.findOne(id);
    }

    @Override
    public CountryLanguage save(CountryLanguage language) {
        return languageRepository.save(language);
    }

    @Override
    public void remove(String language, String countryCode) {
        languageRepository.delete(new CountryLanguageId(countryCode, language));
    }

    @Override
    public List<String> getAllLanguageNames() {
        return languageRepository.findAllLanguageNames();
    }

    @Override
    public Page<String> getAllLanguageNames(Pageable pageRequest) {
        return languageRepository.findAllLanguageNames(pageRequest);
    }

    @Override
    public List<CountryLanguage> filterLanguages(LanguageFilter filter) {
        List<CountryLanguage> languages = languageRepository.filterLanguages(filter.getMinPercentage(), filter.getMaxPercentage());
        return languages.stream()
                .filter(cl->filter.getContinent()==null || cl.getCountry().getContinent()==filter.getContinent())
                .filter(cl->filter.getRegion()==null || filter.getRegion().equals(cl.getCountry().getRegion()))
                .filter(cl-> filter.isOfficial()==null || cl.isOfficial()==filter.isOfficial())
                .collect(Collectors.toList());
    }

    @Override
    public Page<CountryLanguage> filterLanguages(LanguageFilter filter, Pageable pageRequest) {
        List<CountryLanguage> list = filterLanguages(filter);
        List<CountryLanguage> result = list.stream()
                .skip(pageRequest.getPageNumber()*pageRequest.getPageSize())
                .limit(pageRequest.getPageSize())
                .collect(Collectors.toList());
        return new PageImpl<>(result, pageRequest, list.size());
    }
}

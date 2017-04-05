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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LanguageServiceImpl implements LanguageService {

    private CountryLanguageRepository languageRepository;

    @Autowired
    public LanguageServiceImpl(CountryLanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CountryLanguage> getAll() {
        return languageRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CountryLanguage> getAll(Pageable pageRequest) {
        return languageRepository.findAll(pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CountryLanguage> getByCountryCode(String countryId) {
        return languageRepository.findByCountry_Code(countryId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CountryLanguage> getByCountryCode(String countryCode, Pageable pageRequest) {
        return languageRepository.findByCountry_Code(countryCode, pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CountryLanguage> getByCountryAndOfficialty(String countryCode, boolean isOfficial) {
        return languageRepository.findByCountry_Code(countryCode)
                .stream()
                .filter(countryLanguage -> countryLanguage.isOfficial()==isOfficial)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CountryLanguage> getByCountryAndOfficialty(String countryCode, boolean isOfficial, Pageable pageRequest) {
        List<CountryLanguage> list = getByCountryAndOfficialty(countryCode, isOfficial);
        List<CountryLanguage> result = list.stream()
                .skip(pageRequest.getPageNumber()*pageRequest.getPageSize())
                .limit(pageRequest.getPageSize())
                .collect(Collectors.toList());
        return new PageImpl<>(result, pageRequest, list.size());
    }

    @Override
    @Transactional(readOnly = true)
    public CountryLanguage getByNameAndCountry(String language, String countryCode) {
        CountryLanguageId id = new CountryLanguageId(countryCode, language);
        return languageRepository.findOne(id);
    }

    @Override
    @Transactional
    public CountryLanguage save(CountryLanguage language) {
        return languageRepository.save(language);
    }

    @Override
    @Transactional
    public void remove(String language, String countryCode) {
        languageRepository.delete(new CountryLanguageId(countryCode, language));
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAllLanguageNames() {
        return languageRepository.findAllLanguageNames();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<String> getAllLanguageNames(Pageable pageRequest) {
        return languageRepository.findAllLanguageNames(pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CountryLanguage> filter(LanguageFilter filter) {
        return languageRepository.filterLanguages(filter);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CountryLanguage> filter(LanguageFilter filter, Pageable pageRequest) {
        List<CountryLanguage> list = filter(filter);
        List<CountryLanguage> result = list.stream()
                .skip(pageRequest.getPageNumber()*pageRequest.getPageSize())
                .limit(pageRequest.getPageSize())
                .collect(Collectors.toList());
        return new PageImpl<>(result, pageRequest, list.size());
    }
}

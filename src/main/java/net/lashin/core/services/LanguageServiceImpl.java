package net.lashin.core.services;

import net.lashin.core.beans.CountryLanguage;
import net.lashin.core.beans.CountryLanguageId;
import net.lashin.core.dao.CountryLanguageRepository;
import net.lashin.core.filters.LanguageFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LanguageServiceImpl implements LanguageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LanguageServiceImpl.class);
    private CountryLanguageRepository languageRepository;

    @Autowired
    public LanguageServiceImpl(CountryLanguageRepository languageRepository) {
        LOGGER.debug("Instantiating LanguageServiceImpl");
        this.languageRepository = languageRepository;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("countrylanguages")
    public List<CountryLanguage> getAll() {
        LOGGER.debug("Get all languages");
        return languageRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("countrylanguages")
    public Page<CountryLanguage> getAll(Pageable pageRequest) {
        LOGGER.debug("Get {} languages, page #{}", pageRequest.getPageSize(), pageRequest.getPageNumber());
        return languageRepository.findAll(pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("countrylanguages")
    public List<CountryLanguage> getByCountryCode(String countryId) {
        LOGGER.debug("Get all languages by country code {}", countryId);
        return languageRepository.findByCountryCode(countryId);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("countrylanguages")
    public Page<CountryLanguage> getByCountryCode(String countryCode, Pageable pageRequest) {
        LOGGER.debug("Get {} languages by country code {}, page #{}", pageRequest.getPageSize(), countryCode, pageRequest.getPageNumber());
        return languageRepository.findByCountryLanguageIdCountryCode(countryCode, pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("countrylanguages")
    public List<CountryLanguage> getByCountryAndOfficialty(String countryCode, boolean isOfficial) {
        LOGGER.debug("Get all languages by country code {} and official={}", countryCode, isOfficial);
        return languageRepository.findByCountryCode(countryCode)
                .stream()
                .filter(countryLanguage -> countryLanguage.isOfficial()==isOfficial)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("countrylanguages")
    public Page<CountryLanguage> getByCountryAndOfficialty(String countryCode, boolean isOfficial, Pageable pageRequest) {
        LOGGER.debug("Get {} languages by country code {} and official={}, page #{}", pageRequest.getPageSize(), countryCode, isOfficial, pageRequest.getPageNumber());
        List<CountryLanguage> list = getByCountryAndOfficialty(countryCode, isOfficial);
        List<CountryLanguage> result = list.stream()
                .skip(pageRequest.getPageNumber()*pageRequest.getPageSize())
                .limit(pageRequest.getPageSize())
                .collect(Collectors.toList());
        return new PageImpl<>(result, pageRequest, list.size());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("countrylanguages")
    public CountryLanguage getByNameAndCountry(String language, String countryCode) {
        LOGGER.debug("Get language {} of country {}", language, countryCode);
        CountryLanguageId id = new CountryLanguageId(countryCode, language);
        return languageRepository.findOne(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = "countrylanguages", allEntries = true)
    public CountryLanguage save(CountryLanguage language) {
        LOGGER.debug("Save language {}", language);
        return languageRepository.save(language);
    }

    @Override
    @Transactional
    @CacheEvict(value = "countrylanguages", allEntries = true)
    public void remove(String language, String countryCode) {
        LOGGER.debug("Delete language {} of country {}");
        languageRepository.delete(new CountryLanguageId(countryCode, language));
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAllLanguageNames() {
        LOGGER.debug("Get all language names");
        return languageRepository.findAllLanguageNames();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<String> getAllLanguageNames(Pageable pageRequest) {
        LOGGER.debug("Get {} language names, page#{}", pageRequest.getPageSize(), pageRequest.getPageNumber());
        return languageRepository.findAllLanguageNames(pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("countrylanguages")
    public List<CountryLanguage> filter(LanguageFilter filter) {
        LOGGER.debug("Filter languages, filter {}", filter);
        return languageRepository.filterLanguages(filter);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("countrylanguages")
    public Page<CountryLanguage> filter(LanguageFilter filter, Pageable pageRequest) {
        LOGGER.debug("Filter languages, filter {}, amount {} page #{}", filter, pageRequest.getPageSize(), pageRequest.getPageNumber());
        List<CountryLanguage> list = filter(filter);
        List<CountryLanguage> result = list.stream()
                .skip(pageRequest.getPageNumber()*pageRequest.getPageSize())
                .limit(pageRequest.getPageSize())
                .collect(Collectors.toList());
        return new PageImpl<>(result, pageRequest, list.size());
    }
}

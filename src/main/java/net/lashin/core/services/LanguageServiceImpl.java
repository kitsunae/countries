package net.lashin.core.services;

import net.lashin.core.beans.CountryLanguage;
import net.lashin.core.beans.CountryLanguageId;
import net.lashin.core.dao.CountryLanguageRepository;
import net.lashin.core.dao.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lashi on 14.03.2017.
 */
@Service
public class LanguageServiceImpl implements LanguageService {

    private CountryLanguageRepository languageRepository;
    private CountryRepository countryRepository;

    @Autowired
    public LanguageServiceImpl(CountryLanguageRepository languageRepository, CountryRepository countryRepository) {
        this.languageRepository = languageRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public List<CountryLanguage> getAllLanguages() {
        return languageRepository.findAll();
    }

    @Override
    public List<CountryLanguage> getLanguagesByCountryCode(String countryId) {
        return countryRepository.findOne(countryId).getLanguages();
    }

    @Override
    public List<CountryLanguage> getLanguagesByCountryAndOfficialty(String countryCode, boolean isOfficial) {
        return languageRepository.findByCountry_Code(countryCode)
                .stream()
                .filter(countryLanguage -> countryLanguage.isOfficial()==isOfficial)
                .collect(Collectors.toList());
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

    //TODO replace with repository query
    @Override
    public List<String> getAllLanguageNames() {
        return languageRepository.findAll()
                .stream()
                .map(CountryLanguage::getLanguage)
                .distinct()
                .collect(Collectors.toList());
    }
}

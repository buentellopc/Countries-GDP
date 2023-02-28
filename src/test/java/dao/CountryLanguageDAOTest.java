package dao;


import config.TestDBConfiguration;
import org.example.dao.CountryLanguageDAO;
import org.example.model.CountryLanguage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.List;

@RunWith(SpringRunner.class)
@SpringJUnitConfig(classes = {TestDBConfiguration.class, CountryLanguageDAO.class})
public class CountryLanguageDAOTest {

    @Autowired CountryLanguageDAO countryLanguageDAO;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Before
    public void before(){
        countryLanguageDAO.setNamedParameterJdbcTemplate(namedParameterJdbcTemplate);
    }

    @Test
    public void testGetLanguages(){
        List<CountryLanguage> languages = countryLanguageDAO.getLanguages("BRA", 1);

        assertThat(languages.size()).isEqualTo(5);
    }
}

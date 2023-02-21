package dao;

import config.TestDBConfiguration;
import org.example.dao.CountryDao;
import org.example.model.Country;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringJUnitConfig( classes = {
        TestDBConfiguration.class, CountryDao.class})
public class CountryDAOTest {

    @Autowired
    CountryDao countryDao;

    @Autowired
    @Qualifier("testTemplate")
    NamedParameterJdbcTemplate namedParamJdbcTemplate;

    @Before
    public void setup() {
        countryDao.setNamedParameterJdbcTemplate(namedParamJdbcTemplate);
    }

    @Test
    public void testGetCountries() {
        List<Country> countries = countryDao.getCountries(new HashMap<>());
        //AssertJ assertions
        //Paginated List, so should have 20 entries
        assertThat(countries).hasSize(2540);
    }
}
package dao;

import config.TestDBConfiguration;
import org.example.dao.CityDao;
import org.example.dao.CountryDao;
import org.example.model.City;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringJUnitConfig( classes = {
        TestDBConfiguration.class, CityDao.class})
public class CityDAOTest {

    @Autowired
    CityDao cityDao;

    @Autowired
    @Qualifier("testTemplate")
    NamedParameterJdbcTemplate namedParamJdbcTemplate;

    @Before
    public void setup() {
        // this is like passing the template to abstract it and make use of it
        cityDao.setNamedParameterJdbcTemplate(namedParamJdbcTemplate);
    }

    @Test
    public void testGetCities() {

        List<City> cities = cityDao.getCities("BRA", 1);
//        cities.stream().forEach(city -> System.out.println(city + " "));
        assertThat(cities.size()).isEqualTo(20);

    }
}
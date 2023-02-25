package dao;
import org.example.model.Country;

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

    @Test
    public void testGetAllCities(){

        // TODO: 2/24/23 interesting way of pass null  
        List<City> cities = cityDao.getCities("BRA");
        assertThat(cities.size()).isEqualTo(251);

    }

    @Test
    public void testGetCityDetail(){

        City cityDetail = cityDao.getCityDetail(207L);

        assertThat(cityDetail.getName()).isEqualTo("Rio de Janeiro");

    }


    @Test
    public void testAddCity(){

        City fooCity = new City();
//        fooCity.setId(0L);
        fooCity.setName("foodejaneiro");
        fooCity.setCountryCode("BRA");
//        fooCity.setCountry(new Country());
        fooCity.setDistrict("foodejaneiro");
        fooCity.setPopulation(0L);


        Long newCityID = cityDao.addCity("BRA", fooCity);
        assertThat(newCityID).isNotNull();

        City cityFromDB = cityDao.getCityDetail(newCityID);

        // the city retrieved from db should be equal to the one you set earlier
        assertThat(cityFromDB.getName()).isEqualTo("foodejaneiro");

    }


}
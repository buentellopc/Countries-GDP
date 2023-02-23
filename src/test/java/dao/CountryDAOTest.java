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
        // this is like making passing the template to abstract it and make use of it
        countryDao.setNamedParameterJdbcTemplate(namedParamJdbcTemplate);
    }

    @Test
    public void testGetCountries() {
        List<Country> countries = countryDao.getCountries(new HashMap<>());
        //AssertJ assertions
        //Paginated List, so should have 20 entries
        assertThat(countries).hasSize(20);
    }

    @Test
    public void testGetCountryDetail(){
        Country countryDetail = countryDao.getCountryDetail("MEX");
        assertThat(countryDetail).isNotNull();

        // TODO: 2/22/23 be wary of strings, this one was retrieved thanks to printing the row object to the console
        assertThat(countryDetail.toString()).isEqualTo("Country(code=MEX, name=Mexico, continent=North America, region=Central America, surfaceArea=1958201.0, indepYear=1810, population=98881000, lifeExpectancy=71.5, gnp=414972.0, localName=MÃ©xico, governmentForm=Federal Republic, headOfState=Vicente Fox Quesada, capital=City(id=2515, name=Ciudad de MÃ©xico, countryCode=null, country=null, district=null, population=null), code2=MX)");


//        System.out.println("COUNTRY DETAIL");
//        System.out.println(countryDetail);
    }

    @Test
    public void testEditCountryDetail(){
        Country countryToEdit = countryDao.getCountryDetail("MEX");
        countryToEdit.setName("THISWASEDITEDOHNO!");
        countryDao.editCountryDetail("MEX", countryToEdit);


        countryToEdit = countryDao.getCountryDetail("MEX");
        assertThat(countryToEdit.getName()).isEqualTo("THISWASEDITEDOHNO!");

    }
}
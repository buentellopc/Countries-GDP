package org.example;

import org.example.config.DBConfiguration;
import org.example.config.PropertiesWithJavaConfig;
import org.example.dao.mapper.CityRowMapper;
import org.example.dao.mapper.CountryRowMapper;
import org.example.model.City;
import org.example.model.Country;
import org.example.model.CountryLanguage;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        // TODO: 2/9/23 Make tests for each part of the project 
        Country country = new Country();
        country.setCode("");
        country.setName("");
        country.setContinent("");
        country.setRegion("");
        country.setSurfaceArea(0.0D);
        country.setIndepYear((short)0);
        country.setPopulation(0L);
        country.setLifeExpectancy(0.0D);
        country.setGnp(0.0D);
        country.setLocalName("");
        country.setGovernmentForm("");
        country.setHeadOfState("");
        country.setCapital(new City());
        country.setCode2("");

        City city = new City();
        city.setId(1L);
        city.setCountry(new Country());
        city.setName("Kabul");
        city.setDistrict("Kabol");
        city.setPopulation(1780000L);


        System.out.println(city);

        // create the application context with multiple config classes
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PropertiesWithJavaConfig.class, DBConfiguration.class);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = context.getBean(NamedParameterJdbcTemplate.class);
        System.out.println(namedParameterJdbcTemplate);

        // test that jdbc connection is working by querying one row from city
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", 1);
        String s = namedParameterJdbcTemplate.queryForObject("select name from city where id = :id", namedParameters, String.class);
        System.out.println(s);

    }
}
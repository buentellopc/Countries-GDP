package org.example.dao.mapper;

import org.example.model.CountryLanguage;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


// TODO: 2/16/23 I could  implement git to manage the third attempt. Maven depedencies are in a repository, remember that in the end this app could updated to latest versions. You are not require to know everything by heart, is as easy as checking the documentation of each class. Benefit from intellij and automatically implement interfaces methods. Still unsure what's the use of country property in CountryLanguage, and why countryCode is not defined in the book. Not a big problem
public class CountryLanguageRowMapper implements RowMapper<CountryLanguage> {
    @Override
    public CountryLanguage mapRow(ResultSet rs, int rowNum) throws SQLException {
        CountryLanguage countryLanguage = new CountryLanguage();

        countryLanguage.setCountryCode(rs.getString("countrycode"));
        countryLanguage.setLanguage(rs.getString("language"));
        countryLanguage.setIsOfficial(rs.getString("isofficial"));
        countryLanguage.setPercentage(rs.getDouble("percentage"));
        return countryLanguage;



    }
}

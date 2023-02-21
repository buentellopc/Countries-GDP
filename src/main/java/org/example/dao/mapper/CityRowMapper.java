package org.example.dao.mapper;

import org.example.model.City;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CityRowMapper implements RowMapper<City> {
    public City mapRow(ResultSet rs, int rowNum)
            throws SQLException {
        City city = new City();
        city.setDistrict(rs.getString("district"));
        city.setCountryCode(rs.getString("country_code"));
        city.setId(rs.getLong("id"));
        city.setName(rs.getString("name"));
        city.setPopulation(rs.getLong("population"));
        return city;
    }
}

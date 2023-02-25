package org.example.dao;


import lombok.Setter;
import org.example.dao.mapper.CityRowMapper;
import org.example.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Service
public class CityDao {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final Integer PAGE_SIZE = 20;

    public List<City> getCities(String countryCode, Integer pageNo){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("code", countryCode);
        if ( pageNo != null ) {
            // TODO: 2/24/23 notice that offset is only relevant here, thanks to the map. A null value is a clever way to get all the cities
            Integer offset = (pageNo - 1) * PAGE_SIZE;
            params.put("offset", offset);
            params.put("size", PAGE_SIZE);
        }

        return namedParameterJdbcTemplate.query("SELECT "
                        + " id, name, countrycode country_code, district, population "
                        + " FROM city WHERE countrycode = :code"
                        + " ORDER BY Population DESC"
                        + ((pageNo != null) ? " LIMIT :offset , :size " : ""),
                params, new CityRowMapper());
    }

    public List<City> getCities(String countryCode){
        return getCities(countryCode, null);
    }


    public City getCityDetail(Long cityId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", cityId);
        return namedParameterJdbcTemplate.queryForObject("SELECT id, "
                        + " name, countrycode country_code, "
                        + " district, population "
                        + " FROM city WHERE id = :id",
                params, new CityRowMapper());
    }


    public Long addCity(String countryCode, City city) {

        SqlParameterSource paramSource = new MapSqlParameterSource(
                getMapForCity(countryCode, city));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update("INSERT INTO city("
                        + " name, countrycode, "
                        + " district, population) "
                        + " VALUES (:name, :country_code, "
                        + " :district, :population )",
                paramSource, keyHolder);
        return keyHolder.getKey().longValue();
    }

    private Map<String,?> getMapForCity(String countryCode, City city) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", city.getName());
        map.put("country_code", countryCode);
        map.put("district", city.getDistrict());
        map.put("population", city.getPopulation());

        System.out.println("THE NAME FROM THE NEW CITY IS: " + map.get("name"));
        return map;
    }


}

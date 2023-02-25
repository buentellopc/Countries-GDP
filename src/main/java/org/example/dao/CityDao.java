package org.example.dao;


import lombok.Setter;
import org.example.dao.mapper.CityRowMapper;
import org.example.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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


    





}

package org.example.dao;

import lombok.Setter;
import org.example.dao.mapper.CountryRowMapper;
import org.example.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Setter
@Service
public class CountryDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final String SELECT_CLAUSE = "SELECT "
            + " c.Code, "
            + " c.Name, "
            + " c.Continent, "
            + " c.region, "
            + " c.SurfaceArea surface_area, "
            + " c.IndepYear indep_year, "
            + " c.Population, "
            + " c.LifeExpectancy life_expectancy, "
            + " c.GNP, "
            + " c.LocalName local_name, "
            + " c.GovernmentForm government_form, "
            + " c.HeadOfState head_of_state, "
            + " c.code2 ,"
            + " c.capital ,"
            + " cy.name capital_name "
            + " FROM country c"
            + " LEFT OUTER JOIN city cy ON cy.id = c.capital ";


    private static final String SEARCH_WHERE_CLAUSE = " AND ( LOWER(c.name) "
            + " LIKE CONCAT('%', LOWER(:search), '%') ) ";


    private static final String CONTINENT_WHERE_CLAUSE =
            " AND c.continent = :continent ";


    private static final String REGION_WHERE_CLAUSE =
            " AND c.region = :region ";


    private static final String PAGINATION_CLAUSE = " ORDER BY c.code "
            + " LIMIT :size OFFSET :offset ";




    private static final Integer PAGE_SIZE = 20;


    public List<Country> getCountries(Map<String, Object> params){
        int pageNo = 1;
        if ( params.containsKey("pageNo") ) {
            pageNo = Integer.parseInt(params.get("pageNo").toString());
        }
        Integer offset = (pageNo - 1) * PAGE_SIZE;
        params.put("offset", offset);
        params.put("size", PAGE_SIZE);


// SOUT SOUT SOUT
        System.out.println("Params: {}"+ params.toString());
        System.out.println(SELECT_CLAUSE
                + " WHERE 1 = 1 "
                + (!StringUtils.isEmpty((String)params.get("search")) ? SEARCH_WHERE_CLAUSE : "")
                + (!StringUtils.isEmpty((String)params.get("continent")) ? CONTINENT_WHERE_CLAUSE : "")
                + (!StringUtils.isEmpty((String)params.get("region")) ? REGION_WHERE_CLAUSE : "")
                + " ORDER BY c.code "
                + "  LIMIT :offset , :size ");







        return namedParameterJdbcTemplate.query(SELECT_CLAUSE
                        + " WHERE 1 = 1 "
                        + (!StringUtils.isEmpty((String)params.get("search"))
                        ? SEARCH_WHERE_CLAUSE : "")
                        + (!StringUtils.isEmpty((String)params.get("continent"))
                        ? CONTINENT_WHERE_CLAUSE : "")
                        + (!StringUtils.isEmpty((String)params.get("region"))
                        ? REGION_WHERE_CLAUSE : "")
                        + PAGINATION_CLAUSE,
                params, new CountryRowMapper());
    }




    public int getCountriesCount(Map<String, Object> params) {
        return namedParameterJdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM country c"
                        + " WHERE 1 = 1 "
                        + (!StringUtils.isEmpty((String)params.get("search"))
                        ? SEARCH_WHERE_CLAUSE : "")
                        + (!StringUtils.isEmpty((String)params.get("continent"))
                        ? CONTINENT_WHERE_CLAUSE : "")
                        + (!StringUtils.isEmpty((String)params.get("region"))
                        ? REGION_WHERE_CLAUSE : ""),
                params, Integer.class);
    }


//    get a specific country
    public Country getCountryDetail(String code){
        Map<String, String> params = new HashMap<String, String>();
        params.put("code", code);

        return namedParameterJdbcTemplate.queryForObject(SELECT_CLAUSE + " WHERE c.code = :code", params, new CountryRowMapper());
    }




}

package external;

import org.example.external.WorldBankApiClient;
import org.example.model.CountryGDP;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringJUnitConfig(classes = {WorldBankApiClient.class})
public class WorldBankApiClientTest {
    @Autowired
    WorldBankApiClient worldBankApiClient;

    @Test
    public void testGetGDP() throws ParseException {
        WorldBankApiClient apiClient = new WorldBankApiClient();
        List<CountryGDP> countryGDPS = apiClient.getGDP("BRA");
        CountryGDP countryGDP = countryGDPS.get(0);
        assertThat(countryGDP.getYear()).isEqualTo(new Short("2018"));


    }


}

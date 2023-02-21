package org.example.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@Setter
@Getter
public class CountryLanguage {

    // TODO: 2/10/23 why country obj and not just code? why foreign keys is not displayed in workbench?
    @NotNull private Country country;
    @NotNull @Size(max = 30) private String language;

//    todo it is strange that is official type is string and not boolean
    @NotNull @Size(max = 1, min = 1)
    private String isOfficial;
    @NotNull private Double percentage;
    @NotNull
    @Size(max = 3, min = 3)
    private String countryCode;
}

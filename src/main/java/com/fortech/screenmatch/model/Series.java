package com.fortech.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Series(@JsonAlias("Title") String title,
                     @JsonAlias("totalSeasons") Integer totalSeasons,
                     @JsonAlias("imdbRating") String rate) {

}

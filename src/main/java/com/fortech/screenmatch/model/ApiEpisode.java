package com.fortech.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ApiEpisode(@JsonAlias("Title") String title,
                         @JsonAlias("Episode") Integer episode,
                         @JsonAlias("imdbRating") String rating,
                         @JsonAlias("Released") String released) {
}

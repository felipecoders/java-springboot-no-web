package com.fortech.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ApiSeason(@JsonAlias("Season") Integer season,
                        @JsonAlias("Episodes") List<ApiEpisode> apiEpisodes) {
}

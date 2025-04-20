package com.fortech.screenmatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episode {
    private Integer season;
    private String title;
    private Integer episode;
    private Double rating;
    private LocalDate released;

    public Episode(Integer season, ApiEpisode episode) {
        this.season = season;
        this.title = episode.title();
        this.episode = episode.episode();
        try {
            this.rating = episode.rating() == null ? 0 : Double.parseDouble(episode.rating());
        } catch (NumberFormatException e) {
            this.rating = 0.0;
        }
        try {
            this.released = episode.released() == null ? null : LocalDate.parse(episode.released());
        } catch (DateTimeParseException e) {
            this.released = null;
        }
    }

    public Integer getSeason() {
        return season;
    }

    public Double getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "{" +
                "season=" + season +
                ", title='" + title + '\'' +
                ", episode=" + episode +
                ", rating=" + rating +
                ", released=" + released +
                '}';
    }
}

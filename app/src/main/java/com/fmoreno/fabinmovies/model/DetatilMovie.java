package com.fmoreno.fabinmovies.model;

import java.util.List;

public class DetatilMovie {
    public boolean adult;
    public String backdrop_path;
    public BelongsToCollection belongs_to_collection;
    public int budget;
    public List<Genre> genres;
    public String homepage;
    public int id;
    public String imdb_id;
    public String original_language;
    public String original_title;
    public String overview;
    public double popularity;
    public String poster_path;
    public List<ProductionCompany> production_companies;
    public List<ProductionCountry> production_countries;
    public String release_date;
    public int revenue;
    public int runtime;
    public List<SpokenLanguage> spoken_languages;
    public String status;
    public String tagline;
    public String title;
    public boolean video;
    public double vote_average;
    public int vote_count;
    public Videos videos;
    public class BelongsToCollection{
        public int id;
        public String name;
        public String poster_path;
        public String backdrop_path;
    }

    public class Genre{
        public int id;
        public String name;
    }

    public class ProductionCompany{
        public int id;
        public String logo_path;
        public String name;
        public String origin_country;
    }

    public class ProductionCountry{
        public String iso_3166_1;
        public String name;
    }

    public class SpokenLanguage{
        public String english_name;
        public String iso_639_1;
        public String name;
    }

    public class Result{
        public String iso_639_1;
        public String iso_3166_1;
        public String name;
        public String key;
        public String site;
        public int size;
        public String type;
        public boolean official;
        public String published_at;
        public String id;
    }

    public class Videos{
        public List<Result> results;
    }
}

package com.example.cgv.movie;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "genre_movie")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenreMovie {

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GenreMovieId implements Serializable {

        @Column(name = "genre_id")
        private Long genreId;

        @Column(name = "movie_id")
        private Long movieId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GenreMovieId)) return false;
            GenreMovieId that = (GenreMovieId) o;
            return Objects.equals(genreId, that.genreId) && Objects.equals(movieId, that.movieId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(genreId, movieId);
        }
    }

    /** 장르-영화 복합키 */
    @EmbeddedId
    private GenreMovieId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("genreId")
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("movieId")
    @JoinColumn(name = "movie_id")
    private Movie movie;
}

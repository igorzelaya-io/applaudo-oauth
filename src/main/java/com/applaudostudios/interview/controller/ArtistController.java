package com.applaudostudios.interview.controller;

import com.applaudostudios.interview.controller.request.ArtistRequest;
import com.applaudostudios.interview.controller.response.ArtistResponse;
import com.github.javafaker.Faker;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    private final List<ArtistResponse> artist;

    /**
     * Creates an instance of the Artist Controller along with
     * the a fake list of artist names.
     */
    public ArtistController() {
        final Faker faker = new Faker();
        this.artist = IntStream
            .range(0, 10)
            .mapToObj(i -> new ArtistResponse(faker.artist().name()))
            .collect(Collectors.toList());
    }

    /**
     * List of all the artists names available.
     * @return a list of strings with the artists.
     */
    @GetMapping
    public List<ArtistResponse> getArtists() {
        return this.artist;
    }

    /**
     * Tries to find an artist based on the given parameter.
     *
     * @param name used to filter out.
     *
     * @return the artists found otherwise null
     */
    @GetMapping("/{name}")
    public ResponseEntity<ArtistResponse> getArtist(@PathVariable(value = "name") final String name) {
        return artist
            .stream()
            .filter(artistResponse -> name.equalsIgnoreCase(artistResponse.getName()))
            .findFirst()
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Adds a new artist name into the collection.
     *
     * @param artistRequest to be added to the collection.
     *
     * @return the created artist name
     */
    @PostMapping
    public ResponseEntity<ArtistResponse> addArtist(@RequestBody final ArtistRequest artistRequest) {
        ArtistResponse artistResponse = new ArtistResponse(artistRequest.getName());
        this.artist.add(artistResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(artistResponse);
    }

}

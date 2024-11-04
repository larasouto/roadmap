package com.game.store.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "platform_id")
    private Device platform;

    private String developer;

    private String imageUrl;

    private String videoUrl;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    private BigDecimal price;

    public Game() {
    }

    public Game(String name, String description, Genre genre, Device platform, String developer,
            LocalDate releaseDate, BigDecimal price, String imageUrl) {
        this.name = name;
        this.description = description;
        this.genre = genre;
        this.platform = platform;
        this.developer = developer;
        this.releaseDate = releaseDate;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getFormattedReleaseDate() {
        if (releaseDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return releaseDate.format(formatter);
        }
        return null;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", genre='" + genre + '\'' +
                ", platform='" + platform + '\'' +
                ", developer='" + developer + '\'' +
                ", releaseDate=" + releaseDate +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    

    public Long getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public void setPlatform(Device platform) {
        this.platform = platform;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Genre getGenre() {
        return genre;
    }

    public Device getPlatform() {
        return platform;
    }

    public String getDeveloper() {
        return developer;
    }
}
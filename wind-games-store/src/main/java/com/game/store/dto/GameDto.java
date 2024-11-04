package com.game.store.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.game.store.models.Device;
import com.game.store.models.Game;
import com.game.store.models.Genre;

public class GameDto {
    @NotBlank
    @NotNull
    private String name;

    private String description;

    private Genre genre;

    private Device platform;

    private String developer;

    private String imageUrl;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate releaseDate;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal price;

    public Game toGame() {
        Game game = new Game();
        game.setName(this.name);
        game.setDescription(this.description);
        game.setGenre(this.genre);
        game.setPlatform(this.platform);
        game.setDeveloper(this.developer);
        game.setReleaseDate(this.releaseDate);
        game.setPrice(this.price);
        game.setImageUrl(this.imageUrl);

        return game;
    }

    public Game toGame(Game game) {
        game.setName(this.name);
        game.setDescription(this.description);
        game.setGenre(this.genre);
        game.setPlatform(this.platform);
        game.setDeveloper(this.developer);
        game.setReleaseDate(this.releaseDate);
        game.setPrice(this.price);
        game.setImageUrl(this.imageUrl);

        return game;
    }

    public void fromGame(Game game) {
        this.name = game.getName();
        this.description = game.getDescription();
        this.genre = game.getGenre();
        this.platform = game.getPlatform();
        this.developer = game.getDeveloper();
        this.releaseDate = game.getReleaseDate();
        this.price = game.getPrice();
        this.imageUrl = game.getImageUrl();
    }

    @Override
    public String toString() {
        return "GameDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", genre='" + genre + '\'' +
                ", platform='" + platform + '\'' +
                ", developer='" + developer + '\'' +
                ", releaseDate=" + releaseDate +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Device getPlatform() {
        return platform;
    }

    public void setPlatform(Device platform) {
        this.platform = platform;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

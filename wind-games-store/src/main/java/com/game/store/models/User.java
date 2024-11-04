package com.game.store.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Data;

@Data
@Entity(name = "users")
public class User {

  public enum Role {
    CLIENT, MANAGER, ADMINISTRATOR, SELLER
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  @ManyToMany
  @JoinTable(name = "user_purchased_games", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "game_id"))
  private Set<Game> purchasedGames = new HashSet<>();

  @ManyToMany
  @JoinTable(name = "user_purchases", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "purchase_id"))
  private Set<Purchase> purchasedCompleted = new HashSet<>();

  @ManyToMany
  @JoinTable(name = "user_platforms", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "platform_id"))
  private Set<Device> platforms = new HashSet<>();

  @ManyToMany
  @JoinTable(name = "user_genre_preferences", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
  private Set<Genre> genrePreferences = new HashSet<>();

  @Enumerated(EnumType.STRING)
  private Role role;

  @Column(nullable = false)
  private LocalDate birthDate;

  @Column(nullable = false)
  private String address;

  public User() {
  }

  public User(String name, String email, String password, LocalDate birthDate,
      Set<Device> platforms, Set<Genre> genrePreferences, String address, Role role) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.birthDate = birthDate;
    this.platforms = platforms;
    this.genrePreferences = genrePreferences;
    this.address = address;
    this.role = role;
  }

  @Override
  public String toString() {
    return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", plataforma="
        + platforms + ", genrePreference=" + genrePreferences + ", birthDate=" + birthDate + ", address=" + address
        + ", role=" + role + "]";
  }

  //add getter and setter

  public Set<Game> getPurchasedGames() {
    return purchasedGames;
  }

  public void setPurchasedGames(Set<Game> purchasedGames) {
    this.purchasedGames = purchasedGames;
  }

  public Set<Purchase> getPurchasedCompleted() {
    return purchasedCompleted;
  }

  public void setPurchasedCompleted(Set<Purchase> purchasedCompleted) {
    this.purchasedCompleted = purchasedCompleted;
  }

  public Set<Device> getPlatforms() {
    return platforms;
  }

  public void setPlatforms(Set<Device> platforms) {
    this.platforms = platforms;
  }

  public Set<Genre> getGenrePreferences() {
    return genrePreferences;
  }

  public void setGenrePreferences(Set<Genre> genrePreferences) {
    this.genrePreferences = genrePreferences;
  }

  public Role getRole() {
    return role;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setEmail(String email) {
    this.email = email;
  }

}

package com.game.store.dto;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.game.store.models.Device;
import com.game.store.models.Genre;
import com.game.store.models.User;

public class UserDto {
  @NotBlank
  @NotNull

  private String name;

  private String email;

  private String password;

  private Set<Device> platforms;

  private Set<Genre> genrePreferences;

  private LocalDate birthDate;

  private String address;

  public User toUser() {
    User user = new User();
    user.setName(this.name);
    user.setEmail(this.email);
    user.setPassword(this.password);
    user.setPlatforms(this.platforms);
    user.setGenrePreferences(this.genrePreferences);
    user.setBirthDate(this.birthDate);
    user.setAddress(this.address);

    return user;
  }

  public User toUser(User user) {
    user.setName(this.name);
    user.setEmail(this.email);
    user.setPassword(this.password);
    user.setPlatforms(this.platforms);
    user.setGenrePreferences(this.genrePreferences);
    user.setBirthDate(this.birthDate);
    user.setAddress(this.address);

    return user;
  }

  public void fromUser(User user) {
    this.name = user.getName();
    this.email = user.getEmail();
    //this.password = user.getPassword();
    this.platforms = user.getPlatforms();
    this.genrePreferences = user.getGenrePreferences();
    this.birthDate = user.getBirthDate();
    this.address = user.getAddress();
  }

  @Override
  public String toString() {
    return "UserDto [name=" + name + ", email=" + email + ", password=" + password + ", plataforma=" + platforms
        + ", genrePreference=" + genrePreferences + ", birthDate=" + birthDate + ", address=" + address + "]";
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
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

  public String getPassword() {
    return password;
  }
}

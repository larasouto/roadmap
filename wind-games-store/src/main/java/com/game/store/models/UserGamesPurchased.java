package com.game.store.models;

public class UserGamesPurchased {

  private String game_name;

  private String game_url;

  private String game_video_url;

  private Boolean game_purchase_status;

  private String device_name;

  public UserGamesPurchased(String game_name, String game_url, String game_video_url, Boolean game_purchase_status,
      String device_name) {
    this.game_name = game_name;
    this.game_url = game_url;
    this.game_video_url = game_video_url;
    this.game_purchase_status = game_purchase_status;
    this.device_name = device_name;
  }

  public String getGame_name() {
    return game_name;
  }

  public void setGame_name(String game_name) {
    this.game_name = game_name;
  }

  public String getGame_url() {
    return game_url;
  }

  public void setGame_url(String game_url) {
    this.game_url = game_url;
  }

  public String getGame_video_url() {
    return game_video_url;
  }

  public void setGame_video_url(String game_video_url) {
    this.game_video_url = game_video_url;
  }

  public Boolean getGame_purchase_status() {
    return game_purchase_status;
  }

  public void setGame_purchase_status(Boolean game_purchase_status) {
    this.game_purchase_status = game_purchase_status;
  }

  public String getDevice_name() {
    return device_name;
  }

  public void setDevice_name(String device_name) {
    this.device_name = device_name;
  }

  @Override
  public String toString() {
    return "UserGamesPurchased [device_name=" + device_name + ", game_name=" + game_name + ", game_purchase_status="
        + game_purchase_status + ", game_url=" + game_url + ", game_video_url=" + game_video_url + "]";
  }
}

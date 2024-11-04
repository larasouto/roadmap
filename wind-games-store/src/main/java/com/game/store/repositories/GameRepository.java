package com.game.store.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.game.store.models.Game;
import com.game.store.models.Genre;

public interface GameRepository extends JpaRepository<Game, Long> {
  List<Genre> findByGenreId(Long id);

  @Query(
    value="SELECT game.name as game_name, game.image_url as game_url, game.video_url as game_video_url, purchases.purchase_status as game_purchase_status, device.name as device_name " + //
        "  FROM purchases " + //
        "  INNER JOIN user_purchased_games " + //
        "  ON purchases.user_id = user_purchased_games.user_id " + //
        "  AND purchases.game_id = user_purchased_games.game_id " + //
        "  INNER JOIN game " + //
        "  on purchases.game_id = game.id " + //
        "  INNER JOIN device " + //
        "  ON game.platform_id = device.id " + //
        "  WHERE purchases.user_id = :userId", //
    nativeQuery = true
  )
  public List<Object[]> findUserGamesPurchasedByUserId(Long userId);
}

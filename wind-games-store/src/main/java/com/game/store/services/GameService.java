package com.game.store.services;

import java.util.List;
import java.util.Optional;

import com.game.store.models.Game;
import com.game.store.models.UserGamesPurchased;

public interface GameService {
    List<Game> getAllGames();

    Optional<Game> getGameById(Long id);

    void saveGame(Game game);

    void deleteGameById(Long id);

    List<UserGamesPurchased> findUserGamesPurchasedByUserId(Long userId);
}


package com.game.store.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.game.store.models.Game;
import com.game.store.models.UserGamesPurchased;
import com.game.store.repositories.GameRepository;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @Override
    public Optional<Game> getGameById(Long id) {
        return gameRepository.findById(id);
    }

    @Override
    public void saveGame(Game game) {
        gameRepository.save(game);
    }

    @Override
    public void deleteGameById(Long id) {
        gameRepository.deleteById(id);
    }

    @Override
    public List<UserGamesPurchased> findUserGamesPurchasedByUserId(Long userId) {
        List<Object[]> results = gameRepository.findUserGamesPurchasedByUserId(userId);
        List<UserGamesPurchased> userGamesPurchased = new ArrayList<>();

        for (Object[] row : results) {
            String gameName = (String) row[0];
            String gameImageUrl = (String) row[1];
            String gameVideoUrl = (String) row[2];
            Boolean gamePurchaseStatus = (Boolean) row[3];
            String deviceName = (String) row[4];

            UserGamesPurchased userGamePurchased = new UserGamesPurchased(gameName, gameImageUrl, gameVideoUrl,
                    gamePurchaseStatus, deviceName);
            userGamesPurchased.add(userGamePurchased);
        }
        return userGamesPurchased;
    }
}

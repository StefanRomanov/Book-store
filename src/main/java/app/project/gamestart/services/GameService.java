package app.project.gamestart.services;

import app.project.gamestart.domain.entities.Game;
import app.project.gamestart.domain.models.binding.GameAddBindingModel;
import app.project.gamestart.domain.models.service.GameServiceModel;

import java.util.List;

public interface GameService {
    void addGame(GameServiceModel gameServiceModel);
}

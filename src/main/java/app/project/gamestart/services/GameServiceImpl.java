package app.project.gamestart.services;

import app.project.gamestart.domain.entities.Game;
import app.project.gamestart.domain.models.binding.GameAddBindingModel;
import app.project.gamestart.domain.models.service.GameServiceModel;
import app.project.gamestart.repositories.GameRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, ModelMapper modelMapper, CloudinaryService cloudinaryService) {
        this.gameRepository = gameRepository;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public void addGame(GameServiceModel serviceModel) {
        Game game = this.modelMapper.map(serviceModel,Game.class);

        String test = "";

        this.gameRepository.saveAndFlush(game);
    }
}

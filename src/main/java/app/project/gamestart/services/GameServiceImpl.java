package app.project.gamestart.services;

import app.project.gamestart.domain.entities.Game;
import app.project.gamestart.domain.enums.GenreName;
import app.project.gamestart.domain.enums.Platform;
import app.project.gamestart.repositories.GameRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public List<Game> test(){
        Game game = new Game();
        game.setDescription("aaa");
        game.setName("asdasd");
        game.setReleaseDate(LocalDate.now());
        game.setApproved(false);
        game.setImages(new HashSet<>());
        game.getImages().add("qwe");
        game.getImages().add("asd");
        game.setPlatform(Platform.PLAYSTATION);
        game.setGenres(new HashSet<>());
        game.getGenres().add(GenreName.CASUAL);
        game.getGenres().add(GenreName.RACING);
        game.setPrice(new BigDecimal(BigInteger.ONE));

        this.gameRepository.saveAndFlush(game);

        List<GenreName> genres = new ArrayList<>();
        genres.add(GenreName.CASUAL);


        return this.gameRepository.findAllByGenresIn(genres);
    }
}

package app.project.gamestart.config;

import app.project.gamestart.domain.enums.GenreName;
import app.project.gamestart.domain.enums.PegiRatings;
import app.project.gamestart.domain.enums.Platform;
import app.project.gamestart.domain.models.binding.GameAddBindingModel;
import app.project.gamestart.domain.models.service.GameServiceModel;
import app.project.gamestart.services.GameService;
import org.apache.tomcat.jni.Local;
import org.modelmapper.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class MapperConfig{

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        this.addMappings(modelMapper);

        return modelMapper;
    }


    private void addMappings(ModelMapper modelMapper) {
        gameServiceModelMapping(modelMapper);
    }

    private void gameServiceModelMapping(ModelMapper modelMapper){
        Converter<GameAddBindingModel, GameServiceModel> converter = new AbstractConverter<GameAddBindingModel, GameServiceModel>() {
            @Override
            protected GameServiceModel convert(GameAddBindingModel source) {
                GameServiceModel destination = new GameServiceModel();
                destination.setTitle(source.getTitle());
                destination.setApproved(false);
                destination.setDescription(source.getDescription());
                destination.setPegiRating(PegiRatings.valueOf(source.getPegiRating()));
                destination.setPlatform(Platform.valueOf(source.getPlatform()));
                for (String genreName : source.getGenres()) {
                    destination.getGenres().add(GenreName.valueOf(genreName));
                }
                destination.setPrice(source.getPrice());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate date = LocalDate.parse(source.getReleaseDate(),formatter);

                destination.setReleaseDate(date);

                return destination;
            }
        };

        modelMapper.addConverter(converter);
    }
}
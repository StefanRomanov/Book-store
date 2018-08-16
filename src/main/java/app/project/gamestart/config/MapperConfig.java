package app.project.gamestart.config;

import app.project.gamestart.domain.enums.Genre;
import app.project.gamestart.domain.enums.PegiRatings;
import app.project.gamestart.domain.enums.Platform;
import app.project.gamestart.domain.models.binding.BookAddBindingModel;
import app.project.gamestart.domain.models.service.BookAddServiceModel;
import app.project.gamestart.domain.models.service.BookServiceModel;
import org.modelmapper.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
        Converter<BookAddBindingModel, BookAddServiceModel> converter = new AbstractConverter<BookAddBindingModel, BookAddServiceModel>() {
            @Override
            protected BookAddServiceModel convert(BookAddBindingModel source) {
                BookAddServiceModel destination = new BookAddServiceModel();
                destination.setTitle(source.getTitle());
                destination.setApproved(false);
                destination.setDescription(source.getDescription());
                for (String genreName : source.getGenres()) {
                    destination.getGenres().add(Genre.valueOf(genreName));
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
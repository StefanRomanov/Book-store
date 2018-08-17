package app.project.gamestart.config;

import app.project.gamestart.domain.enums.Genre;
import app.project.gamestart.domain.models.binding.BookAddBindingModel;
import app.project.gamestart.domain.models.service.AuthorServiceModel;
import app.project.gamestart.domain.models.service.BookAddServiceModel;
import app.project.gamestart.domain.models.service.PublisherServiceModel;
import app.project.gamestart.domain.models.views.AuthorViewModel;
import app.project.gamestart.domain.models.views.PublisherApproveViewModel;
import org.modelmapper.*;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
        publisherApproveViewModelMapping(modelMapper);
        authorViewModelMapping(modelMapper);
    }

    private void gameServiceModelMapping(ModelMapper modelMapper){
        Converter<BookAddBindingModel, BookAddServiceModel> converter = new AbstractConverter<BookAddBindingModel, BookAddServiceModel>() {
            @Override
            protected BookAddServiceModel convert(BookAddBindingModel source) {
                BookAddServiceModel destination = new BookAddServiceModel();
                destination.setTitle(source.getTitle());
                destination.setApproved(false);
                destination.setDescription(source.getDescription());
                destination.setAuthors(source.getAuthors());
                for (String genreName : source.getGenres()) {
                    destination.getGenres().add(Genre.valueOf(genreName));
                }
                destination.setPrice(source.getPrice());
                destination.setReleaseDate(source.getReleaseDate());

                return destination;
            }
        };

        modelMapper.addConverter(converter);
    }

    private void publisherApproveViewModelMapping(ModelMapper modelMapper){
        Converter<PublisherServiceModel, PublisherApproveViewModel> converter = new Converter<PublisherServiceModel, PublisherApproveViewModel>() {
            @Override
            public PublisherApproveViewModel convert(MappingContext<PublisherServiceModel, PublisherApproveViewModel> mappingContext) {
                PublisherApproveViewModel dest = mappingContext.getDestination();
                PublisherServiceModel source = mappingContext.getSource();

                dest.setVatNumber(source.getVatNumber());
                dest.setPostalCode(source.getPostalCode());
                dest.setLegalForm(source.getLegalForm());
                dest.setBillingAddress(source.getBillingAddress());
                dest.setCity(source.getCity());
                dest.setCompanyEmail(source.getCompanyEmail());
                dest.setCompanyName(source.getCompanyName());
                dest.setId(source.getId());
                dest.setCountry(source.getCountry().getFullName());
                return dest;
            }
        };


        modelMapper.addConverter(converter);
    }

    private void authorViewModelMapping(ModelMapper modelMapper){
        Converter<AuthorServiceModel, AuthorViewModel> converter = new Converter<AuthorServiceModel, AuthorViewModel>() {
            @Override
            public AuthorViewModel convert(MappingContext<AuthorServiceModel, AuthorViewModel> mappingContext) {
                AuthorServiceModel source = mappingContext.getSource();
                AuthorViewModel dest = mappingContext.getDestination();

                dest.setId(source.getId());
                dest.setCountry(source.getCountry().getFullName());
                dest.setName(source.getName());

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

                dest.setDateOfBirth(source.getDateOfBirth().format(formatter));

                return dest;
            }
        };

        modelMapper.addConverter(converter);
    }
}
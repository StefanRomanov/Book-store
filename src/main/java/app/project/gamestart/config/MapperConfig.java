package app.project.gamestart.config;

import app.project.gamestart.domain.entities.Author;
import app.project.gamestart.domain.entities.Review;
import app.project.gamestart.domain.enums.Genre;
import app.project.gamestart.domain.models.binding.BookAddBindingModel;
import app.project.gamestart.domain.models.service.*;
import app.project.gamestart.domain.models.views.*;
import org.modelmapper.*;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Type;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class MapperConfig{

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        this.addMappings(modelMapper);

        return modelMapper;
    }


    private void addMappings(ModelMapper modelMapper) {
        bookServiceModelMapping(modelMapper);
        publisherApproveViewModelMapping(modelMapper);
        authorViewModelMapping(modelMapper);
        bookAllViewModelMapping(modelMapper);
        bookDetailsViewMapping(modelMapper);
        addUsernameToReviewServiceModelMapping(modelMapper);
        userRoleViewMapping(modelMapper);
        allPublishersView(modelMapper);
    }

    private void bookServiceModelMapping(ModelMapper modelMapper){
        Converter<BookAddBindingModel, BookAddServiceModel> converter = new AbstractConverter<BookAddBindingModel, BookAddServiceModel>() {
            @Override
            protected BookAddServiceModel convert(BookAddBindingModel source) {
                BookAddServiceModel destination = new BookAddServiceModel();
                destination.setTitle(source.getTitle());
                destination.setApproved(false);
                destination.setDescription(source.getDescription());
                destination.setAuthors(source.getAuthors());
                destination.setGenre(Genre.valueOf(source.getGenre()));
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
                dest.setApproved(source.getApproved());
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

    private void bookAllViewModelMapping(ModelMapper modelMapper){
        Converter<BookServiceModel,BookAllView> converter = new Converter<BookServiceModel, BookAllView>() {
            @Override
            public BookAllView convert(MappingContext<BookServiceModel, BookAllView> mappingContext) {
                BookAllView dest = mappingContext.getDestination();
                BookServiceModel source = mappingContext.getSource();

                dest.setId(source.getId());
                dest.setTitle(source.getTitle());
                dest.setCoverImageUrl(source.getCoverImageUrl());
                dest.setPrice(source.getPrice());
                dest.setGenre(source.getGenre().getFullName());
                Set<String> authorNames = source.getAuthors().stream().map(Author::getName).collect(Collectors.toSet());
                String names = String.join(", ", authorNames);

                dest.setAuthorName(names);
                dest.setReviewScore(source.reviewScore());
                dest.setReleaseDate(source.getReleaseDate());

                return dest;
            }
        };

        modelMapper.addConverter(converter);
    }

    private void bookDetailsViewMapping(ModelMapper modelMapper){
        Converter<BookServiceModel, BookDetailsView> converter = new Converter<BookServiceModel, BookDetailsView>() {
            @Override
            public BookDetailsView convert(MappingContext<BookServiceModel, BookDetailsView> mappingContext) {
                BookDetailsView dest = mappingContext.getDestination();
                BookServiceModel source = mappingContext.getSource();

                dest.setId(source.getId());
                dest.setTitle(source.getTitle());
                dest.setCoverImageUrl(source.getCoverImageUrl());
                dest.setDescription(source.getDescription());
                dest.setReleaseDate(source.getReleaseDate());
                dest.setPublisher(source.getPublisher().getCompanyName());
                dest.setGenre(source.getGenre().getFullName());

                Type type = new TypeToken<Set<ReviewViewModel>>(){}.getType();

                dest.setReviews(modelMapper.map(source.getReviews(),type));
                dest.setApproved(source.getApproved());
                dest.setPrice(source.getPrice());
                dest.setTextFile(source.getTextFile());
                Set<String> authorNames = source.getAuthors().stream().map(Author::getName).collect(Collectors.toSet());
                dest.setAuthors(String.join(", ", authorNames));

                return dest;
            }
        };

        modelMapper.addConverter(converter);
    }

    private void addUsernameToReviewServiceModelMapping(ModelMapper modelMapper){
    Converter<Review,ReviewServiceModel> converter = new Converter<Review, ReviewServiceModel>() {
        @Override
        public ReviewServiceModel convert(MappingContext<Review, ReviewServiceModel> mappingContext) {
            Review source = mappingContext.getSource();
            ReviewServiceModel dest = mappingContext.getDestination();

            dest.setTitle(source.getTitle());
            dest.setText(source.getText());
            dest.setUser(source.getUser());
            dest.setUsername(source.getUser().getUsername());
            dest.setSubmissionDate(source.getSubmissionDate());
            dest.setRecommended(source.getRecommended());
            dest.setBook(source.getBook());
            dest.setId(source.getId());

            return dest;
        }
    };

    modelMapper.addConverter(converter);
    }

    private void userRoleViewMapping(ModelMapper modelMapper){
    Converter<UserServiceModel,UserRoleView> converter = new Converter<UserServiceModel, UserRoleView>() {
        @Override
        public UserRoleView convert(MappingContext<UserServiceModel, UserRoleView> mappingContext) {
            UserServiceModel source = mappingContext.getSource();
            UserRoleView dest = mappingContext.getDestination();

            dest.setId(source.getId());
            dest.setUsername(source.getUsername());
            dest.setUserRole(source.getAuthorities().iterator().next().getAuthority());

            return dest;
        }
    };

    modelMapper.addConverter(converter);
    }

    private void allPublishersView(ModelMapper modelMapper){
        Converter<PublisherServiceModel, AllPublishersViewModel> converter = new Converter<PublisherServiceModel, AllPublishersViewModel>() {
            @Override
            public AllPublishersViewModel convert(MappingContext<PublisherServiceModel, AllPublishersViewModel> mappingContext) {
                PublisherServiceModel source = mappingContext.getSource();
                AllPublishersViewModel dest = mappingContext.getDestination();

                dest.setCountry(source.getCountry().getFullName());
                dest.setCompanyName(source.getCompanyName());
                dest.setId(source.getId());
                dest.setVatNumber(source.getVatNumber());

                return dest;
            }
        };

        modelMapper.addConverter(converter);
    }
}


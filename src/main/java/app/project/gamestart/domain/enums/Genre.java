package app.project.gamestart.domain.enums;

public enum Genre {
    BIOGRAPHY("Biography"),
    FANTASY("Fantasy"),
    DOCUMENTARY("Documentary"),
    SCI_FI("Science Fiction"),
    BUSINESS("Business"),
    CHILDREN("Children"),
    COMICS("Comics"),
    SCIENCE("Science"),
    MYSTERY("Mystery"),
    THRILLER("Thriller"),
    HORROR("Horror");

    private String fullName;

    Genre(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}

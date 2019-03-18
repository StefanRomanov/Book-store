# BookStore #
- “BookStore” is a web application for book sellers. The application is built as a server-side web app using Spring Boot and Thymeleaf as a template engine. Data is stored by using MySQL database. 

- The business logic flow goes as follows. Users can register,login, buy books and leave reviews on owned books. Each user can apply for 'Partnership'. After approval from admin, the user takes the Partner role and can publish books to the application. A Publisher entity is attached to the user and is used in the publishing process. Admins can add books, change roles, edit and delete books as well as deleting publishers. When publisher is deleted, all books his bulished books are deleted as well and the user loses the Partner role. The application has Root admin role which can't be changed.
 

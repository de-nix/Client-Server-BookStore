package ro.ubb.catalog.core.service.ServiceServer;

import org.springframework.data.domain.Sort;
import ro.ubb.catalog.core.model.Book;
import ro.ubb.catalog.core.model.Exceptions.ValidatorException;

import java.util.List;
import java.util.Set;

public interface BookServer {


    Book addBook(Book book) throws ValidatorException;

    /**
     * @param id - the id of the book that will be deleted
     * @return
     */
     void deleteBook(Long id) ;

    /**
     * @param book - the updated book with the same id as the old one
     * @return
     */
     Book updateBook(Long id,Book book) throws ValidatorException ;

    /**
     * @param id - id of the book
     * @return - the book with id = 'id'
     */
    Book getBook(Long id);

    Set<Book> getAllBook();


    List<Book> getSortedBooks();


}

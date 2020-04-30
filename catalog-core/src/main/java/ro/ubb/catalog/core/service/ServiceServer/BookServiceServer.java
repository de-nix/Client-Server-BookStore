package ro.ubb.catalog.core.service.ServiceServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ro.ubb.catalog.core.model.Book;
import ro.ubb.catalog.core.model.Exceptions.ValidatorException;
import ro.ubb.catalog.core.model.Purchase;
import ro.ubb.catalog.core.service.Service.BookServiceImpl;
import ro.ubb.catalog.core.service.Service.PurchaseServiceImpl;

import java.util.List;
import java.util.Set;

@Service
public class BookServiceServer implements BookServer{
    @Autowired
    BookServiceImpl bookServiceImpl;
    @Autowired
    PurchaseServiceImpl purchaseServiceImpl;


    /**
     * @param book - the book that will be added
     * @return
     */
    public synchronized Book addBook(Book book) throws ValidatorException {

        return bookServiceImpl.addBook(book);
    }

    /**
     * @param id - the id of the book that will be deleted
     * @return
     */
    public synchronized void deleteBook(Long id) {


        bookServiceImpl.getBook(id);
        purchaseServiceImpl.getAllPurchases().stream().filter(x -> x.getIdBook().equals(id)).map(Purchase::getIdClient).forEach(x -> {
            purchaseServiceImpl.deletePurchase(x, id);
        });

        bookServiceImpl.deleteBook(id);

    }

    /**
     * @param book - the updated book with the same id as the old one
     * @return
     */
    public synchronized Book updateBook(Long id,Book book) throws ValidatorException {
        return bookServiceImpl.updateBook(id,book);
    }

    /**
     * @param id - id of the book
     * @return - the book with id = 'id'
     */
    public synchronized Book getBook(Long id) {

        return bookServiceImpl.getBook(id);

    }

    public synchronized Set<Book> getAllBook() {
        return bookServiceImpl.getAllBooks();
    }


    public synchronized List<Book> getSortedBooks() {

        Sort sort = Sort.by("price").ascending().and( Sort.by("title").descending());

        return bookServiceImpl.getBooksSorted(sort);
    }
}

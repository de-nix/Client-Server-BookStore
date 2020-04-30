package ro.ubb.catalog.core.service.Service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Book;
import ro.ubb.catalog.core.model.Exceptions.ValidatorException;
import ro.ubb.catalog.core.model.Validators.BookValidator;
import ro.ubb.catalog.core.repository.BookRepo;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
@Service
public class BookServiceImpl {
    @Autowired
    private BookRepo repository;


    public static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);
    public synchronized Book addBook(Book book) throws ValidatorException {
        BookValidator.validate(book);
        return repository.save(book);
    }

    public synchronized void deleteBook(Long id) {
        repository.deleteById(id);
    }
    @Transactional
    public synchronized Book updateBook(Long id,Book book) throws ValidatorException {
        log.trace("updateBook - method entered:id={} book={}", id,book);
        BookValidator.validate(book);
        Optional<Book> optionalBook =  repository.findById(id);
        optionalBook.ifPresent(s -> {
            s.setTitle(book.getTitle());
            s.setAuthor(book.getAuthor());
            s.setPrice(book.getPrice());
            log.debug("updateBook - updated: s={}", s);
        });
        log.trace("updateBook - method finished");
        return optionalBook.orElse(null);


    }
    public synchronized Book getBook(Long id) {
        return repository.findById(id).orElse(null);
    }

    public synchronized List<Book> getBooksSorted(Sort sort){

        return repository.findAll(sort);
    }

    public synchronized Set<Book> getAllBooks() {
        Iterable<Book> Books = repository.findAll();
        return StreamSupport.stream(Books.spliterator(), false).collect(Collectors.toSet());
    }


}

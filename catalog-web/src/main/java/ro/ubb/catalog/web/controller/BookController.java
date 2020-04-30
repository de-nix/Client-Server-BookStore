package ro.ubb.catalog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.catalog.core.model.Exceptions.ValidatorException;
import ro.ubb.catalog.core.service.ServiceServer.BookServer;
import ro.ubb.catalog.web.converter.BookConverter;
import ro.ubb.catalog.web.dto.BookDto;
import ro.ubb.catalog.web.dto.BooksDto;
import ro.ubb.catalog.web.dto.SortedBooksDto;

@RestController
public class BookController {
    public static final Logger log= LoggerFactory.getLogger(ClientController.class);
    @Autowired
    BookConverter bookConverter;
    @Autowired
    BookServer bookService;
    @RequestMapping(value = "/books", method = RequestMethod.GET)
    BooksDto getBooks() {
        log.trace("getBooks - method entered");
        BooksDto result = new BooksDto(bookConverter
                .convertModelsToDtos(bookService.getAllBook()));
        log.trace("getBooks - method finished");
        return result;

    }

//    to do: sort -> dto
    @RequestMapping(value = "/books/sort",method = RequestMethod.GET)
    SortedBooksDto getBooksSorted() {
        log.trace("getBooksSorted - method entered ");

        SortedBooksDto result = new SortedBooksDto(bookConverter
                .convertModelsToSortedDtos(bookService.getSortedBooks()));
        log.trace("getBooksSorted - method finished");
        return result;

    }



    @RequestMapping(value = "/books/{id}", method = RequestMethod.GET)
    BookDto getBook(@PathVariable Long id) {
        log.trace("getBook - method entered: id{}",id);
        BookDto result = bookConverter.convertModelToDto(bookService.getBook(id));
        log.trace("getBook - method finished");
        return result;
    }

    @RequestMapping(value = "/books", method = RequestMethod.POST)
    BookDto saveBook(@RequestBody BookDto bookDto) throws ValidatorException {

        log.trace("saveBook - method entered : bookDTO {}",bookDto);
        BookDto result = bookConverter.convertModelToDto(bookService.addBook(
                bookConverter.convertDtoToModel(bookDto)
        ));
        log.trace("saveBook - method finished");
        return result;
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.PUT)
    BookDto updateBook(@PathVariable Long id, @RequestBody BookDto bookDto) throws ValidatorException {

        log.trace("updateBook - method entered: id {}, bookDto {}",id,bookDto);
        BookDto result=  bookConverter.convertModelToDto( bookService.updateBook(id,
                bookConverter.convertDtoToModel(bookDto)));
        log.trace("updateBook - method finished");
        return result;
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteBook(@PathVariable Long id) {
        log.trace("deleteBook - method entered: id {}",id);
        bookService.deleteBook(id);
        log.trace("deleteBook - method finished");
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    
}

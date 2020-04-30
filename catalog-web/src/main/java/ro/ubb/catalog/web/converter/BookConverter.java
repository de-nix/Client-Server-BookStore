package ro.ubb.catalog.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Book;
import ro.ubb.catalog.web.dto.BookDto;
@Component
public class BookConverter extends BaseConverter<Book, BookDto> {
    @Override
    public Book convertDtoToModel(BookDto dto) {

        Book book = Book.builder().title(dto.getTitle()).author(dto.getAuthor()).price(dto.getPrice()).build();
        book.setId(dto.getId());
        return book;
    }

    @Override
    public BookDto convertModelToDto(Book book) {
        BookDto bookDto = BookDto.builder().title(book.getTitle()).author(book.getAuthor()).price(book.getPrice()).build();
        bookDto.setId(book.getId());
        return bookDto;
    }
}

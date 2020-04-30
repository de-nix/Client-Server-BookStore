package ro.ubb.catalog.core.model.Validators;


import ro.ubb.catalog.core.model.Book;
import ro.ubb.catalog.core.model.Exceptions.ValidatorException;

public class BookValidator  {

    public static void validate(Book entity) throws ValidatorException {
        if (entity==null) throw new ValidatorException("There is no book. Sorry.\n");
        if (entity.getAuthor().equals("")) throw new ValidatorException("There is no author for this book. Sorry.\n");
        if (entity.getTitle().equals("")) throw new ValidatorException("OOPS! You forgot the title!!\n");
        if (entity.getPrice()<=0) throw new ValidatorException("The Price of the book should be greater than 0\n");

    }
}

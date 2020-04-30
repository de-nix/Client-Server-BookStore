package ro.ubb.catalog.core.model.Validators;


import ro.ubb.catalog.core.model.Exceptions.ValidatorException;
import ro.ubb.catalog.core.model.Purchase;

public class PurchaseValidator  {

    public static void validate(Purchase entity) throws ValidatorException {
        if (entity==null) throw new ValidatorException("There is no purchase. Sorry.\n");
        if (entity.getDate() == null) throw new ValidatorException("There is no date\n");
        if (entity.getIdBook()<=0) throw new ValidatorException("OOPS! BookId is invalid!!\n");
        if (entity.getIdClient()<=0) throw new ValidatorException("OOPS! ClientId is invalid!!\n");
    }}


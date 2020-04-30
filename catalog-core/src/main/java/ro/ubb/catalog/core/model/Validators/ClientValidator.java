package ro.ubb.catalog.core.model.Validators;

import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.model.Exceptions.ValidatorException;

public class ClientValidator  {

    public static void validate(Client entity) throws ValidatorException {
        if (entity==null) throw new ValidatorException("There is no client. Sorry.\n");
        if (entity.getName().equals("")) throw new ValidatorException("There is no name\n");
        if (entity.getId()<=0) throw new ValidatorException("OOPS! Id issue!!\n");
    }
}

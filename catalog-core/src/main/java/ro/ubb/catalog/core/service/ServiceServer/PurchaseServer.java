package ro.ubb.catalog.core.service.ServiceServer;

import org.springframework.data.domain.Sort;
import ro.ubb.catalog.core.model.Exceptions.ValidatorException;
import ro.ubb.catalog.core.model.Purchase;

import java.util.List;
import java.util.Set;

public interface PurchaseServer {


     Set<Purchase> getAllPurchases() ;
     List<Purchase> getSortedPurchases();

     Purchase buy(Purchase purchase) throws ValidatorException ;
     void reTurn(Long clientId, Long bookId);

     Purchase changeDate(Purchase purchase) throws ValidatorException;

     Purchase getPurchase(Long clientId, Long bookId);
}

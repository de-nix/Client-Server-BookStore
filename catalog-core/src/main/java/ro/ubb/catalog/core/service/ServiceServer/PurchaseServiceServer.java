package ro.ubb.catalog.core.service.ServiceServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ro.ubb.catalog.core.model.Exceptions.ValidatorException;
import ro.ubb.catalog.core.model.Purchase;
import ro.ubb.catalog.core.service.Service.BookServiceImpl;
import ro.ubb.catalog.core.service.Service.ClientServiceImpl;
import ro.ubb.catalog.core.service.Service.PurchaseServiceImpl;

import java.util.List;
import java.util.Set;


@Service
public class PurchaseServiceServer implements PurchaseServer  {
    @Autowired
    BookServiceImpl bookServiceImpl;
    @Autowired
    ClientServiceImpl clientServiceImpl;
    @Autowired
    PurchaseServiceImpl purchaseServiceImpl;

    public synchronized Set<Purchase> getAllPurchases() {

     return  purchaseServiceImpl.getAllPurchases();

    }

    @Override
    public synchronized List<Purchase> getSortedPurchases() {
        Sort sort = Sort.by("idClient").and(Sort.by("idBook"));
        return purchaseServiceImpl.getPurchasesSorted(sort);
    }

    public synchronized Purchase buy(Purchase purchase) throws ValidatorException {

        clientServiceImpl.getClient(purchase.getIdClient());
        bookServiceImpl.getBook(purchase.getIdBook());
        if (purchaseServiceImpl.findPurchase(purchase.getIdClient(), purchase.getIdBook()) == -1) {
             return purchaseServiceImpl.addPurchase(purchase);
        } else return null;
    }

    public synchronized void reTurn(Long clientId, Long bookId) {
        clientServiceImpl.getClient(clientId);
        bookServiceImpl.getBook(bookId);
        purchaseServiceImpl.deletePurchase(clientId, bookId);
    }

    public synchronized Purchase changeDate(Purchase purchase) throws ValidatorException {

        return purchaseServiceImpl.updatePurchase(purchase);
    }


    public synchronized Purchase getPurchase(Long clientId, Long bookId) {
        Long id = purchaseServiceImpl.findPurchase(clientId, bookId);
        if (id == -1L)return null;
        return purchaseServiceImpl.getPurchase(id);
    }

}


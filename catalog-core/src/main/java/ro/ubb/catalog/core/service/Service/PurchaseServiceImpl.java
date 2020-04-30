package ro.ubb.catalog.core.service.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Exceptions.ValidatorException;
import ro.ubb.catalog.core.model.Purchase;
import ro.ubb.catalog.core.model.Validators.PurchaseValidator;
import ro.ubb.catalog.core.repository.PurchaseRepo;

import java.util.*;
@Service
public class PurchaseServiceImpl {


    public static final Logger log = LoggerFactory.getLogger(PurchaseServiceImpl.class);

    @Autowired
    private PurchaseRepo repository;


    public synchronized Purchase addPurchase(Purchase purchase) throws ValidatorException {
        PurchaseValidator.validate(purchase);
        if(findPurchase(purchase.getIdClient(),purchase.getIdBook()) == -1L) return repository.save(purchase);
        return null;
    }

    public synchronized void deletePurchase(Long idClient, Long idBook) {
        Long id = findPurchase(idClient,idBook);
        repository.deleteById(id);
    }

    public synchronized Purchase getPurchase(Long id) {
        return repository.findById(id).orElse(null);
    }
    @Transactional
    public synchronized Purchase updatePurchase(Purchase purchase) throws ValidatorException {
        PurchaseValidator.validate(purchase);
        log.trace("updatePurchase - method entered: purchase={}", purchase);
        Optional<Purchase> optionalPurchase = repository.findById(findPurchase(purchase.getIdClient(),purchase.getIdBook()));
        optionalPurchase.ifPresent(s -> {
            s.setDate(purchase.getDate());
            log.debug("updatePurchase - updated: s={}", s);
        });
        log.trace("updatePurchase - method finished");
        return optionalPurchase.orElse(null);
    }

    public synchronized Set<Purchase> getAllPurchases() {
        Iterable<Purchase> purchases = repository.findAll();
        Set<Purchase> result = new HashSet<>();
        purchases.forEach(result::add);
        return result;
    }
    public synchronized Long findPurchase(Long idClient,Long idBook){
        List<Purchase> listP = new ArrayList<>(repository.findAll());
        Optional<Purchase> optional=  listP.stream().filter(x->x.getIdClient().equals(idClient) && x.getIdBook().equals(idBook)).findFirst();
        if (optional.isPresent()) return optional.get().getId();
        return -1L;
    }

    public synchronized List<Purchase> getPurchasesSorted(Sort sort){
        return repository.findAll(sort);
    }

}

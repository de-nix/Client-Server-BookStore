package ro.ubb.catalog.core.service.ServiceServer;

import ro.ubb.catalog.core.model.Book;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.model.Purchase;

import java.util.*;
import java.util.stream.Collectors;

public interface ReportsServer {

     Set<Client> filterClientsWithMoreThanXBooks(int no);

    /**
     * @return sorted list of the clients by the total amount of spent money
     */
     List<Client> sortClientsByMoneySpent() ;


    /**
     * Returns all Books whose name contain the given string.
     *
     * @param s - the given string
     * @return - set of books that contains the given string
     */
     Set<Book> filterBooksByTitles(String s);
    /**
     * @param s - the given price
     * @return - a set of books that have the price greater than s
     */

     Set<Book> removeBookIfPriceLessThenS(int s) ;

    /**
     * @param s - a given string
     * @return - the set of clients whose names contain that string
     */

     Set<Client> filterClientsByName(String s);


     Set<Purchase> filterPurchasesByDateBefore(Date date);

     Set<Purchase> getAllPurchaseFromClientX(Long idClient) ;

     Set<Purchase> getAllPurchaseofBookX(Long idBook) ;
}

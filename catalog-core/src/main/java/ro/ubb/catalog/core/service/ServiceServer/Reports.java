package ro.ubb.catalog.core.service.ServiceServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.catalog.core.model.Book;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.model.Purchase;
import ro.ubb.catalog.core.service.Service.BookServiceImpl;
import ro.ubb.catalog.core.service.Service.ClientServiceImpl;
import ro.ubb.catalog.core.service.Service.PurchaseServiceImpl;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class Reports implements ReportsServer  {
    @Autowired
    BookServiceImpl bookServiceImpl;
    @Autowired
    ClientServiceImpl clientServiceImpl;
    @Autowired
    PurchaseServiceImpl purchaseServiceImpl;

    /**
     * @param no - the given minimum number of books that a client should have
     * @return - the set of clients that have more than x books
     */
    public synchronized Set<Client> filterClientsWithMoreThanXBooks(int no) {
        return clientServiceImpl.getAllClients().stream().
                filter(x ->
                        no < purchaseServiceImpl.getAllPurchases().stream().
                                filter(y -> y.getIdClient().equals(x.getId())).count()).
                collect(Collectors.toSet());
    }

    /**
     * @param clientId - the id of the client
     * @return - the sum spent by the client on bokos
     */
    public synchronized Integer spentMoney(Long clientId) {

        Set<Long> bookIds = purchaseServiceImpl.getAllPurchases().stream().filter(x -> x.getIdClient().
                equals(clientId)).map(Purchase::getIdBook).collect(Collectors.toSet());
        return bookServiceImpl.getAllBooks().stream().filter(x -> bookIds.contains(x.getId())).
                map(Book::getPrice).reduce(0, Integer::sum);
    }

    /**
     * @return sorted list of the clients by the total amount of spent money
     */
    public synchronized List<Client> sortClientsByMoneySpent() {

        List<Client> clients = new ArrayList<>(clientServiceImpl.getAllClients());
        clients.sort(Comparator.comparing(c -> spentMoney(c.getId())));
        return clients;
    }


    /**
     * Returns all Books whose name contain the given string.
     *
     * @param s - the given string
     * @return - set of books that contains the given string
     */
    public synchronized Set<Book> filterBooksByTitles(String s) {
        Set<Book> filteredBooks = bookServiceImpl.getAllBooks();
        filteredBooks.removeIf(Book -> !Book.getTitle().contains(s));
        return filteredBooks;
    }

    /**
     * @param s - the given price
     * @return - a set of books that have the price greater than s
     */

    public synchronized Set<Book> removeBookIfPriceLessThenS(int s) {
        Set<Book> filteredBooks = bookServiceImpl.getAllBooks();
        filteredBooks.removeIf(book -> book.getPrice() <= s);
        return filteredBooks;
    }

    /**
     * @param s - a given string
     * @return - the set of clients whose names contain that string
     */

    public synchronized Set<Client> filterClientsByName(String s) {

        Set<Client> filteredClients = clientServiceImpl.getAllClients();
        filteredClients.removeIf(client -> !client.getName().contains(s));
        return filteredClients;
    }


    public synchronized Set<Purchase> filterPurchasesByDateBefore(Date date) {

        return purchaseServiceImpl.getAllPurchases().stream().filter(x -> x.getDate().before(date)).
                collect(Collectors.toSet());
    }


    public synchronized Set<Purchase> getAllPurchaseFromClientX(Long idClient) {
        return purchaseServiceImpl.getAllPurchases().stream().filter(x -> x.getIdClient().equals(idClient)).
                collect(Collectors.toSet());
    }

    public synchronized Set<Purchase> getAllPurchaseofBookX(Long idBook) {
        return purchaseServiceImpl.getAllPurchases().stream().filter(x -> x.getIdBook().equals(idBook)).
                collect(Collectors.toSet());
    }

}
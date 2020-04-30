package ro.ubb.catalog.core.service.ServiceServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.model.Exceptions.ValidatorException;
import ro.ubb.catalog.core.model.Purchase;
import ro.ubb.catalog.core.service.Service.ClientServiceImpl;
import ro.ubb.catalog.core.service.Service.PurchaseServiceImpl;

import java.util.List;
import java.util.Set;

@Service
public class ClientServiceServer implements ClientServer {
    @Autowired
    ClientServiceImpl clientServiceImpl;
    @Autowired
    PurchaseServiceImpl purchaseServiceImpl;


    /**
     * @param client - the client that will be added
     * @return
     */
    public synchronized Client addClient(Client client) throws ValidatorException {

        return clientServiceImpl.addClient(client);
    }

    /**
     * @param id - the client that will be deleted
     *
     */
    public synchronized void deleteClient(Long id)  {

        clientServiceImpl.getClient(id);
        purchaseServiceImpl.getAllPurchases().stream().filter(x -> x.getIdClient().equals(id)).map(Purchase::getIdBook).forEach(x -> {
            purchaseServiceImpl.deletePurchase( id,x);
        });
        clientServiceImpl.deleteClient(id);
    }

    /**
     * @param client - the updated client with the same id as the old one
     * @return

     */
    public synchronized Client updateClient(Long id,Client client) throws ValidatorException {

        return clientServiceImpl.updateClient(id,client);

    }

    /**
     * @param id - the id of a client
     * @return - the client with the id = 'id'
     */
    public synchronized Client getClient(Long id) {


            return clientServiceImpl.getClient(id);


    }

    /**
     * @return - a set of all the clients
     */

    public synchronized Set<Client> getAllClient() {
        return clientServiceImpl.getAllClients();
    }
    public synchronized List<Client> getSortedClients() {
        Sort sort  = Sort.by("name").descending();
        return clientServiceImpl.getClientsSortedByName(sort);
    }

}

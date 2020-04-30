package ro.ubb.catalog.core.service.ServiceServer;

import org.springframework.data.domain.Sort;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.model.Exceptions.ValidatorException;

import java.util.List;
import java.util.Set;

public interface ClientServer {

     Client addClient(Client client) throws ValidatorException;

    /**
     * @param id - the client that will be deleted
     *
     */
     void deleteClient(Long id)  ;

    /**
     * @param client - the updated client with the same id as the old one
     * @return

     */
     Client updateClient(Long id,Client client) throws ValidatorException ;

    /**
     * @param id - the id of a client
     * @return - the client with the id = 'id'
     */
     Client getClient(Long id) ;

    /**
     * @return - a set of all the clients
     */

     Set<Client> getAllClient() ;
     List<Client> getSortedClients();
}

package ro.ubb.catalog.core.service.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.model.Exceptions.ValidatorException;
import ro.ubb.catalog.core.model.Validators.ClientValidator;
import ro.ubb.catalog.core.repository.ClientRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
@Service
public class ClientServiceImpl {
    @Autowired
    private ClientRepo repository;


    public static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    public synchronized Client addClient(Client client) throws ValidatorException {
        ClientValidator.validate(client);
        return repository.save(client);
    }

    public synchronized void deleteClient(Long id) {
        repository.deleteById(id);
    }

    public synchronized List<Client> getClientsSortedByName(Sort sort){

        Iterable<Client> clients = repository.findAll(sort);
        List<Client> l = new ArrayList<>();
        clients.forEach(l::add);
        return l;
    }

    public synchronized Client getClient(Long id) {
        return repository.findById(id).orElse(null);
    }
    @Transactional
    public synchronized Client updateClient(Long id,Client client) throws ValidatorException {
        ClientValidator.validate(client);
        log.trace("updateClient - method entered: id ={}, client={}", id,client);
        Optional<Client> optionalClient = repository.findById(id);
        optionalClient.ifPresent(s -> {
            s.setName(client.getName());
            log.debug("updateClient - updated: s={}", s);
        });

        log.trace("updateClient - method finished");
        return optionalClient.orElse(null);
    }

    public synchronized Set<Client> getAllClients() {
        Iterable<Client> clients = repository.findAll();
        return StreamSupport.stream(clients.spliterator(), false).collect(Collectors.toSet());
    }


}

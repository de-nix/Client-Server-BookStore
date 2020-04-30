package ro.ubb.catalog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.catalog.core.model.Exceptions.ValidatorException;
import ro.ubb.catalog.core.service.ServiceServer.ClientServer;
import ro.ubb.catalog.web.converter.ClientConverter;
import ro.ubb.catalog.web.dto.ClientDto;
import ro.ubb.catalog.web.dto.ClientsDto;
import ro.ubb.catalog.web.dto.SortedClientsDto;

@RestController
public class ClientController {

    public static final Logger log= LoggerFactory.getLogger(ClientController.class);
    @Autowired
    ClientConverter clientConverter;
    @Autowired
    ClientServer clientService;
    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    ClientsDto getClients() {
        log.trace("getClients - method entered");
        ClientsDto result = new ClientsDto(clientConverter
                .convertModelsToDtos(clientService.getAllClient()));
        log.trace("getClients - method finished");
        return result;

    }

    @RequestMapping(value = "/clients/sort", method = RequestMethod.GET)
    SortedClientsDto getClientsSorted() {
        log.trace("getClientsSorted - method entered");
        SortedClientsDto result = new SortedClientsDto(clientConverter
                .convertModelsToSortedDtos(clientService.getSortedClients()));
        log.trace("getClientsSorrted - method finished");
        return result;

    }

    @RequestMapping(value = "/clients/{id}", method = RequestMethod.GET)
    ClientDto getClient(@PathVariable Long id) {
        log.trace("getClient - method entered: id{}",id);
        ClientDto result = clientConverter.convertModelToDto(clientService.getClient(id));
        log.trace("getClient - method finished");
        return result;
    }

    @RequestMapping(value = "/clients", method = RequestMethod.POST)
    ClientDto saveClient(@RequestBody ClientDto clientDto) throws ValidatorException {

        log.trace("saveClient - method entered : clientDTO {}",clientDto);
        ClientDto result = clientConverter.convertModelToDto(clientService.addClient(
                clientConverter.convertDtoToModel(clientDto)
        ));
        log.trace("saveClient - method finished");
        return result;
    }

    @RequestMapping(value = "/clients/{id}", method = RequestMethod.PUT)
    ClientDto updateClient(@PathVariable Long id, @RequestBody ClientDto clientDto) throws ValidatorException {

        log.trace("updateClient - method entered: id {}, clientDto {}",id,clientDto);
        ClientDto result=  clientConverter.convertModelToDto( clientService.updateClient(id,
                clientConverter.convertDtoToModel(clientDto)));
        log.trace("updateClient - method finished");
        return result;
    }

    @RequestMapping(value = "/clients/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteClient(@PathVariable Long id) {
        log.trace("deleteClient - method entered: id {}",id);
        clientService.deleteClient(id);
        log.trace("deleteClient - method finished");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

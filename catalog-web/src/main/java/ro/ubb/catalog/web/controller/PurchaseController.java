package ro.ubb.catalog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.catalog.core.model.Exceptions.ValidatorException;
import ro.ubb.catalog.core.service.ServiceServer.PurchaseServer;
import ro.ubb.catalog.web.converter.PurchaseConverter;
import ro.ubb.catalog.web.dto.PurchaseDto;
import ro.ubb.catalog.web.dto.PurchasesDto;
@RestController
public class PurchaseController {


    public static final Logger log= LoggerFactory.getLogger(PurchaseController.class);
    @Autowired
    PurchaseConverter purchaseConverter;
    @Autowired
    PurchaseServer purchaseService;
    @RequestMapping(value = "/purchases", method = RequestMethod.GET)
    PurchasesDto getPurchases() {
        log.trace("getPurchases - method entered");
        PurchasesDto result = new PurchasesDto(purchaseConverter
                .convertModelsToDtos(purchaseService.getAllPurchases()));
        log.trace("getPurchases - method finished");
        return result;

    }

    @RequestMapping(value = "/purchases", method = RequestMethod.GET)
    PurchasesDto getPurchasesSorted() {
        log.trace("getPurchasesSorted - method entered");
        PurchasesDto result = new PurchasesDto(purchaseConverter
                .convertModelsToDtos(purchaseService.getSortedPurchases()));
        log.trace("getPurchasesSorrted - method finished");
        return result;

    }

    @RequestMapping(value = "/purchases/{idClient}/{idBook}", method = RequestMethod.GET)
    PurchaseDto getPurchase(@PathVariable Long idClient, @PathVariable Long idBook) {
        log.trace("getPurchase - method entered: idClient{}, idBook{}",idClient,idBook);
        PurchaseDto result = purchaseConverter.convertModelToDto(purchaseService.getPurchase(idClient, idBook));
        log.trace("getPurchase - method finished");
        return result;
    }

    @RequestMapping(value = "/purchases", method = RequestMethod.POST)
    PurchaseDto savePurchase(@RequestBody PurchaseDto purchaseDto) throws ValidatorException {

        log.trace("savePurchase - method entered : purchaseDTO {}",purchaseDto);
        PurchaseDto result = purchaseConverter.convertModelToDto(purchaseService.buy(
                purchaseConverter.convertDtoToModel(purchaseDto)
        ));
        log.trace("savePurchase - method finished");
        return result;
    }

    @RequestMapping(value = "/purchases", method = RequestMethod.PUT)
    PurchaseDto updatePurchase( @RequestBody PurchaseDto purchaseDto) throws ValidatorException {

        log.trace("updatePurchase - method entered:  purchaseDto {}",purchaseDto);
        PurchaseDto result=  purchaseConverter.convertModelToDto( purchaseService.changeDate(
                purchaseConverter.convertDtoToModel(purchaseDto)));
        log.trace("updatePurchase - method finished");
        return result;
    }

    @RequestMapping(value = "/purchases/{idClient}/{idBook}", method = RequestMethod.DELETE)
    ResponseEntity<?> deletePurchase(  @PathVariable Long idClient,@PathVariable Long idBook) {
        log.trace("deletePurchase - method entered: idClient {}, idBook{}",idClient,idBook);
        purchaseService.reTurn(idClient,idBook);
        log.trace("deletePurchase - method finished");
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
}

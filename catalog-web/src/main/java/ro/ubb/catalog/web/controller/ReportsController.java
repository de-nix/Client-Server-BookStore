package ro.ubb.catalog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.ubb.catalog.core.service.ServiceServer.*;
import ro.ubb.catalog.web.converter.BookConverter;
import ro.ubb.catalog.web.converter.ClientConverter;
import ro.ubb.catalog.web.converter.PurchaseConverter;
import ro.ubb.catalog.web.dto.BooksDto;
import ro.ubb.catalog.web.dto.ClientsDto;
import ro.ubb.catalog.web.dto.PurchasesDto;
import ro.ubb.catalog.web.dto.SortedClientsDto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
@RestController

public class ReportsController {

    public static final Logger log= LoggerFactory.getLogger(ReportsController.class);
    @Autowired
    PurchaseConverter purchaseConverter;
    @Autowired
    ReportsServer reports;
    @Autowired
    ClientConverter clientConverter;
    @Autowired
    BookConverter bookConverter;
//
    @RequestMapping(value = "/clients/name/{name}",method = RequestMethod.GET)
    ClientsDto getFilteredClientsByName(@PathVariable String name){
        log.trace("getFilteredClientsByName : method entered : name{}",name);
        ClientsDto result  = new ClientsDto(clientConverter.convertModelsToDtos(reports.filterClientsByName(name)));
        log.trace("getFilteredClientsByName: method finished");
        return result;
    }
    @RequestMapping(value="/clients/noBooks/{noBooks}", method = RequestMethod.GET)
        ClientsDto getFilteredClientsByNOBooks(@PathVariable int noBooks) {
        log.trace("getFilteredClientsByNOBooks - method entered : noBooks{}",noBooks);
        ClientsDto result = new ClientsDto(clientConverter.convertModelsToDtos(reports.filterClientsWithMoreThanXBooks(noBooks)));
        log.trace("getFilteredClientsBynoBooks - method fineshed");
        return result;
    }
    @RequestMapping(value="/clients/moneySort", method = RequestMethod.GET)
    SortedClientsDto getSortedClientsByMoneySpent() {
        log.trace("getSortedClientsByMoneySpent - method entered");
        SortedClientsDto result = new SortedClientsDto(clientConverter.convertModelsToSortedDtos(reports.sortClientsByMoneySpent()));
        log.trace("getSortedClientsByMoneySpent - method fineshed");
        return result;
    }

    @GetMapping(value = "/books/title/{title}")
    BooksDto getFilteredBooksByTitle(@PathVariable String  title){
        log.trace("getFilteredBooksByTitle : method entered : title{}",title);
        BooksDto result  = new BooksDto(bookConverter.convertModelsToDtos(reports.filterBooksByTitles(title)));
        log.trace("getFilteredBooksByTitle: method finished");
        return result;
    }

    @RequestMapping(value = "/books/price/{price}" , method = RequestMethod.GET)
    BooksDto getFilteredBooksByPrice(@PathVariable int  price){
        log.trace("getFilteredBooksByPrice : method entered : price{}",price);
        BooksDto result  = new BooksDto(bookConverter.convertModelsToDtos(reports.removeBookIfPriceLessThenS(price)));
        log.trace("getFilteredBooksByPrice: method finished");
        return result;
    }

    @RequestMapping(value = "/purchases/date/{date}",method = RequestMethod.GET)
    PurchasesDto getFilteredPurchasesByDate(@PathVariable Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        log.trace("getFilteredPurchasesByDate : method entered : date{}",simpleDateFormat.format(date));
        PurchasesDto result  = new PurchasesDto(purchaseConverter.convertModelsToDtos(reports.filterPurchasesByDateBefore(date)));
        log.trace("getFilteredPurchasesByDate: method finished");
        return result;
    }
}

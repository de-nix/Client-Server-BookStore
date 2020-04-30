package ro.ubb.catalog.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ro.ubb.catalog.core.model.Exceptions.ValidatorException;
import ro.ubb.catalog.web.dto.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by radu.
 */
@Component
public class Console {


    public static final String URLclients = "http://localhost:8080/api/clients";
    public static final String URLbooks = "http://localhost:8080/api/books";
    public static final String URLpurchases = "http://localhost:8080/api/purchases";

    @Autowired
    RestTemplate restTemplate;

    public void runConsole() throws IOException {
        boolean ok=true;

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        while(ok) {
            System.out.println("Please choose one of the above:" +
                    "\n1.Add book" +
                    "\n2.Update book" +
                    "\n3.Delete book" +
                    "\n4.Filter books by title" +
                    "\n5.Filter books by price" +
                    "\n6.Print all books" +
                    "\n7.Add client" +
                    "\n8.Delete client" +
                    "\n9.Update client" +
                    "\n10.Filter clients by name" +
                    "\n11.Filter clients by number of books" +
                    "\n12.Print all clients" +
                    "\n13.Buy a book" +
                    "\n14.Return a book" +
                    "\n15.Modify a purchase date" +
                    "\n16.Filter purchases by date : 'Before'" +
                    "\n17.Print all purchases" +
                    "\n18.Sort the clients by the money spent on books" +
                    "\n19.Sort the clients by name descending" +
                    "\n20.Sort the Books by price ascending and descending by name" +
                    "\n21.Sort purchases by clientID ascending and ascending by bookID" +


                    "\n0.exit\n");

            int a = Integer.parseInt(bufferRead.readLine());
            try {
                switch (a) {
                    case 0:
                        ok = false;
                        break;
                    case 1:
                        addBook();
                        break;
                    case 2:
                        updateBook();
                        break;
                    case 3:
                        deleteBook();
                        break;
                    case 4:
                        filterBooksByTitle();
                        break;
                    case 5:
                        filterBooksByPrice();
                        break;
                    case 6:
                        printAllBooks();
                        break;
                    case 7:
                        addClient();
                        break;
                    case 8:
                        deleteClient();
                        break;
                    case 9:
                        updateClient();
                        break;
                    case 10:
                        filterClientsByName();
                        break;
                    case 11:
                        filterClientsByNoBooks();
                        break;
                    case 12:
                        printAllClients();
                        break;
                    case 13:
                        buy();
                        break;
                    case 14:
                        reTurn();
                        break;
                    case 15:
                        changeDate();
                        break;
                    case 16:
                        getAllPurchasesBefore();
                        break;
                    case 17:
                        printAllPurchases();
                        break;
                    case 18:
                        sortClientsBySpentMoney();
                        break;
                    case 19:
                        sortClientsByName();
                        break;
                    case 20:
                        sortBooks();
                        break;
                    case 21:
                        sortPurchases();
                        break;

                }
            }catch (IOException | ValidatorException | ParseException ex){
                System.err.println(ex.toString());
            }

        }

    }

    private void printAllPurchases() {
        System.out.println(restTemplate.getForObject(URLpurchases,PurchasesDto.class));
        //purchaseService.getAllPurchases().forEach(System.out::println);
    }

    private void getAllPurchasesBefore() throws IOException, ParseException {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("give the date for filtering\n");
        String lineInput;
        lineInput = bufferRead.readLine();
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(lineInput);
        System.out.println(restTemplate.getForObject(URLpurchases+"/date",PurchasesDto.class,date));
    }

    private void changeDate() throws IOException, ParseException, ValidatorException {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("give the client id\n");
        Long clientId = Long.valueOf(bufferRead.readLine());// ...
        System.out.println("give the book id\n");
        Long bookId = Long.valueOf(bufferRead.readLine());//
        System.out.println("give the new Date\n");
        String lineInput;
        lineInput = bufferRead.readLine();
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(lineInput);
        restTemplate.put(URLpurchases,new PurchaseDto(clientId,bookId,date));
    }

    private void reTurn() throws IOException {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("give the client id\n");
        Long clientId = Long.valueOf(bufferRead.readLine());// ...
        System.out.println("give the book id\n");
        Long bookId = Long.valueOf(bufferRead.readLine());//
        restTemplate.delete(URLpurchases+"/{idClient}/{idBook}",clientId,bookId);
//        purchaseService.reTurn(clientId,bookId);

    }

    private void buy() throws IOException, ParseException, ValidatorException {

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("give the client id\n");
        Long clientId = Long.valueOf(bufferRead.readLine());// ...
        System.out.println("give the book id\n");
        Long bookId = Long.valueOf(bufferRead.readLine());//
        System.out.println("give the date of the purchase\n");
        String lineInput;
        lineInput = bufferRead.readLine();
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(lineInput);
        restTemplate.postForObject(URLpurchases,new PurchaseDto(clientId,bookId,date), PurchaseDto.class);
//        purchaseService.buy(new Purchase(clientId,bookId,date));

    }

    private Long readId(){
        Scanner inn = new Scanner(System.in);
        System.out.println("Enter the id of the entity:\n");
        return inn.nextLong();
    }

    private void filterBooksByTitle() {
        Scanner inn = new Scanner(System.in);
        System.out.println("Enter the filter parameter\n");
        String title = inn.nextLine();
        System.out.println("Filtered Books (title containing '"+title+"'):\n");
        System.out.println(restTemplate.getForObject(URLbooks+"/title/{title}",BooksDto.class,title));
    }

    private void filterBooksByPrice() {
        Scanner inn = new Scanner(System.in);
        System.out.println("Enter the price you want to filter by:\n");
        Integer price = inn.nextInt();
        System.out.println("Filtered Books (price greater than '"+ price +"'):\n");
        System.out.println(restTemplate.getForObject(URLbooks+"/price/{price}",BooksDto.class,price));
    }

    private void printAllBooks() {

        BooksDto result  = restTemplate.getForObject(URLbooks,BooksDto.class);
        System.out.println(result);
    }
    private void printAllClients() {

        ClientsDto result  = restTemplate.getForObject(URLclients,ClientsDto.class);
        System.out.println(result);
    }

    private void addBook() throws IOException {

        BookDto book = readBook();
        restTemplate.postForObject(URLbooks,book, BookDto.class);
    }

    private void deleteBook() {
        Long id = readId();
        restTemplate.delete(URLbooks+ "/{id}",id);
    }
    private void updateBook() throws IOException {

        BookDto book = readBook();
        Long id = readId();

        restTemplate.put(URLbooks+ "/{id}",book,id);
    }


    private BookDto readBook() throws IOException {
        System.out.println("Read Book {id,title, author, price, quantity}\n");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Give the Title of the book\n");
        String title = bufferRead.readLine();
        System.out.println("Give the author of the book\n");
        String author = bufferRead.readLine();
        System.out.println("Give the price of the book\n");
        int price = Integer.parseInt(bufferRead.readLine());
        return new BookDto(title,author,price);

    }

    private void addClient() throws IOException, ValidatorException {
        ClientDto client = readClient();
        restTemplate.postForObject(URLclients,client, ClientDto.class);
    }

    private void deleteClient() {
        Long id = readId();
        restTemplate.delete(URLclients+"/{id}",id);
    }

    private void updateClient() throws IOException, ValidatorException {
        ClientDto client = readClient();
        Long id = readId();
        restTemplate.put(URLclients+"{id}",client,id);
    }
    private ClientDto readClient() throws IOException {
        System.out.println("Read Client {name}\n");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Give the name of the client\n");
        String name = bufferRead.readLine();
        return new ClientDto(name);
    }

    private void filterClientsByName(){
        Scanner inn = new Scanner(System.in);
        System.out.println("Enter the filter parameter\n");
        String name = inn.nextLine();
        System.out.println("Filtered Clients (name containing '"+name+"'):\n");
        System.out.println(restTemplate.getForObject(URLclients+"/{name}",ClientsDto.class,name));
    }

    private void filterClientsByNoBooks(){
        Scanner inn = new Scanner(System.in);
        System.out.println("Enter the NO books a client must have:\n");
        int booksNO = inn.nextInt();
        System.out.println("Filtered Clients (NO books greater than '"+ booksNO +"'):\n");
        System.out.println(restTemplate.getForObject(URLclients+"/{noBooks}",ClientsDto.class,booksNO));
    }

    private void sortClientsBySpentMoney(){
        System.out.println(restTemplate.getForObject(URLclients+"/moneySort",SortedClientsDto.class));
    }


    private void sortClientsByName(){
//        Scanner inn = new Scanner(System.in);
//        System.out.println("Enter 1 for ascending sorting or 2 for descending sorting\n");
//        int x = inn.nextInt();
//        Sort sort ;
//        if(x == 1){
//            sort = new Sort("name");
//        }
//        else sort = new Sort(Sort.Dir.descending,"name");
//        System.out.println();
//        clientService.getSortedClients(sort).forEach(System.out::println);
        System.out.println(restTemplate.getForObject(URLclients+"/sort",SortedBooksDto.class));

    }

    private void sortBooks(){
        SortedBooksDto result = restTemplate.getForObject(URLbooks+"/sort", SortedBooksDto.class);
        System.out.println(result);
    }

    private void sortPurchases(){
//        Sort sort = new Sort("idClient","idBook");
//        purchaseService.getSortedPurchases(sort).forEach(System.out::println);

        System.out.println(restTemplate.getForObject(URLpurchases+"/sort",SortedPurchasesDto.class));
    }
}

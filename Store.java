import java.util.*;
import java.rmi.*;

public class Store extends UnicastRemoteObject implements RMIInterface{

    private static final long serialVersionUID = 1L;
    private List<Book> bookList;

    protected Store(List<Book> list) throws RemoteException{
        super();
        this.bookList = list;
    }

    // The client sends a Book object with the isbn information on it 
    
    // With this method the server searches in the List bookList 
    // for any book that has that isbn and returns the whole object
    @Override
    public Book findBook(Book book) throws RemoteException{
        Predicate<Book> predicate = x -> x.getIsbn().equals(book.getIsbn());
        return bookList.stream().filter(predicate).findFirst().get();
    }

    @Override
    public List<Book> allBooks() throws RemoteException{
        return bookList;
    }

    private static List<Book> initializeList(){
        List<Book> list = new ArrayList<>();
        list.add(new Book("Example Book 1", "978-0596009205", 10.00));
        list.add(new Book("Example Book 2", "978-0596007737", 11.00));
        list.add(new Book("Example Book 3", "978-0071808552", 12.00));
        return list;
    }

    public static void main(String[] args){
        try{
            Naming.rebind("//localhost/Store", new Store(initializeList()));
            System.err.println("Server ready");
        }
        catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }

}

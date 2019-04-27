import java.util.*;
import java.rmi.*;
import java.rmi.server.*;

public class Store extends UnicastRemoteObject implements RMIInterface{

    private static final long serialVersionUID = 1L;
    private List<Book> bookList;

    protected Store(List<Book> list) throws RemoteException{
        super();
        this.bookList = list;
    }

    // The client sends a Book object with the isbn info on it 
    
    // The server searches in bookList for any book that has that the isbn
    @Override
    public Book findBook(Book book) throws RemoteException{
        Book returnBook = null;
        for (Book specificBook : bookList){
            if (specificBook.getIsbn().equals(book.getIsbn())){
                returnBook = specificBook;
            }
        }
        return returnBook;
    }

    @Override
    public List<Book> allBooks() throws RemoteException{
        return bookList;
    }

    private static List<Book> initializeList(){
        List<Book> list = new ArrayList<>();
        list.add(new Book("Example Book 1", "123", 10.00));
        list.add(new Book("Example Book 2", "456", 11.00));
        list.add(new Book("Example Book 3", "789", 12.00));
        return list;
    }

    public static void main(String[] args){
        try{
            Naming.rebind("//155.246.140.1/MyBookstore", new Store(initializeList()));
            System.err.println("Server ready");
        }
        catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }

}

import java.rmi.*;
import java.util.List;

interface RMIInterface extends Remote{

    Book findBook(Book b) throws RemoteException;
    List<Book> allBooks() throws RemoteException;
}

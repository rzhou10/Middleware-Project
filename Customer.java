import java.net.*;
import java.rmi.*;
import java.util.*;
import javax.swing.*;

public class Customer{

    private static RMIInterface look_up;

    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException{

        look_up = (RMIInterface)Naming.lookup("//localhost/MyBookstore");

        boolean findmore;

        do{
            String[] options = {"Show All", "Find a book", "Exit"};

            int choice = JOptionPane.showOptionDialog(null, "Hello, what would you like to do today?", "Action",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null, options, options[0]);

            switch (choice){
                case 0:
                    List<Book> list = look_up.allBooks();
                    StringBuilder message = new StringBuilder();
                    list.forEach(x ->{
                        message.append(x.toString() + "\n");
                    });

                    JOptionPane.showMessageDialog(null, new String(message));
                    break;
                case 1:
                    String isbn = JOptionPane.showInputDialog("Type the isbn of the book you want to find.");
                    try{
                        Book response = look_up.findBook(new Book(isbn));
                        JOptionPane.showMessageDialog(null, "Title: " +
                                        response.getTitle() + "\n" + "Cost: $" +
                                        response.getCost(),
                                response.getIsbn(), JOptionPane.INFORMATION_MESSAGE);
                    }
                    catch (NoSuchElementException ex){
                        JOptionPane.showMessageDialog(null, "Not found");
                    }
                    break;
                default:
                    System.exit(0);
                    break;
            }
            findmore = (JOptionPane.showConfirmDialog(null, "Do you want to exit?", "Exit",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION);
        } while (findmore);
    }
}

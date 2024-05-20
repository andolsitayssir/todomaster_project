package ToDo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    public  Connection cnx;
    public  Connection connectDB(){
       String url="jdbc:mysql://localhost:3306/todomaster";
       String user="root";
       String password="";
       try{
           Class.forName("com.mysql.jdbc.Driver");
           cnx= DriverManager.getConnection(url,user,password);
       } catch (SQLException | ClassNotFoundException e) {
           throw new RuntimeException(e);
       }
        return cnx;
    }
}

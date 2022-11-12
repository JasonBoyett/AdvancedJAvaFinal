package jaboye2448;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Inventory {
    private Product[] products;
    public Inventory(){
        this.products = inventoryFactory();
    }

    private Product[] inventoryFactory(){
        ArrayList<Product> products = new ArrayList<Product>();
        String url = "jdbc:mysql://127.0.0.1:3306/advanced_java_data";
        String user = "root";
        String password = "root@123";
        String querey = "SELECT name FROM advanced_java_data.product_info";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(querey);

            while( result.next()){
                products.add(new Product(result.toString()));
            }
            return (Product[]) products.toArray();

        } catch (SQLException|ClassNotFoundException e){
            e.printStackTrace();
            return new Product[0];
        }
    }

    public Product[] getProducts() {
        return this.products;
    }

    public Product getProducts(int index) {
        return products[index];
    }

}
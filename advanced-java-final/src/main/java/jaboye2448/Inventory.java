package jaboye2448;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

public class Inventory {
    private Product[] products;
    public Inventory(){
        this.products = inventoryFactory();
    }

    private Product[] inventoryFactory(){//builds an inventory by pulling in all elements of the connected database and building objects based on the information stored there
        ArrayList<Product> products = new ArrayList<>();
        String url = "jdbc:mysql://127.0.0.1:3306/advanced_java_data";
        String user = "root";
        String password = "root@123";
        String querey = "SELECT name FROM advanced_java_data.product_info";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(querey);

            while(result.next()){
                products.add(new Product(result.getString("name")));
            }
            Collections.shuffle(products);
            return products.toArray(new Product[products.size()]);

        } catch (SQLException|ClassNotFoundException e){
            e.printStackTrace();
            return new Product[0];
        }
    }

    public Product[] getProducts() {//return the entire inventory
        return this.products;
    }

    public Product getProducts(int index) {//gets a product from the inventory based on its index in the products array
        return products[index];
    }

    public Product[] getProducts(int start, int end){//a getter that pulls out a selected range of products from the inventory
        Product[] result = new Product[end - start];
        int index = 0;
        for(int i = start; i < end; i++){
            result[index] = products[i];
            index++;
        }
        return result;
    }

    public Product getProducts(String name){//gets a product of a given name from the inventory
        return new Product(name);
    }

    public int getAmountListed(){//returns the number of products in the inventory
        return products.length;
    }

}
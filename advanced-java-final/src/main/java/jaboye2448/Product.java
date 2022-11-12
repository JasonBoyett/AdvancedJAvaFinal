package jaboye2448;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.github.javafaker.Faker;

public class Product {

    private String productName;
    private String description;
    private int price;
    private int quantity;

    public Product(){
        Faker faker = new Faker();
        this.productName = faker.commerce().productName() + " " + faker.commerce().color() + " " + faker.number().digit();
        this.description = faker.shakespeare().hamletQuote();
        this.price = (int) faker.number().randomNumber(10, false);
        this.quantity = (int) faker.number().randomNumber(100, false);

        if(this.quantity < 0){
            this.quantity *= -1;
        }
        if(this.price < 0){
            this.price *= -1;
        }
    }

    public Product(String name){
        this.productName = name;
            String url = "jdbc:mysql://127.0.0.1:3306/advanced_java_data";
            String user = "root";
            String password = "root@123";
            StringBuilder descriptionBld = new StringBuilder("SELECT * FROM advanced_java_data.product_info ");
            descriptionBld.append(String.format("WHERE name = '%s'", this.productName));
            String descriptionQuery = descriptionBld.toString();
            System.out.println(descriptionQuery);
            try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(descriptionQuery);
            result.next();
            this.description = result.getString("description");
            this.price = result.getInt("price");


        }
        catch(SQLException sql){
            sql.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int persistInSQL(){

        try{
            String url = "jdbc:mysql://127.0.0.1:3306/advanced_java_data";
            String user = "root";
            String password = "root@123";
            StringBuilder bld = new StringBuilder("Insert into product_info(name, description, quantity, price)");
            bld.append(String.format(" values('%s', '%s', %s, %s)", this.productName, this.description, this.price, this.quantity));
            String query = bld.toString();
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);
            Statement statement = con.createStatement();
            statement.execute(query);

        }
        catch(SQLException e){
            e.printStackTrace();
            return 0;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }    
    
    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        Double result = (double) this.price;
        result /= 100;
        return String.format("$%,.2f", result);
    }

    public int getQuantity() {
        return quantity;
    }
}

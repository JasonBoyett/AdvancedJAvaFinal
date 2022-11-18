package jaboye2448;

import com.github.javafaker.Faker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class User {

    Faker faker = new Faker();
    String name;
    String billingAddress;
    String mailingAddress;
    long creditCardNumber;

    public User() {//builds a random user
        this.name = faker.name().fullName();
        this.billingAddress = faker.address().streetAddress();
        this.mailingAddress = faker.address().streetAddress();
        this.creditCardNumber = faker.number().randomNumber(16, true);

        if(this.creditCardNumber%3==0){
            this.billingAddress = this.mailingAddress;
        }
    }

    public User(String name, String billingAddress, String mailingAddress, long creditCardNumber){
        this.name = name;
        this.billingAddress = billingAddress;
        this.mailingAddress = mailingAddress;
        this.creditCardNumber = creditCardNumber;
    }
    public User(String name){
        this.name = name;
        String url = "jdbc:mysql://127.0.0.1:3306/advanced_java_data";
        String user = "root";
        String password = "root@123";
        StringBuilder descriptionBld = new StringBuilder("SELECT * FROM advanced_java_data.customer_info(name, billing_address, mailing_address, credit_card)");
        descriptionBld.append(String.format("WHERE name = '%s'", this.name));
        String descriptionQuery = descriptionBld.toString();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(descriptionQuery);
            result.next();
            this.billingAddress = result.getString("billing_address");
            this.mailingAddress = result.getString("mailing_address");
            this.creditCardNumber = Long.parseLong(result.getString("credit_card"));

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Build Failed " + e.getMessage());
        }
    }

    public int persistInSQL(){//sends the user object to the database

        try{
            String url = "jdbc:mysql://127.0.0.1:3306/advanced_java_data";
            String user = "root";
            String password = "root@123";
            StringBuilder bld = new StringBuilder("Insert into customer_info(name, mailing_address, billing_address, credit_card)");
            bld.append(String.format(" values('%s', '%s', '%s', '%s')", this.name, this.mailingAddress, this.billingAddress, String.valueOf(this.creditCardNumber)));
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
    
    @Override
    public String toString() {//returns all the user's information as a string
        StringBuilder bld = new StringBuilder("\n");
        bld.append("Name: ").append(name).append("\n");
        bld.append("Billing Address: ").append(billingAddress).append("\n");
        bld.append("Mailing Address: ").append(mailingAddress).append("\n");
        bld.append("Card Number: ").append(creditCardNumber);

        return bld.toString();
    }
    //getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public long getCreditCardNumber() {
        return this.creditCardNumber;
    }

    public void setCreditCardNumber(long creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    
}

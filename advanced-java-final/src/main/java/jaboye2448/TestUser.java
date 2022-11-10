package jaboye2448;

import com.github.javafaker.Faker;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestUser {

    Faker faker = new Faker();
    String name;
    String billingAddress;
    String mailingAddress;
    long creditCardNumber;

    public TestUser() {
        this.name = faker.name().fullName();
        this.billingAddress = faker.address().streetAddress();
        this.mailingAddress = faker.address().streetAddress();
        this.creditCardNumber = faker.number().randomNumber(16, true);

        try{
            Connection con = DriverManager.getConnection("advanced-java-data.sql");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        StringBuilder bld = new StringBuilder("\n");
        bld.append("Name: ").append(name).append("\n");
        bld.append("Billing Address: ").append(billingAddress).append("\n");
        bld.append("Main Address: ").append(mailingAddress).append("\n");
        bld.append("Card Number: ").append(creditCardNumber);

        return bld.toString();
    }

}

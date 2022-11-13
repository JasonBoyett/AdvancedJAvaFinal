package jaboye2448;

import javax.swing.JOptionPane;

import com.github.javafaker.Faker;

public class MyDB {
    public MyDB(){
        try {
            int sentProducts = 0;
            int sentUsers = 0; 
            Faker faker = new Faker();
            User primary = new User("Dr. James Moore", faker.address().streetAddress(), faker.address().streetAddress(),Long.parseLong("1234567891234567"));
            for (int i = 0; i < 1000; i++) {
                User make = new User();
                sentUsers += make.persistInSQL();
            }
            while (sentUsers < 1000) {
                for (int i = 0; i < 1000 - sentUsers; i++) {
                    User make = new User();
                    sentUsers += make.persistInSQL();
                }
            }
            for (int i = 0; i < 1000; i++) {
                Product test = new Product();
                sentProducts += test.persistInSQL();
            }
            while (sentProducts < 1000) {
                for (int i = 0; i < (1000 - sentProducts); i++) {
                    Product test = new Product();
                    sentProducts += test.persistInSQL();
                }
            }
            primary.persistInSQL();
            JOptionPane.showMessageDialog(null, "Build Complete", null, JOptionPane.PLAIN_MESSAGE);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "build failed\n" + e.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}

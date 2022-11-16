package jaboye2448;

import javax.swing.JOptionPane;

import com.github.javafaker.Faker;

public class MyDB {
    private int target = 1000;
    public static final boolean POPULATE = true;
    
    public MyDB(boolean populate) {
        if(populate){
            populate();
        }
    }

    private void populate(){
        try {
            int sentProducts = 0;
            int sentUsers = 0; 
            Faker faker = new Faker();
            User primary = new User("Dr. James Moore", faker.address().streetAddress(), faker.address().streetAddress(),Long.parseLong("1234567891234567"));
            for (int i = 0; i < target; i++) {
                User make = new User();
                sentUsers += make.persistInSQL();
            }
            while (sentUsers < target) {
                for (int i = 0; i < target - sentUsers; i++) {
                    User make = new User();
                    sentUsers += make.persistInSQL();
                }
            }
            for (int i = 0; i < target; i++) {
                Product test = new Product();
                sentProducts += test.persistInSQL();
            }
            while (sentProducts < target) {
                for (int i = 0; i < (target - sentProducts); i++) {
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

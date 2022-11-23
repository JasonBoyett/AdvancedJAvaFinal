package jaboye2448;

import java.util.ArrayList;
import javax.swing.*;

import com.github.javafaker.Faker;

import java.awt.*;
import java.time.LocalDate;

public class ConfirmOrder extends JFrame{
    Faker faker = new Faker();
    private JPanel backPanel = new JPanel(new BorderLayout(0,20));
    private JPanel mainPanel;
    private JPanel topPanel = new JPanel(new GridLayout(1,3));
    private JPanel buttonPanel = new JPanel(new GridLayout(2,2,50,10));
    private JButton confirmButton = new JButton("Confirm");
    private JButton edit = new JButton("Edit Order"); 
    private JTextArea amt = new JTextArea("Quantity");
    private JTextArea pricePer = new JTextArea("Price Per Unit");
    private JTextArea price = new JTextArea("Price");
    private JTextArea name = new JTextArea("Product Name");
    private JTextArea Taxes = new JTextArea("Taxes: ");
    private JTextArea spacer = new JTextArea();
    private String dateTime = "\nDate of Purchase:\n" + LocalDate.now();
    private String contact = "\nContact us:\n" + faker.address().streetAddress() + "\n" + faker.phoneNumber().cellPhone();

    private Double subtotal = 0.0; 
    private Double tax = 0.0;
    private Double total = 0.0;
    private Double shipping = 0.0;
    int itemsPurchased = 0;

    private ArrayList<Product> purchased = new ArrayList<Product>();
    private ArrayList<Integer> amounts;

    public ConfirmOrder(ArrayList<String> purchased, ArrayList<Integer> amounts) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        JTextArea showDate = new JTextArea(dateTime);
        prepTextArea(showDate);
        topPanel.add(showDate);

        JLabel label = new JLabel();
        label.setIcon(new ImageIcon("logo.png"));


        topPanel.add(label);

        JTextArea showContact = new JTextArea(contact);
        prepTextArea(showContact);
        topPanel.add(showContact);

        
        for(int i = 0; i < purchased.size(); i++){
            this.purchased.add(new Product(purchased.get(i)));
        }
        this.amounts = amounts;
        
        this.setTitle("Confirm Order");
        
        GridLayout mainGrid = new GridLayout(purchased.size()+4,4,10,100);
        this.mainPanel = new JPanel(mainGrid);
        
        prepTextArea(name);
        mainPanel.add(name);

        prepTextArea(amt);
        mainPanel.add(amt);

        prepTextArea(pricePer);
        mainPanel.add(pricePer);
        
        prepTextArea(price);
        mainPanel.add(price);

        for(int i = 0; i < purchased.size(); i++){
            JTextArea nm = new JTextArea(this.purchased.get(i).getProductName());
            prepTextArea(nm);
            nm.setFont(new Font(amt.getFont().getFontName(), Font.ITALIC, amt.getFont().getSize()));
            mainPanel.add(nm);

            JTextArea a = new JTextArea(this.amounts.get(i).toString());
            prepTextArea(a);
            a.setFont(new Font(amt.getFont().getFontName(),Font.BOLD,amt.getFont().getSize()));
            mainPanel.add(a);

            JTextArea pp = new JTextArea(this.purchased.get(i).getPriceAsString());
            prepTextArea(pp);
            mainPanel.add(pp);

            Double total = (this.amounts.get(i).intValue() * this.purchased.get(i).getPrice());
            subtotal += total;

            String p = String.format("$%,.2f", total);
            JTextArea pa = new JTextArea(p);
            prepTextArea(pa);
            mainPanel.add(pa);
        
        }
        
        JTextArea taxes = new JTextArea("Tax amount: ");
        prepTextArea(taxes);
        mainPanel.add(taxes);

        mainPanel.add(new JPanel());
        mainPanel.add(new JPanel());

        this.tax = subtotal * (0.08);
        JTextArea showTax = new JTextArea(String.format("$%,.2f", tax));
        prepTextArea(showTax);
        mainPanel.add(showTax);

        JTextArea subT = new JTextArea("Subtotal: ");
        prepTextArea(subT);
        mainPanel.add(subT);
        
        mainPanel.add(new JPanel());
        mainPanel.add(new JPanel());
        String show = String.format("$%,.2f", this.subtotal+tax);
        JTextArea showSubT = new JTextArea(show);
        prepTextArea(showSubT);
        mainPanel.add(showSubT);
        
        itemsPurchased = calculateItemsPurchased();
        this.shipping = itemsPurchased * 0.20;
        
        JTextArea shippingText = new JTextArea("Shipping: ");
        prepTextArea(shippingText);
        mainPanel.add(shippingText);

        mainPanel.add(new JPanel());
        mainPanel.add(new JPanel());
        String showShip = String.format("$%,.2f", this.shipping);
        JTextArea showShipping = new JTextArea(showShip);
        showShipping.setToolTipText("shipping is 20 cents per unit");
        prepTextArea(showShipping);
        mainPanel.add(showShipping);

        total = subtotal + shipping + tax;

        JTextArea gTotal = new JTextArea("Grand total: ");
        prepTextArea(gTotal);
        buttonPanel.add(gTotal);

        JTextArea showGTotal = new JTextArea(String.format("$%,.2f", total));
        prepTextArea(showGTotal);
        buttonPanel.add(showGTotal);

        edit.addActionListener(e -> pressEdit());
        buttonPanel.add(edit);
        confirmButton.addActionListener(e -> pressConfirm());
        buttonPanel.add(confirmButton);

        backPanel.setPreferredSize(new Dimension(1280, 720));
        topPanel.setPreferredSize(new Dimension(backPanel.getPreferredSize().width-20,(backPanel.getPreferredSize().height)/8));
        buttonPanel.setPreferredSize(new Dimension(backPanel.getPreferredSize().width-20, topPanel.getPreferredSize().height));

        JScrollPane scroll = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        backPanel.add(topPanel, BorderLayout.NORTH);
        backPanel.add(scroll, BorderLayout.CENTER);
        backPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.add(backPanel);
        this.pack();
    }

    private void prepTextArea(JTextArea area){
        area.setOpaque(false);
        area.setEditable(false);
        area.setBorder(null);
    }

    private int calculateItemsPurchased(){
        int result = 0;
        for(int i = 0; i < amounts.size(); i++){
            result += amounts.get(i).intValue();
        }
        return result;
    }

    public void pressConfirm(){
        System.exit(0);
    }

    public void pressEdit(){
        EditOrder e = new EditOrder(this.purchased,this.amounts);
        e.setVisible(true);
        this.dispose();
    }

}
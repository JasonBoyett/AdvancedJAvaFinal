package jaboye2448;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class EditOrder extends JFrame implements ActionListener{
    private Timer timer;
    private double total = 0.0;
    private final int MAX = 10;
    ArrayList<Incrementor> incrementors = new ArrayList<Incrementor>();
    ArrayList<Double> totals = new ArrayList<Double>();//these are the totals of each item times the number ordered
    ArrayList<Product> purchased = new ArrayList<Product>();//this is a list of the indevidual products
    ArrayList<Integer> amounts = new ArrayList<Integer>();//this represents the amount of each product being purchased in the corrisponding index of "products"
    ArrayList<JTextArea> showTotals = new ArrayList<JTextArea>();//this is the text areas where the totals will be shown and this will be updated to reflect the current number of items in "amounts"
    JTextArea showGrandTotal = preppedArea("");//this is where the grand total will be displayed
    JButton confirm = new JButton("Confirm");//this button will confirm the order and return the user to the confirm order screen
    JButton returnToShop = new JButton("Return to Shop");//this button will return the user to the original shop

    public EditOrder(ArrayList<Product> purchased, ArrayList<Integer> amounts){
        setTitle("Edit Order");
        this.setVisible(true);
        this.purchased = purchased;
        this.amounts = amounts;
        GridLayout grid = new GridLayout(purchased.size()+3,4,10,10);//the grid size will be determined based on the number of feilds that we know will will have plus the number of items in the array list being fed to the constructor
        JPanel panel = new JPanel(grid);//we will only need one primary panel for this gui
	//in this block the first four text areas are added. these say what the below areas represent
        panel.add(preppedArea("Product Name"));
        panel.add(preppedArea("Quantity"));
        panel.add(preppedArea("Price Per Unit"));
        panel.add(preppedArea("Price"));
	
	//this for loop will determine the values to be added to each text area and add them to the GUI
	//it will also add the appropriate objects to their respective ArrayLists
        for(int i = 0; i < purchased.size(); i++){
            panel.add(preppedArea(purchased.get(i).getProductName()));
            incrementors.add(new Incrementor(amounts.get(i).intValue(), MAX));
            panel.add(incrementors.get(i));
            panel.add(preppedArea(purchased.get(i).getPriceAsString()));
            totals.add(purchased.get(i).getPrice() * amounts.get(i).intValue());
            showTotals.add(preppedArea(formatTotalText(totals.get(i))));
            panel.add(showTotals.get(i));
        }

	//now we add all the remaining text areas
        panel.add(preppedArea("Total"));
        panel.add(new JPanel());
        panel.add(new JPanel());
        total = calculateTotal();
        showGrandTotal.setText(formatTotalText(total));
        panel.add(showGrandTotal);

       	//adding the main pael to the GUI 
        panel.add(new JPanel());

	//adding the buttons to the panel and giving them actions
        confirm.addActionListener(this);
        panel.add(confirm);
        returnToShop.addActionListener(this);
        panel.add(returnToShop);



	//some boiler plate stuff
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JScrollPane scroll = new JScrollPane(panel);
        scroll.setPreferredSize(new Dimension(1280,720));
        this.add(scroll);
        pack();

	//setting up the timer that will update the text fields
        timer = new Timer(10, this);
        timer.start();
    }

    //a method to properly format text feilds
    private JTextArea preppedArea(String text) {
        JTextArea area = new JTextArea(text);
        area.setOpaque(false);
        area.setEditable(false);
        area.setBorder(null);

        return area;
    }

    //a method to turn a double representing a dollar amount into a string that shows said dollar amount
    private String formatTotalText(double total){
        return String.format("$%,.2f",total);
    }

    //calculates the grand total
    private double calculateTotal(){
        double result = 0.0;
        for(int i = 0; i < totals.size(); i++){
            result += totals.get(i);
        }
        return result;
    }

    //calculates the total of a given item based on the amount selected of that item
    private double calculateTotal(int index){
        return purchased.get(index).getPrice() * incrementors.get(index).getSelected();
    }

    //this action performed method handles updating the lists and text areas and gives the buttons their functionality
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == confirm){//this runs if the "confirm" button is pressed
            ArrayList<String> list = new ArrayList<String>();
            for(int i = 0; i < purchased.size(); i++){
                list.add(purchased.get(i).getProductName());
            }
            ConfirmOrder co = new ConfirmOrder(list, this.amounts);
            this.dispose();
        }
        else if(e.getSource() == returnToShop){//this runs if the "return to shop" button is pressed
            GUI gui = new GUI();
            this.dispose();
        }
        else{//this will run when the timer is triggered
	     //loops through the array lists and uses them to set the state of the GUI and the lists
            for(int i = 0; i < totals.size(); i++){
                showTotals.get(i).setText(formatTotalText(calculateTotal(i)));
                totals.set(i, calculateTotal(i));
                amounts.set(i, incrementors.get(i).getSelected());
            }
            total = calculateTotal();
            showGrandTotal.setText(formatTotalText(total));
        }
    }
}

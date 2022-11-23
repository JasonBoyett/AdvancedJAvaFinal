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
    ArrayList<Double> totals = new ArrayList<Double>();
    ArrayList<Product> purchased = new ArrayList<Product>();
    ArrayList<Integer> amounts = new ArrayList<Integer>();
    ArrayList<JTextArea> showTotals = new ArrayList<JTextArea>();
    JTextArea showGrandTotal = preppedArea("");
    JButton confirm = new JButton("Confirm");
    JButton returnToShop = new JButton("Return to Shop");
    public EditOrder(ArrayList<Product> purchased, ArrayList<Integer> amounts){
        setTitle("Edit Order");
        this.setVisible(true);
        this.purchased = purchased;
        this.amounts = amounts;
        GridLayout grid = new GridLayout(purchased.size()+3,4,10,10);
        JPanel panel = new JPanel(grid);
        panel.add(preppedArea("Product Name"));
        panel.add(preppedArea("Quantity"));
        panel.add(preppedArea("Price Per Unit"));
        panel.add(preppedArea("Price"));

        for(int i = 0; i < purchased.size(); i++){
            panel.add(preppedArea(purchased.get(i).getProductName()));
            incrementors.add(new Incrementor(amounts.get(i).intValue(), MAX));
            panel.add(incrementors.get(i));
            panel.add(preppedArea(purchased.get(i).getPriceAsString()));
            totals.add(purchased.get(i).getPrice() * amounts.get(i).intValue());
            showTotals.add(preppedArea(formatTotalText(totals.get(i))));
            panel.add(showTotals.get(i));
        }

        panel.add(preppedArea("Total"));
        panel.add(new JPanel());
        panel.add(new JPanel());
        total = calculateTotal();
        showGrandTotal.setText(formatTotalText(total));
        panel.add(showGrandTotal);

        
        panel.add(new JPanel());

        confirm.addActionListener(this);
        panel.add(confirm);
        returnToShop.addActionListener(this);
        panel.add(returnToShop);



        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JScrollPane scroll = new JScrollPane(panel);
        scroll.setPreferredSize(new Dimension(1280,720));
        this.add(scroll);
        pack();

        timer = new Timer(10, this);
        timer.start();
    }

    private JTextArea preppedArea(String text) {
        JTextArea area = new JTextArea(text);
        area.setOpaque(false);
        area.setEditable(false);
        area.setBorder(null);

        return area;
    }

    private String formatTotalText(double total){
        return String.format("$%,.2f",total);
    }

    private double calculateTotal(){
        double result = 0.0;
        for(int i = 0; i < totals.size(); i++){
            result += totals.get(i);
        }
        return result;
    }

    private double calculateTotal(int index){
        return purchased.get(index).getPrice() * incrementors.get(index).getSelected();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == confirm){
            ArrayList<String> list = new ArrayList<String>();
            for(int i = 0; i < purchased.size(); i++){
                list.add(purchased.get(i).getProductName());
            }
            ConfirmOrder co = new ConfirmOrder(list, this.amounts);
            this.dispose();
        }
        else if(e.getSource() == returnToShop){
            GUI gui = new GUI();
            this.dispose();
        }
        else{
            for(int i = 0; i < totals.size(); i++){
                showTotals.get(i).setText(formatTotalText(calculateTotal(i)));
                totals.set(i, calculateTotal(i));
            }
            total = calculateTotal();
            showGrandTotal.setText(formatTotalText(total));
        }
    }
}

package jaboye2448;
import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;

import java.awt.*;
import java.util.ArrayList;

public class GUI extends JFrame{
    private final static int INITIAL_HEIGHT = 720;
    private final static int INITIAL_WIDTH = 1280;
    private final int SCROLL_X = 0;
    private final static int SCROLL_Y = 0;
    
    private int buttonPanelWidth;
    private int buttonPanelHeight;
    private int buttonPanelX;
    private int buttonPanelY;
    private int scrollHeight;
    private int scrollWidth;
    private int scrollX;
    private int scrollY;

    private GridLayout grid = new GridLayout(10,2,5,20);
    private JPanel scrollingPanel = new JPanel(grid);
    private JPanel buttonPanel = new JPanel();
    private JScrollPane scroll = new JScrollPane(this.scrollingPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    private JButton checkout = new JButton("Checkout");
    private JButton next = new JButton("Next");
    private JButton previous = new JButton("Previous");
    private JCheckBox[] boxes = new JCheckBox[10];
    private JComboBox<Integer>[] combos = new JComboBox[10];
    private Integer[] numbers = {0,1,2,3,4,5,6,7,8,9,10};
    private Inventory myInventory = new Inventory();
    private ArrayList<Product> selectedItems = new ArrayList<Product>();

    public GUI() {
        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        
        previous.setEnabled(false);
        buttonPanel.add(next);
        checkout.addActionListener(e -> checkout());
        buttonPanel.add(checkout);
        buttonPanel.add(previous);
        this.add(buttonPanel);

        for(int i = 0; i < boxes.length; i++) {
            boxes[i] = new JCheckBox();
            combos[i] = new JComboBox<>(numbers);
            
            boxes[i].setVisible(true);
            boxes[i].setToolTipText(setHoverText(myInventory.getProducts(i)));
            boxes[i].setText(setBoxText(myInventory.getProducts(i)));
            
            scrollingPanel.add(boxes[i]);
            scrollingPanel.add(combos[i]);
        }
        this.add(scroll);
        buttonPanel.setBackground(Color.gray);
        try{
            Thread.sleep(1);
        }
        catch(InterruptedException e){
         e.printStackTrace();   
        }
        finally{
            this.setSize(INITIAL_WIDTH,INITIAL_HEIGHT);
        }
        
    }

    private void checkout(){
        double subtotal = 0;
        ArrayList<String> purchased = new ArrayList<String>();
        StringBuilder message = new StringBuilder("Thank you for shopping!\n\n");
        for(int i = 0; i < boxes.length; i++){
            if(boxes[i].isSelected()){
                StringBuilder bld = new StringBuilder(boxes[i].getText());
                String name = bld.substring(bld.indexOf(": ")+2);
                for(int j = 0; j < combos[i].getSelectedIndex(); j++){
                    selectedItems.add(this.myInventory.getProducts(name));
                }
                
            }
        }
        if(!selectedItems.isEmpty()){
            for(int i = 0; i < selectedItems.size(); i++){
                subtotal += (selectedItems.get(i).getPrice());
                if(i>0){
                    addSingle(purchased, i);
                }
                else{
                    purchased.add(selectedItems.get(i).getProductName());
                }
            }
        }
        for(int i = 0; i < purchased.size(); i++){
            message.append(String.format("%s%n", purchased.get(i)));
        }
        message.append(String.format("Subtotal: $%,.2f", subtotal));
        JOptionPane.showMessageDialog(this, message.toString());
        this.dispose();
    }

    private void addSingle(ArrayList<String> list, int index){
        if(!selectedItems.get(index).getProductName().equals(selectedItems.get(index-1).getProductName())){
            list.add(selectedItems.get(index).getProductName());
        }
    }

    private String setBoxText(Product product) {
        StringBuilder bld  = new StringBuilder(product.getPriceAsString());
        bld.append(String.format(": %s", product.getProductName()));
        return bld.toString();
    }

    private String setHoverText(Product product){
        StringBuilder bld = new StringBuilder(product.getDescription());
        bld.append(String.format(" %,d units in stock", product.getQuantity()));
        return bld.toString();
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        
        
        scrollingPanel.setSize(this.getWidth(), (int)(this.getHeight() * 0.8));
        scroll.setBounds(SCROLL_X, SCROLL_Y, (this.getWidth()), (int)(this.getHeight() * 0.8));
        buttonPanel.setBounds(0, (int)(this.getHeight() * 0.8),this.getWidth(), (int)(this.getHeight() * 0.2));

        int buttonWidth = (buttonPanel.getWidth()/9);
        int buttonHeight = (buttonPanel.getHeight()/2);
        int buttonY = (buttonHeight/4);
        int checkoutX = (buttonPanel.getWidth()/2)-(buttonWidth/2);
        int previousX= (buttonWidth);
        int nextX = (buttonPanel.getWidth() - (buttonWidth*2));
        checkout.setBounds(checkoutX, buttonY, buttonWidth, buttonHeight);
        previous.setBounds(previousX, buttonY, buttonWidth, buttonHeight);
        next.setBounds(nextX, buttonY, buttonWidth, buttonHeight);
    }
}
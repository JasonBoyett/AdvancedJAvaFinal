package jaboye2448;
import javax.swing.*;
import java.awt.*;

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

    private JPanel scrollingPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JScrollPane scroll = new JScrollPane(this.scrollingPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    private JButton checkout = new JButton("Checkout");
    private JButton next = new JButton("Next");
    private JButton previous = new JButton("Previous");

    public GUI() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.add(scroll);
        this.add(buttonPanel);
        scroll.setBackground(Color.darkGray);
        scrollingPanel.setBackground(scroll.getBackground());
        buttonPanel.setBackground(Color.gray);
        this.setSize(INITIAL_WIDTH,INITIAL_WIDTH);
        // buttonPanel.add(next);
        buttonPanel.add(checkout);
        // buttonPanel.add(previous);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        
        scrollingPanel.setSize(this.getWidth(), (int)(this.getHeight() * 0.8));
        scroll.setBounds(0, 0, (this.getWidth()), (int)(this.getHeight() * 0.8));
        buttonPanel.setBounds(0, (int)(this.getHeight() * 0.8),this.getWidth(), (int)(this.getHeight() * 0.2));
        int buttonWidth = (buttonPanel.getWidth()/9);
        int buttonHeight = (buttonPanel.getWidth()/16);
        int checkoutX = (this.getWidth()/2)-(buttonWidth/2);
        int checkoutY = (buttonPanel.getHeight()/4);
        checkout.setBounds(checkoutX, checkoutY, buttonWidth, buttonHeight);

    }
}
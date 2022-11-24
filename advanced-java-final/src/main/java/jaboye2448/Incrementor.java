package jaboye2448;

import javax.swing.*;
import java.awt.*;

public class Incrementor extends JPanel{
   private int start;
   private int limit;
   private int selected;
   private JTextArea view;
   private GridLayout grid = new GridLayout(1,3);
   private JButton plus = new JButton("+");
   private JButton minus = new JButton("-");
   
   public Incrementor(int start, int limit){
        this.start = start;
        this.limit = limit;
        this.selected = start;
        this.view = new JTextArea(String.valueOf(selected));
        this.view.setEditable(false);
        this.view.setBorder(null);
        this.view.setOpaque(false);
        
        minus.addActionListener(e -> minus());
        this.add(minus);
        this.add(view);
        plus.addActionListener(e -> plus());
        this.add(plus);
   } 
   
   private void plus(){
        if(selected < limit){
            selected++;
        }
        else if(selected == limit){
            selected = 0;
        }
        view.setText(String.valueOf(selected));
    }

    private void minus(){
        if (selected > 0) {
            selected--;
        } else {
            selected = limit;
        }
        view.setText(String.valueOf(selected));
    }

    public int getSelected(){
        return selected;
    }
}

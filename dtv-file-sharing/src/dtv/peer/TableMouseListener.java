package dtv.peer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
 
/**
 * A mouse listener for a JTable component.
 * @author www.codejava.neet
 *
 */
public class TableMouseListener extends MouseAdapter {
     
    public TableMouseListener(JButton table) {
    }
     
    @Override
    public void mousePressed(MouseEvent event) {
        event.getPoint();
    }
}
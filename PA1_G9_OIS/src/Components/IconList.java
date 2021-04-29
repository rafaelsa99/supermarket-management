/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Components;

import java.util.ArrayList;
import javax.swing.AbstractListModel;
import javax.swing.JLabel;

/**
 *
 * @author luisc
 */
public class IconList extends AbstractListModel<JLabel>{
    
    ArrayList<JLabel> icons;
    
    public IconList() {
        this.icons = new ArrayList<JLabel>();
    }

    @Override
    public int getSize() { return this.icons.size(); }

    @Override
    public JLabel getElementAt(int arg0) {
        return this.icons.get(arg0);
    }
    
    public void addElement(javax.swing.JLabel element){
        this.icons.add(element); 
    }
    
    public void removeElement(int index){
        this.icons.remove(index);
    }

    public Object[] getIcons() {
        return icons.toArray();
    }
}

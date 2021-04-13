/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giangnt.tblStore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class cartObjUpgrade implements Serializable {
    private List<TblStoreDTO> items;
    
    public cartObjUpgrade() {
        this.items = new ArrayList<>();
    }
    
    public List<TblStoreDTO> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(List<TblStoreDTO> items) {
        this.items = items;
    }
}

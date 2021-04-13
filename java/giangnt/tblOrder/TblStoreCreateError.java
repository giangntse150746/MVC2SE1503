/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giangnt.tblOrder;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class TblStoreCreateError implements Serializable {
    //from user
    private String quantityOver;
    private String nameLengthErr;
    private String addressLengthErr;
    //from Database
    private String duplicateANY;

    public TblStoreCreateError() {
        
    }

    /**
     * @return the nameLengthErr
     */
    public String getNameLengthErr() {
        return nameLengthErr;
    }

    /**
     * @param nameLengthErr the nameLengthErr to set
     */
    public void setNameLengthErr(String nameLengthErr) {
        this.nameLengthErr = nameLengthErr;
    }

    /**
     * @return the addressLengthErr
     */
    public String getAddressLengthErr() {
        return addressLengthErr;
    }

    /**
     * @param addressLengthErr the addressLengthErr to set
     */
    public void setAddressLengthErr(String addressLengthErr) {
        this.addressLengthErr = addressLengthErr;
    }

    /**
     * @return the duplicateANY
     */
    public String getDuplicateANY() {
        return duplicateANY;
    }

    /**
     * @param duplicateANY the duplicateANY to set
     */
    public void setDuplicateANY(String duplicateANY) {
        this.duplicateANY = duplicateANY;
    }

    /**
     * @return the quantityOver
     */
    public String getQuantityOver() {
        return quantityOver;
    }

    /**
     * @param quantityOver the quantityOver to set
     */
    public void setQuantityOver(String quantityOver) {
        this.quantityOver = quantityOver;
    }

}

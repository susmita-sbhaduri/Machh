/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package org.bhaduri.machh.harvest;

import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import javax.naming.NamingException;
import org.bhaduri.machh.services.MasterDataServices;

/**
 *
 * @author sb
 */
@Named(value = "activeHarvestList")
@ViewScoped
public class ActiveHarvestList implements Serializable {

    /**
     * Creates a new instance of ActiveHarvestList
     */
    public ActiveHarvestList() {
    }

    public void fillHarvestValues() throws NamingException {
        MasterDataServices masterDataService = new MasterDataServices();
        crops = masterDataService.getCropList();
    }
}

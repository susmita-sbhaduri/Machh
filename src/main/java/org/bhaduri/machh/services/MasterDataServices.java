/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.machh.services;

import jakarta.persistence.Persistence;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.transaction.UserTransaction;
import java.util.ArrayList;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.bhaduri.machh.DA.CropDAO;



import org.bhaduri.machh.DA.UsersDAO;
import org.bhaduri.machh.DTO.CropPk;
import org.bhaduri.machh.entities.Users;
//import org.bhaduri.machh.UserAuthentication;
import org.bhaduri.machh.DTO.UsersDTO;
import org.bhaduri.machh.entities.CropPK;

public class MasterDataServices {
    private final EntityManagerFactory emf;
    private final UserTransaction utx;

    public MasterDataServices() {
        emf = Persistence.createEntityManagerFactory("org.bhaduri_Machh_jar_1.0-SNAPSHOT");
        utx = null;
    }
    public UsersDTO getUserAuthDetails(String username, String password) {
        UsersDAO useraccess = new UsersDAO(utx,emf);  
        UsersDTO userAuthDto = new UsersDTO();
        try {            
            Users userInfo = useraccess.getUserDetails(username, password);
            userAuthDto.setID(userInfo.getId());
            userAuthDto.setUsername(userInfo.getUsername());
            userAuthDto.setPassword(userInfo.getPassword());
            return userAuthDto;
        }
        catch (NoResultException e) {
            System.out.println("No user found with provided credentials.");
            userAuthDto.setID("null");
            return userAuthDto;
        }
        catch (Exception exception) {
            System.out.println(exception + " has occurred in getUserAuthDetails.");
            return null;
        }
    }
    
    public List<CropPk> getCropPkList() {
        CropDAO cropdao = new CropDAO(utx,emf);  
        List<CropPk> recordList = new ArrayList<>();
        CropPk record = new CropPk();
        try {  
            List<CropPK> croppkentities = cropdao.listCropPk();
            for (int i = 0; i < croppkentities.size(); i++) {
                record.setCropCategory(croppkentities.get(i).getCropcategory());
                record.setCropName(croppkentities.get(i).getCrop());
                recordList.add(record);
                record = new CropPk();
            }        
            return recordList;
        }
        catch (NoResultException e) {
            System.out.println("No expense categories rae found.");            
            return null;
        }
        catch (Exception exception) {
            System.out.println(exception + " has occurred in getExpenseCategoryDetails.");
            return null;
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.machh.services;

import jakarta.persistence.Persistence;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.transaction.UserTransaction;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.bhaduri.machh.DA.CropDAO;
import org.bhaduri.machh.DA.ResourceDAO;



import org.bhaduri.machh.DA.UsersDAO;
import org.bhaduri.machh.DTO.CropDTO;
import org.bhaduri.machh.DTO.CropPk;
import org.bhaduri.machh.entities.Users;
//import org.bhaduri.machh.UserAuthentication;
import org.bhaduri.machh.DTO.UsersDTO;
import org.bhaduri.machh.entities.Crop;

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
    
    public List<String> getCropCat() {
        CropDAO cropdao = new CropDAO(utx,emf);  
        List<String> recordList = new ArrayList<>();
        try {  
            List<String> cropcategories = cropdao.listCropCat();
            for (int i = 0; i < cropcategories.size(); i++) {
                recordList.add(cropcategories.get(i));
            }        
            return recordList;
        }
        catch (NoResultException e) {
            System.out.println("No crop categories are found.");            
            return null;
        }
        catch (Exception exception) {
            System.out.println(exception + " has occurred in getCropCat().");
            return null;
        }
    }
    
    public List<String> getCropNameForCat(String cropcat) {
        CropDAO cropdao = new CropDAO(utx,emf);  
        List<String> recordList = new ArrayList<>();
//        CropPk record = new CropPk();
        try {  
            List<String> cropnamesforcat = cropdao.listCropName(cropcat);
            for (int i = 0; i < cropnamesforcat.size(); i++) {
//                record.setCropCategory(croppkentities.get(i).getCropPK().getCropcategory());
//                record.setCropName(cropnamesforcat.get(i).getCropPK().getCrop());                
                recordList.add(cropnamesforcat.get(i));
//                record = new String();
            }        
            return recordList;
        }
        catch (NoResultException e) {
            System.out.println("No crop names are found for this crop category");            
            return null;
        }
        catch (Exception exception) {
            System.out.println(exception + " has occurred in getCropNameForCat.");
            return null;
        }
    }
    
    public List<CropDTO> getCropList() {
        CropDAO cropdao = new CropDAO(utx,emf);  
        List<CropDTO> recordList = new ArrayList<>();
        CropDTO record = new CropDTO();
        Date mysqlDate;
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        try {  
            List<Crop> croplist = cropdao.getCropListAll();
            for (int i = 0; i < croplist.size(); i++) {
                record.setCropCategory(croplist.get(i).getCropPK().getCropcategory());
                record.setCropName(croplist.get(i).getCropPK().getCrop());  
                record.setDetails(croplist.get(i).getDetails());
                if (croplist.get(i).getSowingdt() != null) {
                    mysqlDate = croplist.get(i).getSowingdt();                    
                    record.setSowingDate(formatter.format(mysqlDate));
                }else{
                    record.setSowingDate("");
                }
                if (croplist.get(i).getHarvestingdt() != null) {
                    mysqlDate = croplist.get(i).getHarvestingdt();
                    record.setHarvestDate(formatter.format(mysqlDate));
                }else{
                    record.setHarvestDate("");
                }                               
                recordList.add(record);
                record = new CropDTO();
            }        
            return recordList;
        }
        catch (NoResultException e) {
            System.out.println("No crops are found");            
            return null;
        }
        catch (Exception exception) {
            System.out.println(exception + " has occurred in getCropList.");
            return null;
        }
    }
    
    public CropDTO getCropsPerPk(String cropcategory, String cropname) {
        CropDAO cropdao = new CropDAO(utx,emf);         
        CropDTO record = new CropDTO();
        Date mysqlDate;
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        try {
            Crop croprec = cropdao.getCropPerPK(cropcategory, cropname);

            record.setCropCategory(croprec.getCropPK().getCropcategory());
            record.setCropName(croprec.getCropPK().getCrop());
            record.setDetails(croprec.getDetails());
            if (croprec.getSowingdt() != null) {
                mysqlDate = croprec.getSowingdt();
                record.setSowingDate(formatter.format(mysqlDate));
            } else {
                record.setSowingDate("");
            }
            if (croprec.getHarvestingdt() != null) {
                mysqlDate = croprec.getHarvestingdt();
                record.setHarvestDate(formatter.format(mysqlDate));
            } else {
                record.setHarvestDate("");
            }

            return record;
        }
        catch (NoResultException e) {
            System.out.println("No crop record is found for cropcategory, cropname");            
            return null;
        }
        catch (Exception exception) {
            System.out.println(exception + " has occurred in getCropsPerPk.");
            return null;
        }
    }
    
    public int editCrop(CropDTO cropdto) {
        CropDAO cropdao = new CropDAO(utx,emf);         
        
        Date mysqlDate;
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        try {
            CropPk croprecpk = new CropPk();
            Crop croprec = new Crop();
            croprecpk.setCropCategory(cropdto.getCropCategory());
            croprecpk.setCropName(cropdto.getCropName());
            croprec.setDetails(cropdto.getDetails());
            mysqlDate = formatter.parse(cropdto.getSowingDate());
            croprec.setSowingdt(mysqlDate);
            mysqlDate = formatter.parse(cropdto.getHarvestDate());
            croprec.setHarvestingdt(mysqlDate);
            cropdao.edit(croprec);
            return 1;
        }
        catch (NoResultException e) {
            System.out.println("No crop record is found for cropcategory, cropname");            
            return 2;
        }
        catch (Exception exception) {
            System.out.println(exception + " has occurred in getCropsPerPk.");
            return 2;
        }
    }
    
    public List<String> getResCatForCrop(String cropcat, String cropname) {
        ResourceDAO resourcedao = new ResourceDAO(utx,emf);  
        List<String> recordList = new ArrayList<>();
        try {  
            List<String> rescatforcrop = resourcedao.listResCat(cropcat, cropname);
            for (int i = 0; i < rescatforcrop.size(); i++) {              
                recordList.add(rescatforcrop.get(i));
            }        
            return recordList;
        }
        catch (NoResultException e) {
            System.out.println("No resource is found for this crop");            
            return null;
        }
        catch (Exception exception) {
            System.out.println(exception + " has occurred in getResCatForCrop.");
            return null;
        }
    }
    
    public List<String> getResDetForCrop(String cropcat, String cropname, String resourcecat) {
        ResourceDAO resourcedao = new ResourceDAO(utx,emf);  
        List<String> recordList = new ArrayList<>();
        try {  
            List<String> rescatforcrop = resourcedao.listResDet(cropcat, cropname, resourcecat);
            for (int i = 0; i < rescatforcrop.size(); i++) {              
                recordList.add(rescatforcrop.get(i));
            }        
            return recordList;
        }
        catch (NoResultException e) {
            System.out.println("No resource is found for this crop and resource category");            
            return null;
        }
        catch (Exception exception) {
            System.out.println(exception + " has occurred in getResDetForCrop.");
            return null;
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.machh.services;

import jakarta.annotation.Resource;
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
import org.bhaduri.machh.DA.HarvestDAO;
import org.bhaduri.machh.DA.ResourceDAO;
//import org.bhaduri.machh.DA.SiteCropDAO;
import org.bhaduri.machh.DA.SiteDAO;



import org.bhaduri.machh.DA.UsersDAO;
import org.bhaduri.machh.DTO.CropDTO;
import org.bhaduri.machh.DTO.CropPk;
import org.bhaduri.machh.DTO.HarvestDTO;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_DUPLICATE;

import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_NON_EXISTING;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_SEVERE;
import static org.bhaduri.machh.DTO.MachhResponseCodes.SUCCESS;
import org.bhaduri.machh.DTO.SiteCropDTO;
import org.bhaduri.machh.DTO.SiteDTO;
import org.bhaduri.machh.entities.Users;
//import org.bhaduri.machh.UserAuthentication;
import org.bhaduri.machh.DTO.UsersDTO;
import org.bhaduri.machh.JPA.exceptions.PreexistingEntityException;
import org.bhaduri.machh.entities.Crop;

import org.bhaduri.machh.entities.Harvest;
import org.bhaduri.machh.entities.Site;

public class MasterDataServices {
    private final EntityManagerFactory emf;
    private final UserTransaction utx;

    
    public MasterDataServices() throws NamingException {
        emf = Persistence.createEntityManagerFactory("org.bhaduri_Machh_jar_1.0-SNAPSHOT");
        utx = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
//        utx = null;
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
        CropDAO cropdao = new CropDAO(utx, emf);  
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
        CropDAO cropdao = new CropDAO(utx, emf);  
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
    
//    public List<CropDTO> getCropList() {
//        CropDAO cropdao = new CropDAO(utx, emf);  
//        List<CropDTO> recordList = new ArrayList<>();
//        CropDTO record = new CropDTO();
//        Date mysqlDate;
//        String pattern = "yyyy-MM-dd";
//        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
//        try {  
//            List<Crop> croplist = cropdao.getCropListAll();
//            for (int i = 0; i < croplist.size(); i++) {
//                record.setCropCategory(croplist.get(i).getCropPK().getCropcategory());
//                record.setCropName(croplist.get(i).getCropPK().getCrop());  
//                record.setDetails(croplist.get(i).getDetails());
//                if (croplist.get(i).getSowingdt() != null) {
//                    mysqlDate = croplist.get(i).getSowingdt();                    
//                    record.setSowingDate(formatter.format(mysqlDate));
//                }else{
//                    record.setSowingDate("");
//                }
//                if (croplist.get(i).getHarvestingdt() != null) {
//                    mysqlDate = croplist.get(i).getHarvestingdt();
//                    record.setHarvestDate(formatter.format(mysqlDate));
//                }else{
//                    record.setHarvestDate("");
//                }                               
//                recordList.add(record);
//                record = new CropDTO();
//            }        
//            return recordList;
//        }
//        catch (NoResultException e) {
//            System.out.println("No crops are found");            
//            return null;
//        }
//        catch (Exception exception) {
//            System.out.println(exception + " has occurred in getCropList.");
//            return null;
//        }
//    }
    
//    public CropDTO getCropsPerPk(String cropcategory, String cropname) {
//        CropDAO cropdao = new CropDAO(utx, emf);        
//        CropDTO record = new CropDTO();
//        Date mysqlDate;
//        String pattern = "yyyy-MM-dd";
//        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
//        try {
//            Crop croprec = cropdao.getCropPerPK(cropcategory, cropname);
//
//            record.setCropCategory(croprec.getCropPK().getCropcategory());
//            record.setCropName(croprec.getCropPK().getCrop());
//            record.setDetails(croprec.getDetails());
//            if (croprec.getSowingdt() != null) {
//                mysqlDate = croprec.getSowingdt();
//                record.setSowingDate(formatter.format(mysqlDate));
//            } else {
//                record.setSowingDate("");
//            }
//            if (croprec.getHarvestingdt() != null) {
//                mysqlDate = croprec.getHarvestingdt();
//                record.setHarvestDate(formatter.format(mysqlDate));
//            } else {
//                record.setHarvestDate("");
//            }
//
//            return record;
//        }
//        catch (NoResultException e) {
//            System.out.println("No crop record is found for cropcategory, cropname");            
//            return null;
//        }
//        catch (Exception exception) {
//            System.out.println(exception + " has occurred in getCropsPerPk.");
//            return null;
//        }
//    }
    
//    public int editCrop(CropDTO cropdto) {
//        CropDAO cropdao = new CropDAO(utx, emf);          
//        
//        Date mysqlDate;
//        String pattern = "yyyy-MM-dd";
//        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
//        try {
//            CropPK croprecpk = new CropPK();
//            Crop croprec = new Crop();
//            croprecpk.setCropcategory(cropdto.getCropCategory());
//            croprecpk.setCrop(cropdto.getCropName());
//            croprec.setCropPK(croprecpk);
//            croprec.setDetails(cropdto.getDetails());
//            mysqlDate = formatter.parse(cropdto.getSowingDate());
//            croprec.setSowingdt(mysqlDate);
//            mysqlDate = formatter.parse(cropdto.getHarvestDate());
//            croprec.setHarvestingdt(mysqlDate);
//            cropdao.edit(croprec);
//            return SUCCESS;
//        }
//        catch (NoResultException e) {
//            System.out.println("No crop record is found for cropcategory, cropname");            
//            return DB_NON_EXISTING;
//        }
//        catch (Exception exception) {
//            System.out.println(exception + " has occurred in editCrop.");
//            return DB_SEVERE;
//        }
//    }
    
//    public int addCrop(CropDTO cropdto) {
//        CropDAO cropdao = new CropDAO(utx, emf);         
//        
//        Date mysqlDate;
//        String pattern = "yyyy-MM-dd";
//        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
//        try {
//            CropPK croprecpk = new CropPK();
//            Crop croprec = new Crop();
//            croprecpk.setCropcategory(cropdto.getCropCategory());
//            croprecpk.setCrop(cropdto.getCropName());
//            croprec.setCropPK(croprecpk);
//            croprec.setDetails(cropdto.getDetails());
//            mysqlDate = formatter.parse(cropdto.getSowingDate());
//            croprec.setSowingdt(mysqlDate);
//            mysqlDate = formatter.parse(cropdto.getHarvestDate());
//            croprec.setHarvestingdt(mysqlDate);
//            cropdao.create(croprec);
//            return SUCCESS;
//        }
//        catch (PreexistingEntityException e) {
//            System.out.println("Record is already there for this cropcategory, cropname");            
//            return DB_DUPLICATE;
//        }
//        catch (Exception exception) {
//            System.out.println(exception + " has occurred in addCrop.");
//            return DB_SEVERE;
//        }
//    }
    
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
    
//    public int existsSiteForCrop(String cropcategory, String cropname) {
//        SiteDAO sitedao = new SiteDAO(utx, emf);
//        try {
//            int countofsiteid = sitedao.listSiteForCrop(cropcategory, cropname);
//            return countofsiteid;
//        }
//        
//        catch (Exception exception) {
//            System.out.println(exception + " has occurred in existsSiteForCrop.");
//            return DB_SEVERE;
//        }
//    }
    
    public int existsResourceForCrop(String cropcategory, String cropname) {
        ResourceDAO resourcedao = new ResourceDAO(utx, emf);
        try {
            int count = resourcedao.listResourceForCrop(cropcategory, cropname);
            return count;
        }
        
        catch (Exception exception) {
            System.out.println(exception + " has occurred in existsResourceForCrop.");
            return DB_SEVERE;
        }
    }
    
    public List<String> getSiteNames() {
        SiteDAO sitedao = new SiteDAO(utx, emf);  
        List<String> recordList = new ArrayList<>();
//        CropPk record = new CropPk();listSite
        try {  
            List<String> sitenames = sitedao.listSite();
            for (int i = 0; i < sitenames.size(); i++) {               
                recordList.add(sitenames.get(i));
            }        
            return recordList;
        }
        catch (NoResultException e) {
            System.out.println("No crop names are found for this crop category");            
            return null;
        }
        catch (Exception exception) {
            System.out.println(exception + " has occurred in getSiteNames.");
            return null;
        }
    }
    
//    public List<SiteCropDTO> getSiteCropsPerId(String siteid) {
//        SiteCropDAO sitecropdao = new SiteCropDAO(utx, emf);  
//        List<SiteCropDTO> recordList = new ArrayList<>();
//        SiteCropDTO record = new SiteCropDTO();
//        try {  
//            List<Sitecrop> sitecrops = sitecropdao.listSitCropForId(siteid);
//            for (int i = 0; i < sitecrops.size(); i++) {                 
//                record.setSiteId(sitecrops.get(i).getSitecropPK().getSiteid());
//                record.setCropName(sitecrops.get(i).getSitecropPK().getCrop());
//                record.setSowingDate(sitecrops.get(i).getSitecropPK().getSowingdt());
//                record.setHarvestDate(sitecrops.get(i).getHarvestingdt());
//                recordList.add(record);
//                record = new SiteCropDTO();
//            }        
//            return recordList;
//        }
//        catch (NoResultException e) {
//            System.out.println("No sitecrop records are found for this site");            
//            return null;
//        }
//        catch (Exception exception) {
//            System.out.println(exception + " has occurred in getSiteCropsPerId.");
//            return null;
//        }
//    }
    
      public List<HarvestDTO> getActiveHarvestList() {
        HarvestDAO harvestdao = new HarvestDAO(utx, emf);  
        List<HarvestDTO> recordList = new ArrayList<>();
        HarvestDTO record = new HarvestDTO();
        Date mysqlDate;
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        try {  
            List<Harvest> harvestlist = harvestdao.getActiveList();
            for (int i = 0; i < harvestlist.size(); i++) {
                record.setSiteName(harvestlist.get(i).getSiteid());
                record.setCropName(harvestlist.get(i).getCrop());                           
                mysqlDate = harvestlist.get(i).getSowingdt();                    
                record.setSowingDate(formatter.format(mysqlDate));
                                             
                recordList.add(record);
                record = new HarvestDTO();
            }        
            return recordList;
        }
        catch (NoResultException e) {
            System.out.println("No crops are found");            
            return null;
        }
        catch (Exception exception) {
            System.out.println(exception + " has occurred in getActiveHarvestList.");
            return null;
        }
    }
}

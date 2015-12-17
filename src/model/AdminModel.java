/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author eddy
 */
public class AdminModel {
    
    private Connection jConnection;
    public boolean IsInsert=true;
    
    public String kd_user;
    public String user_name;
    public String user_pass;
    public String bagian;
    
    public String ExceptionMessage="";
    
    public void setConnection(Connection vconn){
        jConnection = vconn;
    }
    
    public AdminModel(){
        
    }
    
    public boolean saveAdmin(){
        boolean result=false;
        String query="";
        
        if (IsInsert==true){
            query = "insert into admin set ";
        } else {
            query = "update admin set ";
        }
        
        query = query + "user_name=?, user_pass=?, bagian=? ";

        if (IsInsert==true){
            query = query +", kd_user=? ";
        } else {
            query = query +" where kd_user=? ";
        }

        try {
            PreparedStatement stmt = null;
            stmt = jConnection.prepareStatement(query);
            
            stmt.setString(1, user_name);
            stmt.setString(2, user_pass);
            stmt.setString(3, bagian);
            stmt.setString(4, kd_user);
            stmt.executeUpdate();
            result=true;
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
        
        
        return result;
    }
    
    public boolean getAdmin(String vID){
        boolean result = false;
        String query = "";
        String FieldList = " user_name, user_pass, bagian ";
        
        query = "select "+FieldList+
                "from admin "+
                "where kd_user='"+vID+"' ";
        
        try{
            PreparedStatement stmt = null;
            stmt = jConnection.prepareStatement(query);
            
            ResultSet rs = stmt.executeQuery(query);
            
            if (rs.next() == false) 
                return false;
            
            kd_user = vID;
            user_name = rs.getString(1);
            user_pass = rs.getString(2);
            bagian = rs.getString(3);
                    
            result=true;            
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
        
        return result;
    }
    
    public boolean isValidLogin(String vUserID, String vPass){
        boolean result = false;
        String query="";
        
        query = "select * from admin "+
                "where user_name='"+vUserID+"' "+
                "and user_pass='"+vPass+"' ";
                
        
        try{
            PreparedStatement stmt = null;
            stmt = jConnection.prepareStatement(query);
            
            ResultSet rs = stmt.executeQuery(query);
            
            if (rs.next() == false) 
                return false;
            
            kd_user = rs.getString(1);
            user_name = rs.getString(2);
            user_pass = rs.getString(3);
            bagian = rs.getString(4);
                    
            result=true;            
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
        return result;
    }
    
    public boolean cekRefrens(String vTable,String vID){
        boolean result=false;
        String query="";
        
        query = "select count(*) from "+vTable+
                "where kd_user='"+vID+"' ";
                
        
        try{
            PreparedStatement stmt = null;
            stmt = jConnection.prepareStatement(query);
            
            ResultSet rs = stmt.executeQuery(query);
            
            if (rs.next() == false) 
                return false;
            
            int jml = rs.getInt(1);
            
            if (jml>0)
                result=true;
            else
                result=false;
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
        return result;
    }
    
    public boolean deleteAdmin(String vID){
        boolean result=false;
        
        String query="";
        
        
        query = query + "delete from admin where kd_user='"+vID+"' ";

        try {
            PreparedStatement stmt = null;
            stmt = jConnection.prepareStatement(query);
            
            stmt.executeUpdate();
            result=true;
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
        
        
        
        return result;
    }
}

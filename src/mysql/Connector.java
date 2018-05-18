/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import logger.LoggerFile;

/**
 *
 * @author Damangrea
 */
public class Connector {
    Connection conn;

    public Connector() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://ssportal-tbs-2.packet-systems.com/ssportalv2?user=root&password=P@ssw0rd##");
        } catch (Exception ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void rebuildConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://ssportal-tbs-2.packet-systems.com/ssportalv2?user=root&password=P@ssw0rd##");
        } catch (Exception ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int addResetAccount(String username){
        if(conn!=null){
            try {
                Statement stmt=conn.createStatement();
                String sQuery="insert into need_reset(username) values('"+username+"')";
                stmt.executeUpdate(sQuery);
                stmt.close();
                return 1;
            } catch (SQLException ex) {
                Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
                return 0;
            }
        }else{
            return 0;
        }
    }
    
    public void InputDeviceToDatabase(int p_Retry,String p_GroupId,ArrayList<HashMap<String,String>> p_ArrayDevice,LoggerFile logger){
        HashMap<String,String> hmTemp;
        String key,keys,values;
        Iterator iterate;
        int iUpd,iRow=0,iSucceed=0;
        if(p_Retry<5){
            if(conn!=null){
                try {
                    conn.setAutoCommit(false);
                    Statement stmt=conn.createStatement();
                    for (int i = 0; i < p_ArrayDevice.size(); i++) {
                        iRow=i+1;
                        keys="";
                        values="";
                        hmTemp = p_ArrayDevice.get(i);
                        iterate = hmTemp.keySet().iterator();
                        while (iterate.hasNext()) {
                            key = (String) iterate.next();
                            if(keys.length()==0){
                                keys = "groups_id,"+key;
                                values = p_GroupId+",'"+hmTemp.get(key)+"'";
                            }else{
                                keys += ","+key;
                                values += ",'"+hmTemp.get(key)+"'";
                            }
                        }
                        String sQuery="insert into device("+keys+") values("+values+")";
                        try {
                            iUpd=stmt.executeUpdate(sQuery);
                            if(iUpd==1){
                                logger.writeLog("Row "+iRow+" insert Succeed");
                                iSucceed++;
                            }
                        } catch (SQLException e) {
                            logger.writeLog("Row "+iRow+" "+e.getErrorCode()+"-"+e.getSQLState()+" "+e.getLocalizedMessage());       
                        }
                    }
                    stmt.close();
                    conn.commit();
                    logger.writeLog("Succeed : "+iSucceed);
                    logger.writeLog("Failed : "+(p_ArrayDevice.size()-iSucceed));
                } catch (SQLException ex) {
                    ex.printStackTrace();
//                    Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                InputDeviceToDatabase(p_Retry+1, p_GroupId, p_ArrayDevice,logger);
            }
        } else {
            logger.writeLog("CONNECTION FAILED");
        }
    }
    
    public void InputPurchaseToDatabase(int p_Retry,ArrayList<HashMap<String,String>> p_ArrayPurchaseOrder){
        HashMap<String,String> hmTemp;
        String key,keys,values;
        Iterator iterate;
        if(p_Retry<5){
            if(conn!=null){
                try {
                    Statement stmt=conn.createStatement();
                    for (int i = 0; i < p_ArrayPurchaseOrder.size(); i++) {
                        keys="";
                        values="";
                        hmTemp = p_ArrayPurchaseOrder.get(i);
                        iterate = hmTemp.keySet().iterator();
                        while (iterate.hasNext()) {
                            key = (String) iterate.next();
                            if(keys.length()==0){
                                keys = ""+key;
                                values = "'"+hmTemp.get(key)+"'";
                            }else{
                                keys += ","+key;
                                values += ",'"+hmTemp.get(key)+"'";
                            }
                        }
                        String sQuery="insert into rebate_purchase_order("+keys+") values("+values+")";
                        System.out.println("+++++++++++++++++++++++++++++++++++");
                        System.out.println(sQuery);
                        System.out.println("===================================");
                        stmt.executeUpdate(sQuery);
                    }
                    stmt.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
                }
                InputPurchaseToDatabase(p_Retry+1, p_ArrayPurchaseOrder);
            }
        }
    }
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            Connection connect = DriverManager.getConnection("jdbc:mysql://ssportal-tbs-2.packet-systems.com/ssportalv2?user=root&password=P@ssw0rd##");
            Statement statement = connect.createStatement();
      // Result set get the result of the SQL query
            ResultSet resultSet = statement.executeQuery("select * from contact_profile");
            while (resultSet.next()) {                
                System.out.print(resultSet.getString(1));
                System.out.print("--");
                System.out.println(resultSet.getString(2));
            }
        } catch (Exception ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

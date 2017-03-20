/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opass.kiosk_teller;

/**
 *
 * @author agung
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyValues {
    
    private Properties prop;
    private FileInputStream input = null;
    
    public PropertyValues(){
        prop = new Properties();
    }
    
    public Properties getProperties() throws FileNotFoundException, IOException{
        File jarPath =new File(PropertyValues.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String propertiesPath = jarPath.getParentFile().getAbsolutePath();
        System.out.println(propertiesPath);
        input = new FileInputStream(propertiesPath+"/config.properties"); 
	prop.load(input);        
        return prop;    
    }
    
    public void closeProperties(){
        if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
			}
	}
    }
    /*private Properties prop;
    private FileInputStream fis;
    private final String propFileName = "/config.properties";
    public PropertyValues() throws FileNotFoundException, IOException {   
        File jarPath =new File(PropertyValues.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String propertiesPath=jarPath.getParentFile().getAbsolutePath();
        System.out.println(propertiesPath+propFileName);
        fis = new FileInputStream(propertiesPath+propFileName);
        prop.load(fis);
        fis.close();
      }

    public String getRedisServer() throws IOException {
        String result = "";
        result = prop.getProperty("JEDIS_SERVER");
        return result;
    }
    
    public String getTcpSocket(){   
        String result = "";
        result = prop.getProperty("SOCKET_SERVER");
        return result;
    }
     public String getTcpSocketPort(){       
        return prop.getProperty("SOCKET_SERVER_PORT");
    }*/
    
    /* public String getRedisServer() throws IOException {

        String result = "";
        Properties prop = new Properties();
        String propFileName = "/config.properties";

    try {

        File jarPath =new File(PropertyValues.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String propertiesPath=jarPath.getParentFile().getAbsolutePath();
        System.out.println(" propertiesPath-"+propertiesPath);
        prop.load(new FileInputStream(propertiesPath+propFileName));
        } catch (IOException e1) {
        }
        result = prop.getProperty("JEDIS_SERVER");
        return result;
    }
     
      public String getSocketServer() throws IOException {

        String result = "";
        Properties prop = new Properties();
        String propFileName = "/config.properties";

    try {

        File jarPath =new File(PropertyValues.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String propertiesPath=jarPath.getParentFile().getAbsolutePath();
        System.out.println(" propertiesPath-"+propertiesPath);
        prop.load(new FileInputStream(propertiesPath+propFileName));
        } catch (IOException e1) {
        }
        result = prop.getProperty("SOCKET_SERVER");
        return result;
    }
      
       public String getSocketServerPort() throws IOException {

        String result = "";
        Properties prop = new Properties();
        String propFileName = "/config.properties";

    try {

        File jarPath =new File(PropertyValues.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String propertiesPath=jarPath.getParentFile().getAbsolutePath();
        System.out.println(" propertiesPath-"+propertiesPath);
        prop.load(new FileInputStream(propertiesPath+propFileName));
        } catch (IOException e1) {
        }
        result = prop.getProperty("SOCKET_SERVER_PORT");
        return result;
    }
       
        public String getLoketName() throws IOException {

        String result = "";
        Properties prop = new Properties();
        String propFileName = "/config.properties";

    try {

        File jarPath =new File(PropertyValues.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String propertiesPath=jarPath.getParentFile().getAbsolutePath();
        System.out.println(" propertiesPath-"+propertiesPath);
        prop.load(new FileInputStream(propertiesPath+propFileName));
        } catch (IOException e1) {
        }
        result = prop.getProperty("LOKETNAME");
        return result;
    }*/
}

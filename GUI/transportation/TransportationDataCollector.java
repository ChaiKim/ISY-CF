/*
 * The MIT License
 *
 * Copyright 2019 gfoster.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package transportation;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;


public class TransportationDataCollector {
    class Car {
        int id;
        String name;
        String fuel;
        LocalDate lastRecordedDate;    
    } // end of inner class Car
    
    private static TransportationDataCollector singleInstance = null;
    Connection conn = null;
    HashMap<String, Car> carDetails;
    
    public static TransportationDataCollector getInstance() 
    { 
        if (singleInstance == null) 
            singleInstance = new TransportationDataCollector(); 
  
        return singleInstance; 
    } // end of getInstance method
    
    // Creator is private to make this a singleton class
    private TransportationDataCollector(){
        connect();
        getAllCars();
    } // end of constructor
    
    @Override
    public void finalize() throws Throwable{
        close();
        super.finalize();
    }
    
    private void getAllCars(){
        carDetails = new HashMap(); 
        // TODO vehicle list needs to come from the database
        Car car = new Car();
        car.id = 1;
        car.name = "Red Car, the fast one. With a dent on the side";
        car.fuel = "Petrol";
        car.lastRecordedDate = LocalDate.of(2019, 02, 28);
        carDetails.put(car.name, car);
        
        car = new Car();
        car.id = 2;
        car.name = "Blue Car";
        car.fuel = "Diesel";
        car.lastRecordedDate = LocalDate.of(2019, 03, 31);
        carDetails.put(car.name, car);
        
        car = new Car();
        car.id = 3;
        car.name = "Orange Car";
        car.fuel = "Diesel";
        car.lastRecordedDate = LocalDate.of(2019, 03, 31);
        carDetails.put(car.name, car);
        
        car = new Car();
        car.id = 4;
        car.name = "Green Car";
        car.fuel = "LPG";
        car.lastRecordedDate = LocalDate.of(2019, 03, 31);
        carDetails.put(car.name, car);
        
        car = new Car();
        car.id = 5;
        car.name = "Yellow Car";
        car.fuel = "Petrol";
        car.lastRecordedDate = LocalDate.of(2019, 02, 28);
        carDetails.put(car.name, car);
    } // end method getAllCars

    public ArrayList<String> getCarList(){
        ArrayList<String> cars = new ArrayList();
        Set< HashMap.Entry< String, Car> > st = carDetails.entrySet();    
  
       for (HashMap.Entry< String, Car> me:st) 
       {
           cars.add(me.getKey());
       }
       return cars;
    } // end method getCarList()
    
    public ArrayList<String> getFuelList(){
        // TODO fuel types need to come from the database
        ArrayList<String> fuels = new ArrayList();
        fuels.add("Petrol");
        fuels.add("Diesel");
        fuels.add("LPG");
        return fuels;
    } // end method getFuelList()
    
    public String getFuel(String carName){
        return carDetails.get(carName).fuel;
    } // end of method getFuel

    public LocalDate getStartDate(String carName){
        return carDetails.get(carName).lastRecordedDate;
    } // end of method getStartDate
    
    public String vehicleSummary(String carName){
        // TODO the vehicle summary needs to come from the database
        String vehicleSummary = "";
        vehicleSummary += carName + "\n\n";
        vehicleSummary += "January" + "\t" + "63 litres" + "\n";
        vehicleSummary += "February" + "\t" + "157 litres" + "\n";
        vehicleSummary += "March" + "\t" + "83 litres" + "\n";
        vehicleSummary += "\nTotal usage" + "\t" + "303 litres";
        return vehicleSummary;
    } // end of method vehicleSummary

    public String createNewVehicle(String [] values){
        for (String line : values){
            System.out.println(line);
        }
        return "Security controller not yet built, database not updated";
    } // end of method createNewVehicle
    
    private void connect() {
        try(FileInputStream f = new FileInputStream("db.properties")) {
            // load the properties file
            Properties prop = new Properties();
            prop.load(f);

            // assign db parameters
            String url       = prop.getProperty("url");
            String user      = prop.getProperty("user");
            String password  = prop.getProperty("password");
            // create a connection to the database
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connection to the database has been established.");
        } catch(SQLException | IOException e) {
           System.out.println(e.getMessage());
        }
    } // end connect method
    
    public void close(){
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }       
    } // end of method close
} // end class TransportationDataCollector

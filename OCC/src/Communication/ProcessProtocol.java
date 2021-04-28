/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Communication;

import Main.OCC;
import java.net.*;
import java.io.*;
 
public class ProcessProtocol {
 
    public String processInput(String theInput) {
        String[] aux;
        String type;//Manager = MA, Customer = CT, Cashier = CA
        String theOutput = null;
        
        if(!theInput.equals("stop")){
            type = theInput.substring(0, 2);
             System.out.println(type);
            switch(type){
                case "MA":
                    OCC.updateState("Manager", theInput.substring(3));
                    break;
                case "CT":
                    aux = theInput.substring(3).split(":");
                    OCC.updateState("Customer", aux[1], Integer.parseInt(aux[0]));
                    break;
                case "CA":
                    OCC.updateState("Cashier", theInput.substring(3));
                    break;
                default:
                    System.out.println("Unexpected Type");
                    break;
            }
        }
        return theOutput;
    }
}
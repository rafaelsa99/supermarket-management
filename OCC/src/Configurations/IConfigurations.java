/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Configurations;

/**
 *
 * @author luisc
 */
public interface IConfigurations {

    public void setTotalNumberOfCostumers(int totalNumberOfCostumers);

    public void setMovementTimeOut(int movementTimeOut);
    
    public void setOperatingMode(boolean isAuto);
    
    public void setOperatingTimeOut(int timeOut);
    
    public void setTimeToPay(int timeOut);
}

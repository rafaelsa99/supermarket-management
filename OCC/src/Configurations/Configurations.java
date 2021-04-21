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
public class Configurations implements IConfigurations {

    final static Integer[] movementTimeOutPossibilities = {0, 100, 250, 500, 1000};
    final static Integer[] operatingTimeOutPossibilities = {0, 100, 250, 500, 1000, 2500, 5000};

    //TimeOuts defined in ms
    private int totalNumberOfCostumers;
    private int movementTimeOut;
    private boolean operatingMode;
    private int operatingTimeOut;
    private int timeToPay;

    public Configurations() {
        this.totalNumberOfCostumers = 10;
        this.movementTimeOut = 100;
        this.operatingMode = true;
        this.operatingTimeOut = 100;
        this.timeToPay = 100;
    }

    public void setTotalNumberOfCostumers(int totalNumberOfCostumers) {
        this.totalNumberOfCostumers = totalNumberOfCostumers;
    }

    public void setMovementTimeOut(int movementTimeOut) {
        this.movementTimeOut = movementTimeOut;
    }

    //Operating Mode Default True
    public void setOperatingMode(boolean operatingMode) {
        this.operatingMode = operatingMode;
    }

    public void setOperatingTimeOut(int operatingTimeOut) {
        this.operatingTimeOut = operatingTimeOut;
    }

    public void setTimeToPay(int timeToPay) {
        this.timeToPay = timeToPay;
    }

    public static Integer[] getMovementTimeOutPossibilities() {
        return movementTimeOutPossibilities;
    }

    public static Integer[] getOperatingTimeOutPossibilities() {
        return operatingTimeOutPossibilities;
    }

    //Ex: NC:10|CT:100|PT:0|OM:true|OT:0
    public String getConfigurations(){
        return new String("NC:" + this.totalNumberOfCostumers + "|CT:" + this.movementTimeOut + "|PT:" + this.timeToPay + "|OM:" + Boolean.toString(this.operatingMode) + "|OT:" + this.operatingTimeOut);
    }

    public int getTotalNumberOfCostumers() {
        return totalNumberOfCostumers;
    }

    //Ex: OM:true|OT:0
    public String getOperatingMode() {
        return new String("OM:" + Boolean.toString(this.operatingMode) + "|OT:" + this.operatingTimeOut);
    }
       
}

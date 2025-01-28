package demo.demogia.model;

import java.util.Date;

public class Request {

    private int requestID;
    private Date startDate;
    private String orgTechType;
    private String orgTechModel;
    private String problemDescryption;
    private String requestStatus;
    private Date completionDate;
    private String repairParts;
    private int masterID;
    private int clientID;

    public Request() {}

    public Request(String orgTechType, String orgTechModel, String problemDescription, int masterID, int clientID) {
        this.startDate = new Date();
        this.orgTechType = orgTechType;
        this.orgTechModel = orgTechModel;
        this.problemDescryption = problemDescription;
        this.requestStatus = "Создана";
        this.masterID = masterID;
        this.clientID = clientID;
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getOrgTechType() {
        return orgTechType;
    }

    public void setOrgTechType(String orgTechType) {
        this.orgTechType = orgTechType;
    }

    public String getOrgTechModel() {
        return orgTechModel;
    }

    public void setOrgTechModel(String orgTechModel) {
        this.orgTechModel = orgTechModel;
    }

    public String getProblemDescryption() {
        return problemDescryption;
    }

    public void setProblemDescryption(String problemDescryption) {
        this.problemDescryption = problemDescryption;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public String getRepairParts() {
        return repairParts;
    }

    public void setRepairParts(String repairParts) {
        this.repairParts = repairParts;
    }

    public int getMasterID() {
        return masterID;
    }

    public void setMasterID(int masterID) {
        this.masterID = masterID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestID=" + requestID +
                ", startDate=" + startDate +
                ", orgTechType='" + orgTechType + '\'' +
                ", orgTechModel='" + orgTechModel + '\'' +
                ", problemDescryption='" + problemDescryption + '\'' +
                ", requestStatus='" + requestStatus + '\'' +
                ", completionDate=" + completionDate +
                ", repairParts='" + repairParts + '\'' +
                ", masterID=" + masterID +
                ", clientID=" + clientID +
                '}';
    }
}

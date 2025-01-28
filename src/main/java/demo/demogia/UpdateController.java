package demo.demogia;

import demo.demogia.dao.RequestDAO;
import demo.demogia.model.Request;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.Date;

public class UpdateController {
    @FXML
    private Label lableID;
    @FXML
    private TextField inputReqestId;
    @FXML
    private TextField inputStartDate;
    @FXML
    private TextField inputOrgTechType;
    @FXML
    private TextField inputOrgTechModel;
    @FXML
    private TextField inputProblemDescryption;
    @FXML
    private TextField inputRequestStatus;
    @FXML
    private TextField inputCompletionDate;
    @FXML
    private TextField inputRepairParts;
    @FXML
    private TextField inputMasterID;
    @FXML
    private TextField inputClientID;

    public void setRequestData(Request requestData) {
        inputReqestId.setText(String.valueOf(requestData.getRequestID()));
        inputStartDate.setText(requestData.getStartDate().toString());
        inputOrgTechType.setText(requestData.getOrgTechType());
        inputOrgTechModel.setText(requestData.getOrgTechModel());
        inputProblemDescryption.setText(requestData.getProblemDescryption());
        inputRequestStatus.setText(requestData.getRequestStatus());
        inputCompletionDate.setText(requestData.getCompletionDate() != null ? requestData.getCompletionDate().toString() : "");
        inputRepairParts.setText(requestData.getRepairParts());
        inputMasterID.setText(String.valueOf(requestData.getMasterID()));
        inputClientID.setText(String.valueOf(requestData.getClientID()));
    }

    @FXML
    protected void onUpdateButtonClick() throws SQLException {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.connect();

        lableID.setText("Кнопка принята");

        Request updateRequestData = new Request();
        updateRequestData.setRequestID(Integer.parseInt(inputReqestId.getText()));
        updateRequestData.setStartDate(new Date(inputStartDate.getText()));
        updateRequestData.setOrgTechType(inputOrgTechType.getText());
        updateRequestData.setOrgTechModel(inputOrgTechModel.getText());
        updateRequestData.setProblemDescryption(inputProblemDescryption.getText());
        updateRequestData.setRequestStatus(inputRequestStatus.getText());
        updateRequestData.setCompletionDate(new Date(inputCompletionDate.getText()));
        updateRequestData.setRepairParts(inputRepairParts.getText());
        updateRequestData.setMasterID(Integer.parseInt(inputMasterID.getText()));
        updateRequestData.setClientID(Integer.parseInt(inputClientID.getText()));

        RequestDAO requestDAO = new RequestDAO(databaseConnection.getConnection());
        requestDAO.updateRequest(updateRequestData);
    }
}

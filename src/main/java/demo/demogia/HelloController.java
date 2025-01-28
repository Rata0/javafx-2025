package demo.demogia;

import demo.demogia.dao.RequestDAO;
import demo.demogia.model.Request;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class HelloController {

    @FXML
    private Label welcomeText;
    @FXML
    private TableView<Request> tView;
    @FXML
    private TableColumn<Request, Integer> reqId;
    @FXML
    private TableColumn<Request, Date> startDate;
    @FXML
    private TableColumn<Request, String> orgType;
    @FXML
    private TableColumn<Request, String> orgModel;
    @FXML
    private TableColumn<Request, String> problemDesc;
    @FXML
    private TableColumn<Request, String> reqStatus;
    @FXML
    private TableColumn<Request, Date> compDate;
    @FXML
    private TableColumn<Request, String> repaierParts;
    @FXML
    private TableColumn<Request, Integer> masterId;
    @FXML
    private TableColumn<Request, Integer> clientId;

    private final ObservableList<Request> observableList = FXCollections.observableArrayList();

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    public void initialize() throws SQLException {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.connect();

        reqId.setCellValueFactory(new PropertyValueFactory<>("requestID"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        orgType.setCellValueFactory(new PropertyValueFactory<>("orgTechType"));
        orgModel.setCellValueFactory(new PropertyValueFactory<>("orgTechModel"));
        problemDesc.setCellValueFactory(new PropertyValueFactory<>("problemDescryption"));
        reqStatus.setCellValueFactory(new PropertyValueFactory<>("requestStatus"));
        compDate.setCellValueFactory(new PropertyValueFactory<>("completionDate"));
        repaierParts.setCellValueFactory(new PropertyValueFactory<>("repairParts"));
        masterId.setCellValueFactory(new PropertyValueFactory<>("masterID"));
        clientId.setCellValueFactory(new PropertyValueFactory<>("clientID"));

        List<Request> requests = new RequestDAO(databaseConnection.getConnection()).getAllRequest();
        observableList.addAll(requests);

        tView.setItems(observableList);

        tView.setRowFactory(tv -> {
            TableRow<Request> requestTableRow = new TableRow<>();
            requestTableRow.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton() == MouseButton.PRIMARY) {
                    Request requestData = requestTableRow.getItem();
                    Stage stage = new Stage();
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("update-view.fxml"));

                    try {
                        Scene scene = new Scene(fxmlLoader.load(), 950, 800);
                        stage.setTitle("Update Request");
                        UpdateController controller = fxmlLoader.getController();
                        controller.setRequestData(requestData);

                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            return requestTableRow;
        });
    }
}
package demo.demogia;

import demo.demogia.dao.RequestDAO;
import demo.demogia.model.Request;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

public class HelloApplication extends Application {
    private DatabaseConnection databaseConnection;

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        databaseConnection = new DatabaseConnection();

        databaseConnection.connect();

        initTables();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 950, 800);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void initTables() {
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users ("
                + "userID SERIAL PRIMARY KEY,"
                + "fio VARCHAR(255) NOT NULL,"
                + "phone VARCHAR(225),"
                + "login VARCHAR(225) NOT NULL,"
                + "password VARCHAR(255) NOT NULL,"
                + "type VARCHAR(20) NOT NULL);";

        String createRequestsTable = "CREATE TABLE IF NOT EXISTS requests ("
                + "requestID SERIAL PRIMARY KEY,"
                + "startDate DATE NOT NULL,"
                + "orgTechType VARCHAR(255) NOT NULL,"
                + "orgTechModel VARCHAR(255) NOT NULL,"
                + "problemDescryption TEXT NOT NULL,"
                + "requestStatus VARCHAR(255) NOT NULL,"
                + "completionDate DATE,"
                + "repairParts TEXT,"
                + "masterID INTEGER,"
                + "clientID INTEGER"
                + ");";

        String createCommentsTable = "CREATE TABLE IF NOT EXISTS Comments ("
                + "commentID SERIAL PRIMARY KEY,"
                + "message TEXT NOT NULL,"
                + "masterID INTEGER REFERENCES Users(userID),"
                + "requestID INTEGER REFERENCES Requests(requestID)"
                + ");";

        String insertRequests = "INSERT INTO requests (startDate, orgTechType, orgTechModel, problemDescryption, requestStatus, completionDate, repairParts, masterID, clientID) VALUES "
                + "('2023-06-06', 'Компьютер', 'DEXP Aquilon O286', 'Перестал работать', 'В процессе ремонта', NULL, NULL, 2, 7), "
                + "('2023-05-05', 'Компьютер', 'DEXP Atlas H388', 'Перестал работать', 'В процессе ремонта', NULL, NULL, 3, 8), "
                + "('2022-07-07', 'Ноутбук', 'MSI GF76 Katana 11UC-879XRU черный', 'Выключается', 'Готова к выдаче', '2023-01-01', NULL, 3, 9), "
                + "('2023-08-02', 'Ноутбук', 'MSI Modern 15 B12M-211RU черный', 'Выключается', 'Новая заявка', NULL, NULL, NULL, 8), "
                + "('2023-08-02', 'Принтер', 'HP LaserJet Pro M404dn', 'Перестала включаться', 'Новая заявка', NULL, NULL, NULL, 9);";

        try (Statement statement = databaseConnection.getConnection().createStatement()) {
            statement.execute(createUsersTable);
            System.out.println("Таблица users создана");

            statement.execute(createRequestsTable);
            System.out.println("Таблица requests создана");

            statement.execute(createCommentsTable);
            System.out.println("Таблица comments создана");

            statement.execute(insertRequests);
            System.out.println("Данные вставлены");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
package demo.demogia;

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
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
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
                + "masterID INTEGER REFERENCES users(userID),"
                + "clientID INTEGER REFERENCES users(userID)"
                + ");";

        String createCommentsTable = "CREATE TABLE IF NOT EXISTS Comments ("
                + "commentID SERIAL PRIMARY KEY,"
                + "message TEXT NOT NULL,"
                + "masterID INTEGER REFERENCES Users(userID),"
                + "requestID INTEGER REFERENCES Requests(requestID)"
                + ");";

        try (Statement statement = databaseConnection.getConnection().createStatement()) {
            statement.execute(createUsersTable);
            System.out.println("Таблица users создана");

            statement.execute(createRequestsTable);
            System.out.println("Таблица requests создана");

            statement.execute(createCommentsTable);
            System.out.println("Таблица comments создана");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
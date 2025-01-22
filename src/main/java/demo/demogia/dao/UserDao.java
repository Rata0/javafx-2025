package demo.demogia.dao;

import demo.demogia.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao {
    private Connection connection = null;
    private static final String INSERT = "INSERT INTO users (fio, phone, login, password, type) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT = "SELECT * FROM users";
    private static final String FIND_BY_ID = "SELECT * FROM users WHERE userID = ?";
    private static final String UPDATE = "UPDATE users SET fio=?, phone=?, login=?, password=?, type=? WHERE userID=?";

    public UserDao(Connection conn) {
        connection = conn;
    }

    public void createUser(User user) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, user.getFio());
            preparedStatement.setString(2, user.getPhone());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getType());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setUserID(generatedKeys.getInt(1));
            } else {
                throw new SQLException("DB не вернули идентификатор после сохранения сущности");
            }
        }
    }

    public List<User> getAllUsers() throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> result = new ArrayList<User>();
            while (resultSet.next()) {
                int id = resultSet.getInt("userID");
                String fio = resultSet.getString("fio");
                String phone = resultSet.getString("phone");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                String type = resultSet.getString("type");
                User user = new User(fio, phone, login, password, type);
                user.setUserID(id);
                result.add(user);
            }
            return result;
        }
    }

    public Optional<User> getUserById(int id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String fio = resultSet.getString("fio");
                String phone = resultSet.getString("phone");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                String type = resultSet.getString("type");
                User user = new User(fio, phone, login, password, type);
                user.setUserID(id);

                return Optional.of(user);
            }
            return Optional.empty();
        }
    }

    public void updateUser(User user) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, user.getFio());
            preparedStatement.setString(2, user.getPhone());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getType());
            preparedStatement.setInt(6, user.getUserID());

            int countUpdate = preparedStatement.executeUpdate();
            if (countUpdate == 0) {
                throw new SQLException("Пользовательс с id " + user.getUserID() + " не выполнилось");
            }
        } catch (SQLException e) {
            throw new SQLException("Ошибка при обновление сущности: " + e.getMessage());
        }
    }
}

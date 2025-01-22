package demo.demogia.dao;

import demo.demogia.model.Comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommentDAO {
    private Connection connection = null;
    private static final String INSERT = "INSERT INTO comments (message, masterID, requestID) VALUES (?, ?, ?)";
    private static final String SELECT = "SELECT * FROM comments";
    private static final String FIND_BY_ID = "SELECT * FROM comments WHERE commentID = ?";
    private static final String UPDATE = "UPDATE comments SET message=?, masterID=?, requestID=? WHERE commentID=?";

    public CommentDAO(Connection conn) {
        connection = conn;
    }

    public void createComment(Comment comment) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)){

            preparedStatement.setString(1, comment.getMessage());
            preparedStatement.setInt(2, comment.getMasterID());
            preparedStatement.setInt(3, comment.getRequestID());

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                comment.setCommentID(generatedKeys.getInt(1));
            } else {
                throw new SQLException("DB не вернули идентификатор после сохранения сущности коммент");
            }
        }
    }

    public List<Comment> getAllComments() throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Comment> result = new ArrayList<Comment>();
            while (resultSet.next()) {
                int id = resultSet.getInt("commentID");
                String message = resultSet.getString("message");
                int masterID = resultSet.getInt("masterID");
                int requestID = resultSet.getInt("requestID");
                Comment comment = new Comment(message, masterID, requestID);
                comment.setCommentID(id);
                result.add(comment);
            }
            return result;
        }
    }

    public Optional<Comment> getCommentById(int id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String message = resultSet.getString("message");
                int masterID = resultSet.getInt("masterID");
                int requestID = resultSet.getInt("requestID");
                Comment comment = new Comment(message, masterID, requestID);
                comment.setCommentID(id);

                return Optional.of(comment);
            }
            return Optional.empty();
        }
    }

    public void updateComment(Comment comment) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, comment.getMessage());
            preparedStatement.setInt(2, comment.getMasterID());
            preparedStatement.setInt(3, comment.getRequestID());
            preparedStatement.setInt(4, comment.getCommentID());

            int countUpdate = preparedStatement.executeUpdate();
            if (countUpdate == 0) {
                throw new SQLException("Комментарий с id " + comment.getCommentID() + " не выполнилось");
            }
        } catch (SQLException e) {
            throw new SQLException("Ошибка при обновление сущности коммент: " + e.getMessage());
        }
    }
}

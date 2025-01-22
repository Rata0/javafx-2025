package demo.demogia.dao;

import demo.demogia.model.Request;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class RequestDAO {
    private Connection connection = null;
    private static final String INSERT = "INSERT INTO requests (startDate, orgTechType, orgTechModel, problemDescryption, requestStatus, completionDate, repairParts, masterID, clientID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT = "SELECT * FROM requests";
    private static final String FIND_BY_ID = "SELECT * FROM requests WHERE requestID = ?";
    private static final String UPDATE =  "UPDATE requests SET startDate=?, orgTechType=?, orgTechModel=?, problemDescryption=?, requestStatus=?, completionDate=?, repairParts=?, masterID=?, clientID=? WHERE requestID=?";

    public RequestDAO(Connection conn) {
        connection = conn;
    }

    public void createRequest(Request request) throws SecurityException, SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setDate(1, new java.sql.Date(request.getStartDate().getTime()));
            preparedStatement.setString(2, request.getOrgTechType());
            preparedStatement.setString(3, request.getOrgTechModel());
            preparedStatement.setString(4, request.getProblemDescription());
            preparedStatement.setString(5, request.getRequestStatus());

            if (request.getCompletionDate() != null) {
                preparedStatement.setDate(6, new java.sql.Date(request.getCompletionDate().getTime()));
            } else {
                preparedStatement.setNull(6, java.sql.Types.DATE);
            }

            preparedStatement.setString(7, request.getRepairParts());
            preparedStatement.setInt(8, request.getMasterID());
            preparedStatement.setInt(9, request.getClientID());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                request.setRequestID(generatedKeys.getInt(1));
            } else {
                throw new SQLException("DB не вернули идентификатор после сохранения сущности запрос");
            }
        }
    }

    public List<Request> getAllRequest() throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Request> result = new ArrayList<Request>();
            while (resultSet.next()) {
                int id = resultSet.getInt("requestID");
                String orgTechType = resultSet.getString("orgTechType");
                String orgTechModel = resultSet.getString("orgTechModel");
                String problemDescription = resultSet.getString("problemDescryption");
                String requestStatus = resultSet.getString("requestStatus");
                Date completionDate = resultSet.getDate("completionDate");
                String repairParts = resultSet.getString("repairParts");
                int masterID = resultSet.getInt("masterID");
                int clientID = resultSet.getInt("clientID");

                Request request = new Request(orgTechType, orgTechModel, problemDescription, masterID, clientID);

                request.setRequestID(id);
                request.setCompletionDate(completionDate);
                request.setRepairParts(repairParts);
                request.setRequestStatus(requestStatus);

                result.add(request);
            }
            return result;
        }
    }

    public Optional<Request> getRequestById(int id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String orgTechType = resultSet.getString("orgTechType");
                String orgTechModel = resultSet.getString("orgTechModel");
                String problemDescription = resultSet.getString("problemDescryption");
                String requestStatus = resultSet.getString("requestStatus");
                Date completionDate = resultSet.getDate("completionDate");
                String repairParts = resultSet.getString("repairParts");
                int masterID = resultSet.getInt("masterID");
                int clientID = resultSet.getInt("clientID");

                Request request = new Request(orgTechType, orgTechModel, problemDescription, masterID, clientID);
                request.setRequestID(id);
                request.setCompletionDate(completionDate);
                request.setRepairParts(repairParts);
                request.setRequestStatus(requestStatus);

                return Optional.of(request);
            }
            return Optional.empty();
        }
    }

    public void updateRequest(Request request) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setDate(1, new java.sql.Date(request.getStartDate().getTime()));
            preparedStatement.setString(2, request.getOrgTechType());
            preparedStatement.setString(3, request.getOrgTechModel());
            preparedStatement.setString(4, request.getProblemDescription());
            preparedStatement.setString(5, request.getRequestStatus());

            if (request.getCompletionDate() != null) {
                preparedStatement.setDate(6, new java.sql.Date(request.getCompletionDate().getTime()));
            } else {
                preparedStatement.setNull(6, java.sql.Types.DATE);
            }

            preparedStatement.setString(7, request.getRepairParts());
            preparedStatement.setInt(8, request.getMasterID());
            preparedStatement.setInt(9, request.getClientID());
            preparedStatement.setInt(10, request.getRequestID());

            int countUpdate = preparedStatement.executeUpdate();
            if (countUpdate == 0) {
                throw new SQLException("Запро с id " + request.getRequestID() + " не выполнилось");
            }
        } catch (SQLException e) {
            throw new SQLException("Ошибка при обновление сущности запрос: " + e.getMessage());
        }
    }
}

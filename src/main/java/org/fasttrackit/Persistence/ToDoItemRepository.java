package org.fasttrackit.Persistence;

import org.fasttrackit.domain.ToDoItem;
import org.fasttrackit.transfer.SaveToDoItemRequest;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ToDoItemRepository {
    public void createToDoItem(SaveToDoItemRequest request) throws SQLException, IOException, ClassNotFoundException {
        try (Connection connection = DatabaseConfiguration.getConnection()) {

            //FOLOSIM CARCATERUL "?" PENTRU COMBATEREA SQL INJECTION si astfel parametrii se introduc cu PreparedStatement
            String insertSql = "INSERT INTO to_do_items (`deadline`,`description`,`done`) VALUES (?,?,?);";

            //prepared statement cand dam parametrii din exterior
            //prepared statement ne protejeaza de sql rejection (la values avem parametrii din exterior)
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql);

            //la prepared statement numaratoarea parametrilor incepe de la 1
            preparedStatement.setString(1, request.getDescription());
            preparedStatement.setDate(2, request.getDeadline());
            preparedStatement.setBoolean(3, request.isDone());

            //pt modificari in BD cu executeUpdate
            preparedStatement.executeUpdate();
        }
    }

    public List<ToDoItem> getToDoItems() throws SQLException, IOException, ClassNotFoundException {
        try (Connection connection = DatabaseConfiguration.getConnection()) {
            String query = "SELECT id,deadline,description,done FROM to_do_items ORDER BY deadline DESC";

            Statement statement = connection.createStatement();
            statement.execute(query);

            //se returneaza rezultate cu execute query
            ResultSet resultSet = statement.executeQuery(query);

            List<ToDoItem> response = new ArrayList<>();

            while (resultSet.next()) {
                ToDoItem item = new ToDoItem();
                item.setId(resultSet.getLong("id"));
                item.setDescription(resultSet.getString("description"));
                item.setDeadline(resultSet.getDate("deadline"));
                item.setDone(resultSet.getBoolean("done"));

                response.add(item);
            }

            return response;
        }
    }
}


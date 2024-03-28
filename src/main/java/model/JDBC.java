package model;

import java.sql.*;
public class JDBC {
    private static void createTable(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                    create table Students(
                    id int,
                    first_name varchar(256),
                    second_name varchar(256),
                    age int
                    )
                    """);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void saveOrUpdateObject(int id, String name, String lastName, int age) {
        String url = "jdbc:mysql://localhost:3306/myschema";
        String user = "root";
        String password = "Digni330437";
        String insertQuery = "INSERT INTO students (id, first_name, second_name, age) VALUES (?, ?, ?, ?)";
        String updateQuery = "UPDATE students SET first_name = ?, second_name = ?, age = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            int resultId = getId(connection, id);
            if (resultId == -1) {
                insertInto(connection, insertQuery, id, name, lastName, age);
            } else update(connection, updateQuery, name, lastName, age, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void insertInto(Connection connection, String query, int id, String name, String secondName, int age) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.setString(2, name);
            statement.setString(3, secondName);
            statement.setInt(4, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void update(Connection connection, String query, String name, String secondName, int age, int id) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, secondName);
            statement.setInt(3, age);
            statement.setInt(4, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public String simpleSelect() {
        String url = "jdbc:mysql://localhost:3306/myschema";
        String user = "root";
        String password = "Digni330437";
        StringBuilder sb = new StringBuilder();
        try (Connection connection = DriverManager.getConnection(url, user, password); Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("""
                    SELECT * FROM Students
                      """);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("first_name");
                String secondName = resultSet.getString("second_name");
                int age = resultSet.getInt("age");
                sb.append(String.format("Id: %d, Name: %s, Second_Name: %s, Age: %d", id, name, secondName, age));
                sb.append("\n");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    private static int getId(Connection connection, int id) {
        String query = "SELECT id FROM Students WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            } else {
                return -1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

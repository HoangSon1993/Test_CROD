/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sopvn.test_crod.dbconnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author sonly
 */
public class ModelRepository {
    private String url = "jdbc:sqlserver://103.116.8.55:1433; databaseName = java; encrypt=true; trustServerCertificate=true";
    private String username = "java";
    private String password = "1";
    private Connection conn;

    public ModelRepository() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return conn;
    }

    public ObservableList<Model> findAll() {
        ObservableList<Model> ls = FXCollections.observableArrayList();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM book");
            while (rs.next()) {
                Model item = new Model();
                item.setBook_id(rs.getString("book_id"));
                item.setBook_name(rs.getString("book_name"));
                item.setDescription(rs.getString("description"));
                item.setPrice(rs.getInt("price"));
                item.setImg(rs.getString("img"));
                item.setPub_id(rs.getString("pub_id"));
                item.setCat_id(rs.getString("cat_id"));
                ls.add(item);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ls;
    }

    public static void addBook(Connection conn, Model model) throws SQLException {
        String insertQuery = "INSERT INTO book (book_id, book_name, description, price,img, pub_id, cat_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, model.getBook_id());
            preparedStatement.setString(2, model.getBook_name());
            preparedStatement.setString(3, model.getDescription());
            preparedStatement.setInt(4, model.getPrice());
            preparedStatement.setString(5, model.getImg());
            preparedStatement.setString(6, model.getPub_id());
            preparedStatement.setString(7, model.getCat_id());
            preparedStatement.executeUpdate();
        }
    }

    public static void updateBook(Connection conn, Model model) throws SQLException {
        String insertQuery = "UPDATE book set book_name=?, description=?, price=?,img=?,pub_id=?,cat_id=? where book_id=?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, model.getBook_name());
            preparedStatement.setString(2, model.getDescription());
            preparedStatement.setInt(3, model.getPrice());
            preparedStatement.setString(4, model.getImg());
            preparedStatement.setString(5, model.getPub_id());
            preparedStatement.setString(6, model.getCat_id());
            preparedStatement.setString(7, model.getBook_id());
            preparedStatement.executeUpdate();

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Book updated successfully.");
            } else {
                System.out.println("No books were updated.");
            }
        }
    }

    public static boolean deleteBook(Connection conn, String selectedBook_id) {
        String query = "DELETE FROM book WHERE book_id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, selectedBook_id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Data deleted successfully");
                return true;
            } else {
                System.out.println("No data deleted.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Model findByBookId(String bookId) {
        String query = "SELECT * FROM book WHERE book_id = ?";
        Model model = null;
        try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                model = new Model();
                model.setBook_id(resultSet.getString("book_id"));
                model.setBook_name(resultSet.getString("book_name"));
                model.setDescription(resultSet.getString("description"));
                model.setPrice(resultSet.getInt("price"));
                model.setImg(resultSet.getString("img"));
                model.setPub_id(resultSet.getString("pub_id"));
                model.setCat_id(resultSet.getString("cat_id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return model;
    }
}

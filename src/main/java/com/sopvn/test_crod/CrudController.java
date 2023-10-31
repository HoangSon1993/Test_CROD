/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.sopvn.test_crod;

import com.sopvn.test_crod.dbconnect.Model;
import com.sopvn.test_crod.dbconnect.ModelRepository;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author sonly
 */
public class CrudController implements Initializable {
    public static Model modelToUpDate = null;

    public static String selectedBook_id = "";
    public static boolean loading = true;
    private Connection connection;
    private static ObservableList<Model> ls;

    @FXML
    private TableView<Model> table;
    @FXML
    private TextField book_id1;
    @FXML
    private TextField book_name1;
    @FXML
    private TextArea description1;
    @FXML
    private TextField price1;
    @FXML
    private TextField pub_id1;
    @FXML
    private TextField cat_id1;
    @FXML
    private TextField img1;
    @FXML
    private Button add;
    @FXML
    private Button add1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if (loading) {
            ModelRepository mcr = new ModelRepository();
            ls = mcr.findAll();

            ObservableList<TableColumn<Model, ?>> cols = table.getColumns();
            cols.get(0).setCellValueFactory(new PropertyValueFactory("book_id"));
            cols.get(1).setCellValueFactory(new PropertyValueFactory("book_name"));
            cols.get(2).setCellValueFactory(new PropertyValueFactory("description"));
            cols.get(3).setCellValueFactory(new PropertyValueFactory("price"));
            cols.get(4).setCellValueFactory(new PropertyValueFactory("img"));
            cols.get(5).setCellValueFactory(new PropertyValueFactory("pub_id"));
            cols.get(6).setCellValueFactory(new PropertyValueFactory("cat_id"));
            table.setItems(ls);
            System.out.println("load 1: load all");
        } else {
//            ModelRepository mcr = new ModelRepository();
            
            for(Model model : ls){
                if(model.getBook_id().equals(selectedBook_id) ){           
                    model.setBook_name(modelToUpDate.getBook_name());
                    model.setDescription(modelToUpDate.getDescription());
                    model.setPrice(modelToUpDate.getPrice());
                    model.setImg(modelToUpDate.getImg());
                    model.setPub_id(modelToUpDate.getPub_id());
                    model.setCat_id(modelToUpDate.getCat_id());
                    
                    int index = ls.indexOf(model);
                    table.getItems().set(index, model);
                    System.out.println("load 2: load one item");
                    break;
                }
            }
            
//            ObservableList<TableColumn<Model, ?>> cols = table.getColumns();
//            cols.get(0).setCellValueFactory(new PropertyValueFactory("book_id"));
//            cols.get(1).setCellValueFactory(new PropertyValueFactory("book_name"));
//            cols.get(2).setCellValueFactory(new PropertyValueFactory("description"));
//            cols.get(3).setCellValueFactory(new PropertyValueFactory("price"));
//            cols.get(4).setCellValueFactory(new PropertyValueFactory("img"));
//            cols.get(5).setCellValueFactory(new PropertyValueFactory("pub_id"));
//            cols.get(6).setCellValueFactory(new PropertyValueFactory("cat_id"));
            table.setItems(ls);
            
            modelToUpDate = null;
            selectedBook_id = "";
            loading=true;
        }

        table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Model selectedModel = table.getSelectionModel().getSelectedItem();
                if (selectedModel != null) {
                    selectedBook_id = selectedModel.getBook_id();

                    book_id1.setText(selectedModel.getBook_id());
                    book_name1.setText(selectedModel.getBook_name());
                    description1.setText(selectedModel.getDescription());
                    price1.setText(String.valueOf(selectedModel.getPrice()));
                    pub_id1.setText(selectedModel.getPub_id());
                    cat_id1.setText(selectedModel.getCat_id());
                    img1.setText(selectedModel.getImg());

                    System.out.println("Selected book_id " + selectedBook_id);
                }
            }
        });

    }

    public void addButton() {
        String book_id = book_id1.getText();
        String book_name = book_name1.getText();
        String description = description1.getText();
        String price = price1.getText();
        String pub_id = pub_id1.getText();
        String cat_id = cat_id1.getText();
        String img = img1.getText();
        //Nếu người dùng nhập chữ ở ô price sẽ báo lỗi

        Model model = new Model(book_id, book_name, description, Integer.parseInt(price), img, pub_id, cat_id);
        ModelRepository modelRepository = new ModelRepository();
        Connection conn = modelRepository.getConnection();

        try {
            ModelRepository.addBook(conn, model);
            refreshData();
            clearForm();
            System.out.println("Data added to the database");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshData() {
        ModelRepository mcr = new ModelRepository();
        ls.clear();
        ls.addAll(mcr.findAll());
    }

    public void clearForm() {
        book_id1.setText("");
        book_name1.setText("");
        description1.setText("");
        img1.setText("");
        price1.setText("");
        pub_id1.setText("");
        cat_id1.setText("");

    }

    public void editButton() throws IOException {
        if (!selectedBook_id.isEmpty()) {
            App.setRoot("crud_edit");
        }
    }

    public void showDeleteConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận xoá");
        alert.setHeaderText("Bạn có chắc chắn muốn xoá mục này?");
        alert.setContentText("Chọn 'OK' để xoá hoặc 'Cancel' để huỷ bỏ.");

        //Thêm nút Yes
        ButtonType buttonTypeYes = new ButtonType("Yes");
        //Thêm nút No
        ButtonType buttonTypeNo = new ButtonType("No", ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        //Hiển thị hộp thoại xác nhận và xử lý phản hồi
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeYes) {
            //Code xu ly xoa item
            ModelRepository modelRepository = new ModelRepository();
            Connection conn = modelRepository.getConnection();

            try {
                ModelRepository.deleteBook(conn, selectedBook_id);
                refreshData();
                clearForm();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Người dùng đã chọn "No" hoặc đóng hộp thoại
            // Thêm mã xử lý cho trường hợp người dùng không muốn xoá ở đây
        }

    }
}

        // Tu add comment

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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author sonly
 */
public class Crud_editController implements Initializable {
    

    private String selectedBookId = "";

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

    private Connection connection;

    private ObservableList<Model> ls;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        selectedBookId = CrudController.selectedBook_id;
        

        if (selectedBookId != null && !selectedBookId.isEmpty()) {
            ModelRepository mcr = new ModelRepository();

           Model selectedModel = mcr.findByBookId(selectedBookId);
            if (selectedModel != null) {
                book_id1.setText(selectedModel.getBook_id());
                book_name1.setText(selectedModel.getBook_name());
                description1.setText(selectedModel.getDescription());
                price1.setText(String.valueOf(selectedModel.getPrice()));
                pub_id1.setText(selectedModel.getPub_id());
                cat_id1.setText(selectedModel.getCat_id());
                img1.setText(selectedModel.getImg());
                
                CrudController.modelToUpDate = selectedModel;
            }
        } else {
            System.out.println("No book_id selected");
        }
    }

    public void updateButton() {
        String book_id = book_id1.getText();
        String book_name = book_name1.getText();
        String description = description1.getText();
        String price = price1.getText();
        String pub_id = pub_id1.getText();
        String cat_id = cat_id1.getText();
        String img = img1.getText();
        
        Model model = new Model(book_id,book_name,description,Integer.parseInt(price) ,img,pub_id,cat_id);
        ModelRepository mcr = new ModelRepository();
        Connection conn = mcr.getConnection();
        
        try {
            ModelRepository.updateBook(conn, model);
            System.out.println("Data added to the database");
            CrudController.loading = false;
            returnCrud();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public void returnCrud () throws  IOException{
//        CrudController.selectedBook_id = "";
        App.setRoot("crud");
    }
}

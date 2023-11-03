module com.sopvn.test_crod {
    requires javafx.controls;
    requires javafx.fxml;
    
    requires com.microsoft.sqlserver.jdbc;
    requires java.sql;
    opens com.sopvn.test_crod.dbconnect to javafx.base;
    
//    requires net.sf.jasperreports.engine;
//    requires net.sf.jasperreports.engine.design;
    
    
    opens com.sopvn.test_crod to javafx.fxml;
    exports com.sopvn.test_crod;
}

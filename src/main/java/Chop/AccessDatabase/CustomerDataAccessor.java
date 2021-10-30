package Chop.AccessDatabase;

import Chop.Model.Customer;

import java.sql.Connection ;
import java.sql.DriverManager ;
import java.sql.SQLException ;
import java.sql.Statement ;
import java.sql.ResultSet ;

import java.util.List ;
import java.util.ArrayList ;

public class CustomerDataAccessor {

    private Connection connection ;

    public CustomerDataAccessor(String driverClassName, String dbURL, String user, String password) throws SQLException, ClassNotFoundException {
        Class.forName(driverClassName);
        connection = DriverManager.getConnection(dbURL, user, password);
    }

    public void shutdown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public List<Customer> getCustomerList() throws SQLException {
        try (
                Statement stmnt = connection.createStatement();
                ResultSet rs = stmnt.executeQuery("select * from person");
        ){
            List<Customer> customerList = new ArrayList<>();
            while (rs.next()) {
                String user = rs.getString("Cus_userName");
                Customer customer = new Customer(user);
                customerList.add(customer);
            }
            return customerList;
        }
    }

}

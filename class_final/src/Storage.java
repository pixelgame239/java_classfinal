import java.sql.Statement;
import java.util.Scanner;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Storage extends Connect {
    private String vac_code, vac_name;
    private int quantity;
    public Storage() throws SQLException{
        super.connect_database();
    }
    public void get_vac() throws SQLException{
        ResultSet rs = null;
        Statement st  = null;
        String query = "Select * from vaccine_storage";
        st = conn.createStatement();
        rs = st.executeQuery(query);
        while(rs.next()){
            System.out.println("Vaccine code: " + rs.getString(1) + " - Vaccine name: " + rs.getString(2) + " -Vaccine Quantity: " + rs.getString(3));
        }
        st.close();
        rs.close();
    }
    public boolean get_vaccode(String vac_code) throws SQLException{
        PreparedStatement pr = null;
        ResultSet rs = null;
        String query = "Select vac_code from vaccine_storage where vac_code = ?";
        pr = conn.prepareStatement(query);
        pr.setString(1, vac_code);
        rs = pr.executeQuery();
        if (rs.next()){
            return true;
        }
        else{
            return false;
        }
    }
    public void get_vacname() throws SQLException{
        ResultSet rs = null;
        Statement st = null;
        String query = "Select vac_name, vac_code from vaccine_storage";
        st = conn.createStatement();
        rs= st.executeQuery(query);
        while (rs.next()){
            System.out.println("Vaccine name: " + rs.getString("vac_name") + " -Vaccine code:" + rs.getString(2));
        }
        rs.close();
    }
    public void search_vac() throws SQLException{
        ResultSet rs = null;
        PreparedStatement pr = null;
        String query, name, code;
        Scanner sc = new Scanner(System.in);
        int choice=0;
        while (choice!=3){
            System.out.println("Search by: ");
            System.out.println("1. Name");
            System.out.println("2: Code");
            System.out.println("3. Cancel");
            choice = sc.nextInt();
            sc.nextLine();
            switch(choice){
                case 1: 
                    System.out.println("Enter name:");
                    name = sc.nextLine();
                    query = "Select * from vaccine_storage where vac_name =?";
                    pr = conn.prepareStatement(query);
                    pr.setString(1, name);
                    rs = pr.executeQuery();
                    boolean found = false;
                    while(rs.next()){
                        System.out.println("Vaccine code: " + rs.getString(1) + " - Vaccine name: " + rs.getString(2) + " -Vaccine Quantity: " + rs.getString(3));
                        found = true;
                    }
                    if (!found){
                        System.out.println("Not available");
                    }
                    pr.close();
                    rs.close();
                    break;
                case 2:
                    System.out.println("Enter code: ");
                    code = sc.nextLine();
                    query = "Select * from vaccine_storage where vac_code =?";
                    pr = conn.prepareStatement(query);
                    pr.setString(1, code);
                    rs = pr.executeQuery();
                    boolean found2 = false;
                    while(rs.next()){
                        System.out.println("Vaccine code: " + rs.getString(1) + " - Vaccine name: " + rs.getString(2) + " -Vaccine Quantity: " + rs.getString(3));
                        found2 = true;
                    }
                    if (!found2){
                        System.out.println("Not available");
                }
                rs.close();
                pr.close();
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
    }
    
}
    public void add() throws SQLException{
        Scanner sc = new Scanner(System.in);
        ResultSet rs = null;
        PreparedStatement pr = null;
        System.out.println("Enter vaccine code");
        vac_code = sc.nextLine();
        String query = "Select vac_code from vaccine_storage where vac_code = ?";
        pr = conn.prepareStatement(query);
        pr.setString(1, vac_code);
        rs = pr.executeQuery();
        if (rs.next()){
            System.out.println("Cannot add");
            rs.close();
            pr.close();
        }
        else{
            rs.close();
            pr.close();
            System.out.println("Enter vaccine name");
            vac_name = sc.nextLine();
            query = "Select vac_name from vaccine_storage where vac_name = ?";
            pr = conn.prepareStatement(query);
            pr.setString(1,vac_name);
            rs = pr.executeQuery();
            if (rs.next()){
                System.out.println("Cannot add");
                rs.close();
                pr.close();
            }
            else{
                rs.close();
                pr.close();
                System.out.println("Enter quantity");
                quantity = sc.nextInt();
                query = "insert into vaccine_storage(vac_code, vac_name, quantity) values (?,?,?)";
                pr = conn.prepareStatement(query);
                pr.setString(1, vac_code);
                pr.setString(2, vac_name);
                pr.setInt(3, quantity);
                pr.executeUpdate();
                System.out.println("Added");
                pr.close();
            }
        }
        
    }
    public void edit() throws SQLException{
        Scanner sc = new Scanner(System.in);
        ResultSet rs = null;
        PreparedStatement pr = null;
        System.out.println("Enter vaccine code want to change quantity: ");
        vac_code = sc.nextLine();
        String query = "Select vac_code from vaccine_storage where vac_code = ?";
        pr = conn.prepareStatement(query);
        pr.setString(1, vac_code);
        rs = pr.executeQuery();
        if(rs.next()){
            pr.close();
            System.out.println("Enter quantity: ");
            quantity = sc.nextInt();
            query = "update vaccine_storage set quantity = ? where vac_code =?";
            pr =conn.prepareStatement(query);
            pr.setInt(1, quantity);
            pr.setString(2, vac_code);
            pr.executeUpdate();
            System.out.println("Editted");
            pr.close();
            rs.close();
        }
        else {
            System.out.println("Not available");
            pr.close();
            rs.close();
        }
        
    }
    /*public void delete() throws SQLException{
        Scanner sc = new Scanner(System.in);
        PreparedStatement pr = null;
        ResultSet rs = null;
        System.out.println("Enter vaccine code want to delete: ");
        vac_code = sc.nextLine();
        String query = "Select vac_code from vaccine_storage where vac_code = ?";
        pr = conn.prepareStatement(query);
        pr.setString(1, vac_code);
        rs = pr.executeQuery();
        if(rs.next()){
            pr.close();
            query = "Delete from vaccine_storage where vac_code = ?";
            pr = conn.prepareStatement(query);
            pr.setString(1, vac_code);
            pr.executeUpdate();
            System.out.println("Deleted");
            pr.close();
        }
        else {
            System.out.println("Not available");
            pr.close();
            rs.close();
        }
        
    }*/
    public void update(int change, String code) throws SQLException{
        String query = "Select quantity from vaccine_storage where vac_code = ?";
        PreparedStatement pr = null;
        ResultSet rs = null;
        pr =conn.prepareStatement(query);
        pr.setString(1, code);
        rs = pr.executeQuery();
        while(rs.next()){
            quantity = rs.getInt("quantity");
        }
        pr.close();
        rs.close();
        int quan = quantity + change;
        query = "Update vaccine_storage set quantity = ? where vac_code = ?";
        pr =conn.prepareStatement(query);
        pr.setInt(1, quan);
        pr.setString(2, code);
        pr.executeUpdate();
        pr.close();
    }
}

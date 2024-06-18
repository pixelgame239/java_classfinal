import java.sql.Statement;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Medical_staff extends Connect{
    public Medical_staff() throws SQLException{
        super.connect_database();
    
    }
    public int get_st_count() throws SQLException{
        ResultSet rs = null;
        Statement st = null;
        int c=0;
        String query = "Update numb_code set st_numb = st_numb+1";
        st =conn.createStatement();
        st.executeUpdate(query);
        st.close();
        query = "Select st_numb from numb_code";
        st = conn.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()){
            c =rs.getInt(1); 
        }
        rs.close();
        st.close();
        return c;
    }
    public int get_doc_count() throws SQLException{
        ResultSet rs = null;
        Statement st = null;
        int c=0;
        String query = "Update numb_code set doc_numb = doc_numb+1";
        st =conn.createStatement();
        st.executeUpdate(query);
        st.close();
        query = "Select doc_numb from numb_code";
        st = conn.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()){
            c =rs.getInt(1); 
        }
        rs.close();
        st.close();
        return c;
    }
    public void get_table() throws SQLException{
        String query = "Select * from medical_staff order by st_code asc";
        ResultSet rs = null;
        Statement st = null;
        st = conn.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()){
            System.out.println("Staff code: " + rs.getString(1) + " - Staff name: " + rs.getString(2) + " -Responsibility:   " + rs.getString(3));
        }
        rs.close();
        st.close();
    }
    public void get_stcode(String ip_code) throws SQLException{
        String query = "Select * from medical_staff where st_code = ?";
        ResultSet rs = null;
        PreparedStatement pr =null;
        pr = conn.prepareStatement(query);
        pr.setString(1, ip_code);
        rs = pr.executeQuery();
        while (rs.next()){
            System.out.println("Staff code: " + rs.getString(1) + " - Staff name: " + rs.getString(2) + " -Responsibility:   " + rs.getString(3));
        }
        rs.close();
        pr.close();
    }
    public boolean search_med(String ip_code) throws SQLException{
        String query = "Select * from medical_staff where st_code =?";
        ResultSet rs = null;
        PreparedStatement pr =null;
        pr = conn.prepareStatement(query);
        pr.setString(1, ip_code);
        rs = pr.executeQuery();
        boolean found = false;
        if(rs.next()){
            found = true;
        }
        return found;
    }
    public void delete(String ip_code) throws SQLException{
        String query = "Delete from medical_staff where st_code = ?";
        if (search_med(ip_code)){
            PreparedStatement pr = null;
            pr = conn.prepareStatement(query);
            pr.setString(1, ip_code);
            pr.executeUpdate();
            System.out.println("Deleted");
            pr.close();
        }
        else{
            System.out.println("Not found to delete");
        }
    }

    public void edit(String ip_code) throws SQLException{
        String query = "Update medical_staff set st_name =? where st_code = ?";
        if (search_med(ip_code)){
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter change name: ");
            String ip_name = sc.nextLine();
            PreparedStatement pr = null;
            pr = conn.prepareStatement(query);
            pr.setString(1, ip_name);
            pr.setString(2, ip_code);
            pr.executeUpdate();
            System.out.println("Editted");
            pr.close();
            
        }
        else{
            System.out.println("Not found to edit");
        }
    }
    public void add() throws SQLException{
        String query = "Insert into medical_staff(st_code, st_name, res) values (?,?,?)";
        String ip_code="";
        PreparedStatement pr = null;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter name: ");
        String ip_name = sc.nextLine();
        System.out.println("Enter position");
        String pos = sc.nextLine();
        int count = 0;
        if (pos.equals("Doctor")){
            count = get_doc_count();
            String cod = String.valueOf(count);
            if (count<10){
                ip_code = "D00" + cod;
            }
            else if (count>=10&&count<=99){
                ip_code = "D0" + cod;
            }
            else if (count>99&&count<=999){
                ip_code = "D" + cod;
            }
            pr = conn.prepareStatement(query);
            pr.setString(1, ip_code);
            pr.setString(2,ip_name);
            pr.setString(3, pos);
            pr.executeUpdate();
            System.out.println("Added");
            pr.close();
        }
        else if (pos.equals("Staff")){
            count = get_st_count();
            String cod = String.valueOf(count);
            if (count<10){
                ip_code = "S00" + cod;
            }
            else if (count>=10&&count<=99){
                ip_code = "S0" + cod;
            }
            else if (count>99&&count<=999){
                ip_code = "S" + cod;
            }
            pr = conn.prepareStatement(query);
            pr.setString(1, ip_code);
            pr.setString(2,ip_name);
            pr.setString(3, pos);
            pr.executeUpdate();
            System.out.println("Added");
            pr.close();
        }
        else {
            System.out.println("Invalid position");
        }
    
    }
}

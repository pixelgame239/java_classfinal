import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Participant extends Connect{
    private String p_code;
    private String p_name;
    private String age;
    private String gender;
    private String phone;
    private String vac_code;
    private String reg_date;
    private String inj_date;
    public Participant() throws SQLException{
        super.connect_database();
        
    }
    public String get_vaccode(String par_code) throws SQLException{
        String query = "Select vac_code from participant where par_code = ?";
        PreparedStatement pr = null;
        ResultSet rs =null;
        pr =conn.prepareStatement(query);
        pr.setString(1, par_code);
        rs = pr.executeQuery();
        while(rs.next()){
            return rs.getString(1);
        }
        return null;
    }
    public String get_pcode(String ip_code) throws SQLException{
        ResultSet rs = null;
        PreparedStatement pr = null;
        String query = "Select par_code from participant where par_code = ?";
        pr = conn.prepareStatement(query);
        pr.setString(1, ip_code);
        rs = pr.executeQuery();
        if (rs.next()){
            return ip_code;
        }
        else{
            return null;
        }
    }
    public boolean get_phonenumber(String ip_phone, String ip_code) throws SQLException{
        ResultSet rs = null;
        PreparedStatement pr = null;
        String query = "Select phone_number from participant where phone_number = ? and par_code = ?";
        pr = conn.prepareStatement(query);
        pr.setString(1, ip_phone);
        pr.setString(2, ip_code);
        rs = pr.executeQuery();
        if(!rs.next()){
            return false;
        }
        else{
            return true;
        }
    }
    public int count() throws SQLException{
        ResultSet rs = null;
        Statement st = null;
        int c=0;
        String query = "Update numb_code set numb = numb+1";
        st =conn.createStatement();
        st.executeUpdate(query);
        st.close();
        query = "Select numb from numb_code";
        st = conn.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()){
            c =rs.getInt(1); 
        }
        rs.close();
        st.close();
        return c;
    }
    public void get_par() throws SQLException{
        ResultSet rs = null;
        Statement st = null;
        String query = "Select * from participant";
        st = conn.createStatement();
        rs = st.executeQuery(query);
        while(rs.next()){
            System.out.println("Participant code: " + rs.getString(1) + " - Participant name: " + rs.getString(2) 
            + " -Age: " + rs.getInt(3) + " -Gender: " + rs.getString(4) + " -Phone number: " + rs.getString(5) 
            + " -Vaccine code: " + rs.getString(6) + "  -Register Date: " + rs.getDate(7) + " -Inject Date: " + rs.getDate(8));
        }
        st.close();
        rs.close();
    }
    public void get_info(String column, String value) throws SQLException{
        String query = "Select * from participant where " +column +" = ?";
        PreparedStatement pr =null;
        ResultSet rs = null;
        pr = conn.prepareStatement(query);
        boolean found = false;
        pr.setString(1, value);
        rs = pr.executeQuery();
        while(rs.next()){
            System.out.println("Participant code: " + rs.getString(1) + " - Participant name: " + rs.getString(2) 
            + " -Age: " + rs.getInt(3) + " -Gender: " + rs.getString(4) + " -Vaccine code: " + rs.getString(6) + "  -Register Date: " + rs.getDate(7) + " -Inject Date: " + rs.getDate(8));
            found = true;
            }
        if (!found){
            System.out.println("Not found");
        }
        pr.close();
        rs.close();
    }
    public void edit() throws SQLException{
        PreparedStatement pr = null;
        String query;
        System.out.println("Enter participant code: ");
        Scanner sc = new Scanner(System.in);
        p_code = sc.nextLine();
        if(get_pcode(p_code) == null){
            System.out.println("Haven't registered yet");
        }
        else{
            System.out.println("Enter phone number: ");
            phone = sc.nextLine();
            if (get_phonenumber(phone, p_code)){
                String old_vac_code = get_vaccode(p_code);
                query = "Update participant set par_name = ?, age = ?, gender = ?, phone_number = ?, vac_code =?, register_date = ?, inject_date = ? where par_code = ?";
                System.out.println("Enter change name: ");
                p_name = sc.nextLine();
                System.out.println("Enter change age: ");
                age = sc.nextLine();
                System.out.println("Enter change gender: ");
                gender = sc.nextLine();
                System.out.println("Enter change phone number: ");
                phone = sc.nextLine();
                System.out.println("Enter change vaccine code: ");
                vac_code = sc.nextLine();
                System.out.println("Enter change register date: ");
                reg_date = sc.nextLine();
                System.out.println("Enter change inject date: ");
                inj_date = sc.nextLine();
                pr = conn.prepareStatement(query);
                pr.setString(1, p_name);
                pr.setString(2,age);
                pr.setString(3, gender);
                pr.setString(4,phone);
                pr.setString(5, vac_code);
                pr.setString(6,reg_date);
                pr.setString(7, inj_date);
                pr.setString(8, p_code);
                pr.executeUpdate();
                if (!vac_code.equals(old_vac_code)){
                    Storage store = new Storage();
                    store.update(1, old_vac_code);
                    store.update(-1, vac_code);
                }
                System.out.println("Editted");
                pr.close();
            }
            else{
                System.out.println("You don't have permission to edit that data");
            }
        }
        
    }
    public void delete(String ip_code) throws SQLException{
        PreparedStatement pr = null;
        String query = "Delete from participant where par_code = ?";
        pr = conn.prepareStatement(query);
        pr.setString(1, ip_code);
        pr.executeUpdate();
        String vac = get_vaccode(ip_code);
        Storage store = new Storage();
        store.update(1, vac);
        System.out.println("Deleted");
        pr.close();
    }
    public boolean register() throws SQLException, ParseException{
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your name: ");
        p_name = sc.nextLine();
        System.out.println("Enter your age: ");
        age = sc.nextLine();
        System.out.println("Enter your gender: ");
        gender = sc.nextLine();
        if (!gender.equals("Male") && !gender.equals("Female")){
            System.out.println("Invalid gender!");
            
            return false;
        }
        else{
            System.out.println("Enter your phone number: ");
            phone = sc.nextLine();
            System.out.println("Enter your vaccine code: ");
            vac_code = sc.nextLine();
            Storage store = new Storage();
            if(!store.get_vaccode(vac_code)){
                System.out.print("Invalid vaccine code");
                
                return false;
            }
            else{
                java.util.Date cur_date = new java.util.Date();
                System.out.println("Enter your inject date:  (format: yyyy-mm-dd)");
                inj_date = sc.nextLine();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date inject = format.parse(inj_date);
                if (inject.before(cur_date)){
                    System.out.println("Invalid inject date!");
                    
                    return false;
                }
                else{
                    PreparedStatement pr = null;
                    reg_date = format.format(cur_date);
                    String query = "Insert into participant(par_code, par_name, age, gender, phone_number, vac_code, register_date, inject_date) values (?,?,?,?,?,?,?,?)";
                    int nmb = count();
                    String num = String.valueOf(nmb);
                    if (nmb<10){
                        p_code = "P00"+num;
                    }
                    else if(nmb>=10&&nmb<=99){
                        p_code = "P0"+num;
                    }
                    else if (nmb>99&&nmb<=999){
                        p_code = "P" + num;
                    }
                    pr = conn.prepareStatement(query);
                    pr.setString(1, p_code);
                    pr.setString(2, p_name);
                    pr.setString(3, age);
                    pr.setString(4, gender);
                    pr.setString(5, phone);
                    pr.setString(6, vac_code);
                    pr.setString(7, reg_date);
                    pr.setString(8, inj_date);
                    pr.executeUpdate();
                    store.update(-1,vac_code);
                    System.out.println("Registered");
                    pr.close();
                }
            }
        }
        
        return true;
    }
}

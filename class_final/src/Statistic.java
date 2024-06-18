import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Statistic extends Connect {
    public Statistic() throws SQLException{
        super.connect_database();
    }
    public void get_table() throws SQLException{
        String query = "Select p.par_code, p.par_name, p.inject_date, p.phone_number, v.vac_code, v.vac_name, s.is_injected from statistic s " +
        "inner join participant p on p.par_code = s.par_code " +
        "inner join vaccine_storage v on v.vac_code = s.vac_code";
        ResultSet rs = null;
        Statement st = null;
        st = conn.createStatement();
        rs = st.executeQuery(query);
        while(rs.next()){
            System.out.println("Participant code: " + rs.getString(1) +" -Participant name: " + rs.getString(2) + " -Participant's inject date: " + rs.getString(3) + 
            "-Participant's phone number: " + rs.getString(4) + " -Vaccine code: " + rs.getString(5) + " -Vaccine name: " + rs.getString(6) + " -Injected: " + rs.getString(7));
        }
        rs.close();
        st.close();
    }
    public void search() throws SQLException{
        Scanner sc = new Scanner(System.in);
        PreparedStatement pr =null;
        ResultSet rs = null;
        System.out.println("Search by: ");
        System.out.println("1: Participant code ");
        System.out.println("2: Vaccine code ");
        int op = sc.nextInt();
        String query;
        if (op == 1){
            query= "Select p.par_code, p.par_name, p.inject_date, p.phone_number, v.vac_code, v.vac_name, s.is_injected from statistic s " +
            "inner join participant p on p.par_code = s.par_code " +
            "inner join vaccine_storage v on v.vac_code = s.vac_code where p.par_code = ?";
            sc.nextLine();
            System.out.print("Enter participant code: ");
            String par_code = sc.nextLine();
            pr = conn.prepareStatement(query);
            pr.setString(1, par_code);
            rs = pr.executeQuery();
            boolean found = false;
            while(rs.next()){
                System.out.println("Participant code: " + rs.getString(1) +" -Participant name: " + rs.getString(2) + " -Participant's inject date: " + rs.getString(3) + 
                "-Participant's phone number: " + rs.getString(4) + " -Vaccine code: " + rs.getString(5) + " -Vaccine name: " + rs.getString(6) + " -Injected: " + rs.getString(7));
                found = true;
            }
            if (!found){
                System.out.println("Data not found");
            }
            rs.close();
            pr.close();
            
        }
        else if (op ==2){
            query= "Select p.par_code, p.par_name, p.inject_date, p.phone_number, v.vac_code, v.vac_name, s.is_injected from statistic s " +
            "inner join participant p on p.par_code = s.par_code " +
            "inner join vaccine_storage v on v.vac_code = s.vac_code where v.vac_code = ?";
            sc.nextLine();
            System.out.print("Enter vaccine code: ");
            String vac_code = sc.nextLine();
            pr = conn.prepareStatement(query);
            pr.setString(1, vac_code);
            rs = pr.executeQuery();
            boolean found = false;
            while(rs.next()){
                System.out.println("Participant code: " + rs.getString(1) +" -Participant name: " + rs.getString(2) + " -Participant's inject date: " + rs.getString(3) + 
                "-Participant's phone number: " + rs.getString(4) + " -Vaccine code: " + rs.getString(5) + " -Vaccine name: " + rs.getString(6) + " -Injected: " + rs.getString(7));
                found = true;
            }
            if (!found){
                System.out.println("Data not found");
            }
            rs.close();
            pr.close();
            
        }
    }
    public void update(String mode, String par_code, String injected, String vac_code) throws SQLException, ParseException{
        String query;
        Scanner sc = new Scanner(System.in);
        if(mode == null){
            Date cur_date = new Date();
            String inj_date;
            Date i_date;
            query = "Select inject_date, par_code from participant";
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Statement st = null;
            ResultSet rs = null;
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()){
                inj_date = rs.getString(1);
                i_date = format.parse(inj_date);
                if(cur_date.after(i_date)){
                    ResultSet rs1 = null;
                    String par_code1 = "";
                    par_code = rs.getString(2);
                    query = "Select par_code from statistic where is_injected = ?";
                    PreparedStatement pr = null;
                    pr = conn.prepareStatement(query);
                    pr.setString(1, "No");
                    rs1 = pr.executeQuery();
                    while (rs1.next()){
                        par_code1 =rs1.getString("par_code");
                        if (par_code1.equals(par_code)){
                            query = "Delete from participant where par_code = ?";
                            PreparedStatement pr1 = null;
                            pr1 =conn.prepareStatement(query);
                            pr1.setString(1, par_code1);
                            pr1.executeUpdate();
                            pr1.close();
                        }
                    }
                    pr.close();
                }
            }
            System.out.println("Refreshed");
            st.close();
            rs.close();
        }
        else if (mode.equals("Inject")){
            System.out.println("Enter participant code");
            par_code = sc.nextLine();
            PreparedStatement pr = null;
            ResultSet rs = null;
            query = "Select is_injected from statistic where par_code = ?";
            pr = conn.prepareStatement(query);
            pr.setString(1, par_code);
            rs = pr.executeQuery();
            boolean found = false;
            while(rs.next()){
                found = true;
            }
            if(!found){
                System.out.println("Not found");
            }
            else {
                query = "Update statistic set is_injected = ? where par_code = ?";
                pr.close();
                pr = conn.prepareStatement(query);
                System.out.println("Change to: ");
                injected = sc.nextLine();
                if (injected.equals("Yes") || injected.equals("No")){
                    pr.setString(1, injected);
                    pr.setString(2,par_code);
                    pr.executeUpdate();
                    System.out.println("Updated");
                    rs.close();
                }
                else{
                    System.out.println("Invalid value");
                }
            }
            pr.close();
            
        }
        else if(mode.equals("Add")){
            query = "Select numb from numb_code";
            ResultSet rs =null;
            Statement st = null;
            PreparedStatement pr = null;
            int co=0;
            st =conn.createStatement();
            rs = st.executeQuery(query);
            while(rs.next()){
                co =rs.getInt(1);
            }
            if (co<10){
                par_code = "P00" + String.valueOf(co);
            }
            else if (co>=10 && co <=99){
                par_code = "P0" + String.valueOf(co);
            }
            else if (co>99&&co<=999){
                par_code = "P" + String.valueOf(co);
            }
            rs.close();
            st.close();
            query = "Select vac_code from participant where par_code = ?";
            pr = conn.prepareStatement(query);
            pr.setString(1, par_code);
            rs = pr.executeQuery();
            while(rs.next()){
                vac_code =rs.getString(1);
            }
            pr.close();
            rs.close();
            query = "Insert into statistic(vac_code, par_code, is_injected) values (?,?,?)";
            pr = conn.prepareStatement(query);
            pr.setString(1, vac_code);
            pr.setString(2,par_code);
            pr.setString(3, "No");
            pr.executeUpdate();
            pr.close();
        }
    }
}

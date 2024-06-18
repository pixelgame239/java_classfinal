import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Ad_account extends Connect  {
    private String account_name ;
    private String password;
    private String reset_account;
    private int attempts=1;
    //Ad_account admin = new Ad_account();
    public Ad_account() throws SQLException{
        super.connect_database();
        this.account_name =getacc_name();
        this.password = getpass();
        this.reset_account = getreset();
        this.attempts = getattempts();
    }
    public ResultSet getadmin_acc(String column) throws SQLException{
        ResultSet rs =null;
        Statement st = null;
        String qu = "Select " + column + " from admin_acc";
        st = conn.createStatement();
        rs = st.executeQuery(qu);
        return rs;
    }
    public String getacc_name() throws SQLException{
        ResultSet rs = getadmin_acc("acc_name");
        while(rs.next()){ 
            this.account_name = rs.getString("acc_name");
        }
        return this.account_name;
    }
    public String getpass() throws SQLException {
        ResultSet rs = getadmin_acc("pass");
        while(rs.next()){ 
            this.password = rs.getString("pass");
        }
        rs.close();
        return this.password;
    }
    public int getattempts() throws SQLException{
        ResultSet rs = getadmin_acc("attempts");
        while(rs.next()){ 
            this.attempts = rs.getInt("attempts");
        }
        rs.close();
        return this.attempts;
    }
    public String getreset() throws SQLException{
        ResultSet rs = getadmin_acc("reset_acc");
        while(rs.next()){ 
            this.reset_account = rs.getString("reset_acc");
        }
        rs.close();
        return this.reset_account;
    }
    public void setattempts(int att) throws SQLException{
        PreparedStatement pr = null;
        String qu = "Update admin_acc set attempts = ?";
        pr = conn.prepareStatement(qu);
        pr.setInt(1, att);
        pr.executeUpdate();
        pr.close();
    }
    public void setpass() throws SQLException{
        String new_pass, old_pass;
        Scanner sca = new Scanner(System.in);
        int att = getattempts();
        while (att <= 3) {
            System.out.println("Enter your current password: ");
            old_pass = sca.nextLine();
            if (old_pass.equals(this.password)) {
                System.out.println("Enter your new password: ");
                new_pass = sca.nextLine();
                PreparedStatement pr = null;
                String qu = "Update admin_acc set pass = ?";
                pr = conn.prepareStatement(qu);
                pr.setString(1, new_pass);
                pr.executeUpdate();
                this.password = getpass();
                pr.close();
                break;
            } else {
                System.out.println("Incorrect password! Try again!");
                att+=1;
            }
        }
        if (att>=4){
            setattempts(4);
            System.out.println("You entered wrong password 3 times. Locked.");
        }
        else {
            System.out.println("Successful");
        }
               
    }
    public void login() throws SQLException{
        Scanner sca = new Scanner(System.in);
        String username, pas;
        int att;
        att = getattempts();
        while (att<=3){
            System.out.println("Enter username: ");
            username = sca.nextLine();
            if (username.equals(getacc_name())){
                    att=1;
                    while(att<=3){
                    System.out.println("Enter password: ");
                    pas = sca.nextLine();
                    if (pas.equals(this.password)){
                        System.out.println("Logged in");
                        break;
                    }
                    else {
                        System.out.println("Incorrect password!");
                        att+=1;
                    }
                }
                break;
            }
            else{
                System.out.println("Invalid username!");
                att+=1;
            }
        }
        if (att>=4){
        setattempts(4);
        System.out.println("You entered wrong 3 times. Locked");
        }
        
     }
     public void reset() throws SQLException{
        Scanner sca = new Scanner(System.in);
        String username, reset_acc;
        System.out.println("Enter username: ");
            username = sca.nextLine();
            if (username.equals(getacc_name())){
                System.out.println("Enter reset code: ");
                reset_acc = sca.nextLine();
                if (reset_acc.equals(this.reset_account)){
                    Statement st = null;
                    st = conn.createStatement();
                    String query = "update admin_acc set pass = 'doctor123'";
                    st.executeUpdate(query);
                    setattempts(1);
                    st.close();
                    System.out.println("Reset completed");
                }
                else{
                    System.out.println("Contact admin for reset code");
                }
            }
            else{
                System.out.println("You don't have permission to reset account");
            }
            
     }
    }  
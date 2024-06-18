import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

public class Mainpro {
    public static void main(String[] args) throws SQLException, ParseException{    
        int opt=0, opt1 =0, opt2=0, opt3 =0;
        boolean loop= true, loop1, loop2, loop3;
        while (loop==true){
            Scanner sc = new Scanner(System.in);
            Ad_account adm = new Ad_account();
            Storage store = new Storage();
            Medical_staff med = new Medical_staff();
            Participant par = new Participant();
            Statistic stat = new Statistic();
            System.out.println("1. Login as admin");
            System.out.println("2. Guest");
            System.out.println("3. Reset admin account");
            System.out.println("4. Quit");
            System.out.println("Choose option: ");
            opt = sc.nextInt();
            switch(opt){
                case 1:
                    adm.login();
                    if(adm.getattempts()==1){
                        loop1 = true;
                        while(loop1==true){
                        System.out.println("Choose option:");
                        System.out.println("1. Vaccine Storage");
                        System.out.println("2. Participant");
                        System.out.println("3. Medical staff");
                        System.out.println("4. Statistic");
                        System.out.println("5: Change password");
                        System.out.println("6: Quit");
                        opt1 = sc.nextInt();
                        switch(opt1){
                            case 1:
                            loop2 = true;
                            while(loop2==true){
                                System.out.println("Choose option:");
                                System.out.println("1. Get vaccine");
                                System.out.println("2. Search vaccine");
                                System.out.println("3. Edit vaccine quantity");
                                System.out.println("4. Add new vaccine");
                                System.out.println("5: Quit");
                                opt2 = sc.nextInt();
                                switch(opt2){
                                    case 1:
                                    store.get_vac();
                                    break;
                                    case 2:
                                    store.search_vac();
                                    break;
                                    case 3:
                                    store.edit();
                                    break;
                                    case 4:
                                    store.add();
                                    break;
                                    case 5:
                                    loop2 = false;
                                    break;
                                    default:
                                    System.out.println("Enter valid option");
                                    break;
                                }
                            }
                            break;
                            case 2:
                            loop2 = true;
                            while(loop2==true){
                                System.out.println("Choose option:");
                                System.out.println("1. Get participant list");
                                System.out.println("2. Search information");
                                System.out.println("3: Quit");
                                opt2 = sc.nextInt();
                                switch(opt2){
                                    case 1:
                                    par.get_par();
                                    break;
                                    case 2:
                                    loop3 = true;
                                    while(loop3==true){
                                        System.out.println("1. Search by inject date");
                                        System.out.println("2. Search by register date");
                                        System.out.println("3. Search by participant code");
                                        System.out.println("4. Cancel");
                                        opt3 = sc.nextInt();
                                        String value;
                                        switch (opt3) {
                                            case 1:
                                                System.out.println("Enter date: ");
                                                sc.nextLine();
                                                value = sc.nextLine();
                                                par.get_info("inject_date", value);
                                                break;
                                            case 2:
                                                System.out.println("Enter date: ");
                                                sc.nextLine();
                                                value = sc.nextLine();
                                                par.get_info("register_date", value);
                                                break;
                                            case 3:
                                                System.out.println("Enter participant code");
                                                sc.nextLine();
                                                value = sc.nextLine();
                                                par.get_info("par_code", value);
                                                break;
                                            case 4:
                                                loop3=false;
                                                break;
                                            default:
                                                System.out.println("Enter valid option");
                                                break;
                                        }
                                    }
                                    break;
                                    case 3:
                                    loop2 = false;
                                    break;
                                    default:
                                    System.out.println("Enter valid option");
                                    break;
                                }
                            }
                            break;
                            case 3:
                            loop2 = true;
                            while (loop2==true){
                                System.out.println("Choose option:");
                                System.out.println("1. Get medical staff list");
                                System.out.println("2. Search medical staff");
                                System.out.println("3: Delete a medical staff");
                                System.out.println("4: Edit information of a medical staff");
                                System.out.println("5. Add new medical staff");
                                System.out.println("6. Quit");
                                opt2 = sc.nextInt();
                                String value;
                                switch (opt2){
                                    case 1:
                                    med.get_table();
                                    break;
                                    case 2:
                                    System.out.println("Enter staff code: ");
                                    sc.nextLine();
                                    value = sc.nextLine();
                                    if(med.search_med(value)){
                                        med.get_stcode(value);
                                    }
                                    else{
                                        System.out.println("Not found");
                                    }
                                    break;
                                    case 3:
                                    System.out.println("Enter staff code");
                                    sc.nextLine();
                                    value = sc.nextLine();
                                    med.delete(value);
                                    break;
                                    case 4:
                                    System.out.println("Enter staff code");
                                    sc.nextLine();
                                    value = sc.nextLine();
                                    med.edit(value);
                                    break;
                                    case 5: 
                                    med.add();
                                    break;
                                    case 6:
                                    loop2=false;
                                    break;
                                    default:
                                    System.out.println("Enter valid option");
                                    break;
                                }
                            } 
                            break;
                            case 4:
                                loop2 = true;
                                while (loop2==true){
                                    System.out.println("Choose option:");
                                    System.out.println("1. Get statistic");
                                    System.out.println("2. Search in statistic");
                                    System.out.println("3: Change inject status in statistic");
                                    System.out.println("4: Update statistic (Should use the end of the day)");
                                    System.out.println("5. Quit");
                                    opt2 = sc.nextInt();
                                    switch (opt2) {
                                        case 1:
                                            stat.get_table();
                                            break;
                                        case 2:
                                            stat.search();
                                            break;
                                        case 3:
                                            stat.update("Inject", null, null, null);
                                            break;
                                        case 4:
                                            stat.update(null, null, null, null);
                                            break;
                                        case 5:
                                            loop2=false;
                                            break;
                                        default:
                                            System.out.println("Enter valid option");
                                            break;
                                    }
                                }
                            break;
                            case 5:
                            adm.setpass();
                            break;
                            case 6:
                            loop1 = false;
                            break;
                            default:
                            System.out.println("Enter valid option");
                            break;
                        }
                        }
                    }
                    break;
                case 2:
                    loop1 = true;
                    while(loop1 == true){
                        System.out.println("Choose option:");
                        System.out.println("1. Show vaccine available");
                        System.out.println("2. Show your information");
                        System.out.println("3. Edit your information");
                        System.out.println("4. Delete your information");
                        System.out.println("5. Register");
                        System.out.println("6. Quit");
                        opt1 = sc.nextInt();
                        switch(opt1){
                            case 1:
                            store.get_vacname();
                            break;
                            case 2:
                            sc.nextLine();
                            System.out.println("Enter your participant code:");
                            String p_code = sc.nextLine();
                            par.get_info("par_code",p_code);
                            break;
                            case 3:
                            par.edit();
                            break;
                            case 4:
                            sc.nextLine();
                            System.out.println("Enter your participant code");
                            String par_code = sc.nextLine();
                            System.out.println("Enter your phone number: ");
                            String p_num = sc.nextLine();
                            if(par.get_phonenumber(p_num, par_code)){
                                par.delete(par_code);
                            }
                            else{
                                System.out.println("You don't have permission to delete this data");
                            }
                            break;
                            case 5:
                            par.register();
                            stat.update("Add", null, null, null);
                            break;
                            case 6:
                            loop1 = false;
                            break;
                            default:
                            System.out.println("Enter valid option");
                            break;
                        }
                    }
                    break;
                case 3:
                    adm.reset();
                    break;
                case 4:
                    sc.close();
                    loop =false;
                    break;
                default:
                    System.out.println("Enter valid option");
                    break;
            }
        }
    }
}
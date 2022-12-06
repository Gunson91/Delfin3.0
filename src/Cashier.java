import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;

public class Cashier { //PairProgramming

    //TODO opdater betalingsstaus ved nyt år

    Ui ui = new Ui();
    Filehandler f = new Filehandler();
    ArrayList<Member> allMembersList = f.getAllMembersList();

    public void cashierMenu() {
        String[] cashierMenuChoices = {"1. View missing payments", "2. View payment status from ID",
                "3. Register Payment", "9. Exit program"};
        Menu cashierMenu = new Menu("Welcome back", "Pick the number:", cashierMenuChoices);
        boolean runWhile = true;
        do {
            cashierMenu.printMenu();
            switch (cashierMenu.readChoice()) {
                case 1:
                    viewMissingPayments();
                    break;
                case 2:
                    viewMemberPaymentStatus();
                    break;
                case 3:
                    registerPayment();
                    break;
                case 9:
                    System.out.println("Going back to main menu...");
                    runWhile = false;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        } while (runWhile);
    }

    public void registerPayment() {
        int memberId;
        int price = -1;
        String pChoice = "";
        memberId = ui.readInt("Enter member ID: ");
        price = generatePrice(memberId);
        ui.println("This member has to pay: " + price + " DKK");

        pChoice = ui.readString("Confirm payment for member: " + memberId + "\n Enter true or false: ");

        if (pChoice.equalsIgnoreCase("true")) {
            f.editMember(memberId, 9, "true");

        } else if (pChoice.equalsIgnoreCase("false")) {
            f.editMember(memberId, 9, "false");
        }else{
            System.out.println("Something went wrong in registerPayment()");
        }
    }

    public int generatePrice(int iD) {
        int birthYear = -1;
        boolean isActive = false;


        for (int i = 0; i < allMembersList.size(); i++) {
            if (allMembersList.get(i).getMemberID() == iD) {
                birthYear = allMembersList.get(i).getBirthYear();
                isActive = allMembersList.get(i).isActive();
            }
        }

        if (!isActive) {
            return 500;
        }
        // <18 = 1000kr
        if (LocalDateTime.now().getYear() - birthYear < 18) {
            return 1000;
            // >60 = 1200kr
        } else if (LocalDateTime.now().getYear() - birthYear > 60) {
            return (int) (1600 * 0.75);
        } else {
            return 1600;
        }
    }

    public void viewMissingPayments() {
        ui.println("ALL MEMBERS WITH MISSING PAYMENTS: ");
        for (int i = 0; i < allMembersList.size(); i++) {
            if (!allMembersList.get(i).hasPaid()) {
                System.out.println("ID: " + allMembersList.get(i).getMemberID() + ", Last name: "
                        + allMembersList.get(i).getlName()+", Amount: "
                        + generatePrice(allMembersList.get(i).getMemberID())+" DKK" );
            }
        }
    }

    public void viewMemberPaymentStatus(){
        int memberId;
        int price = -1;
        String pChoice = "";
        memberId = ui.readInt("Enter member ID: ");
        price = generatePrice(memberId);

        if(!allMembersList.get(memberId).hasPaid()){
            ui.println("This member has to pay: " + price + " DKK");
        }else{
            ui.println("This member has paid: " + price + " DKK");
        }

    }


    /* Payment information
        ungdoms svømmere (<18 år, 1000kr. årligt)
        seniorsvømmere (>18år, 1600kr årligt)
        over 60 (>=60 1600kr, * 0.75)
        passiv (alle, 500kr)
     */
}

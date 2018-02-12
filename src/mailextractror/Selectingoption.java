/*
this class will perform is the Choosed option of user whether it archive or reply.
if archive then show a dialogue box for warning. if yes the archive message;
if Reply then go to ComposeStage

*/

package mailextractror;

import java.util.Stack;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javax.mail.MessagingException;
import mailextractror.TableMaker.Person;

public class Selectingoption {

    private String reply = "Reply";
    private String archive = "Archive";
    TableMaker demo;
    TableView table;
    public static Stack stk = new Stack();
    Stack stk2 = new Stack();
    Stack stktem = new Stack();
    Stack tempstk = new Stack();

    static boolean pppp = false;

    public void Selection(String Choice) throws MessagingException {
        demo = new TableMaker();
        int n = demo.ans.length;
        Stage wind = new Stage();

        if (Choice == "Archive") {
//Archive mode
            int k = 0;
            for (int i = 0; i < TableMaker.ans.length; i++) {
                if (TableMaker.ans[i] == 1) {
                    k++;
                    break;
                }
            }
            if (k == 0) {
                
                wind.setTitle(Choice + "ing Failure");
                Label tt = new Label(" No Message is Selected!");
                BorderPane pb = new BorderPane(tt, null, null, null, null);
                Scene sn = new Scene(pb, 300, 200);
                wind.setScene(sn);
                wind.showAndWait();

            } else {
//showing dialogue box to warn user.
                SimpleScene warn = new SimpleScene();
                boolean rslt = warn.display("Archiving Message", "This Action will remove the selected Messages from the App.\nThey are never be shown from next time login\n\n\n"
                        + "We strongly prefer to logout immediately after selecting yes.\n\n\nBut Don't worry you will find them from your main Gmail Account.\n\nDo you really want to Arcive those messages?\n\n");
                if (rslt) {

                    try {
                        stk.clear();
//deleting selected messages from table
                        TableView<Person> table = MailExtractror.recenttable;
                        ObservableList<TableMaker.Person> allProducts;
                        allProducts = (ObservableList<TableMaker.Person>) table.getItems();
                        int x = 0;
                        for (Person p : allProducts) {

                            if (p.invitedProperty().get()) {
                                System.out.println("selected :" + p.numBerProperty().get() + " Subject : " + MailExtractror.messages[MailExtractror.SearchedInex.get(p.numBerProperty().get() - 1)].getSubject()
                                        + "  Boolean: " + p.invitedProperty().get()
                                        + " TableMaker.ans Index: " + TableMaker.ans[p.numBerProperty().get()] + " number : " + MailExtractror.SearchedInex.get(p.numBerProperty().get() - 1));

                                stk.push(MailExtractror.SearchedInex.get(p.numBerProperty().get() - 1));
                                tempstk.push(p.numBerProperty().get());

                                stk2.push(x);

                            }
                            x++;
                        }
                        System.out.println("done from serach option");
                        while (!stk2.empty()) {
                            int y = (int) stk2.peek();
                            int z = (int) tempstk.peek();

                            allProducts.remove(y);

                            TableMaker.ans[z] = -1;
                            stk2.pop();
                            tempstk.pop();

                        }

                    } catch (Exception er) {

                        System.out.println("Done finally from searched option");
                    }
                }
            }

        } else if (Choice == "Reply") {
//Reply mode
            int k = 0;
            for (int i = 0; i < TableMaker.ans.length; i++) {
                if (TableMaker.ans[i] == 1) {
                    k++;
                    break;
                }
            }
            if (k == 0) {
                wind.setTitle(Choice + "ing Failure");
                Label tt = new Label(" No recipient is Selected!");
                BorderPane pb = new BorderPane(tt, null, null, null, null);
                Scene sn = new Scene(pb, 300, 200);
                wind.setScene(sn);
                wind.showAndWait();

            } else {
//going to composeStage to type message and add attachment.
                ComposeStage comps = new ComposeStage();
                comps.displaytab();
            }

        }

    }

}

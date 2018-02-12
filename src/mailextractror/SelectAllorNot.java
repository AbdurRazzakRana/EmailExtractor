/*
this class will handle the instruction of user wheather he choose all message or none from
the found messages of searched parameter


*/
package mailextractror;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class SelectAllorNot {

    private ObservableList<TableMaker.Person> allProducts;
    private TableView<TableMaker.Person> table;

    public SelectAllorNot(TableView<TableMaker.Person> table) {
        this.table = table;
        allProducts = (ObservableList<TableMaker.Person>) table.getItems();
    }

    public void selectedtoall(boolean g) {
// setting a particular the boolean values to all
        for (TableMaker.Person p : allProducts) {
            if (p.invitedProperty().get() != g) {
                p.invitedProperty().set(g);
                int v = p.numBerProperty().get();
                if (g) {
                    TableMaker.ans[v] = 1;
                } else if (!g) {
                    TableMaker.ans[v] = 0;
                } else {
                    TableMaker.ans[v] = -1;
                }
            }

        }
    }

}

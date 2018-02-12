/*
This class will generate a table of found messages of searched perameters,
selecting a chackbox from this table means that selecting the main message from inbox 
*/

package mailextractror;

import java.util.ArrayList;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

import javafx.scene.control.TableView;

import javafx.scene.control.cell.PropertyValueFactory;

import javafx.util.Callback;

public class TableMaker {

    public static int[] ans;

    ArrayList<CheckBox> cheakboxarray = new ArrayList<>();
    ObservableList<Person> data;

    public TableView Make(ArrayList<String> tablearray, int k) {

        ans = new int[k + 1];

//Serial column
        TableColumn numberCol = new TableColumn();

        numberCol.setText("Serial");

        numberCol.setCellValueFactory(new PropertyValueFactory("numBer"));
        numberCol.setMaxWidth(60);
//Select column
        TableColumn invitedCol = new TableColumn<Person, Boolean>();

        invitedCol.setText("Select");

        invitedCol.setMaxWidth(60);

        invitedCol.setCellValueFactory(new PropertyValueFactory("invited"));

        invitedCol.setCellFactory(new Callback<TableColumn<Person, Boolean>, TableCell<Person, Boolean>>() {

            public TableCell<Person, Boolean> call(TableColumn<Person, Boolean> p) {

                return new CheckBoxTableCell<Person, Boolean>();
            }
        });

//Subject column
        TableColumn firstNameCol = new TableColumn();

        firstNameCol.setText("Subject");
        firstNameCol.setMinWidth(250);

        firstNameCol.setCellValueFactory(new PropertyValueFactory("firstName"));

//From ID column
        TableColumn lastNameCol = new TableColumn();

        lastNameCol.setText("From ID");
        lastNameCol.setMinWidth(250);

        lastNameCol.setCellValueFactory(new PropertyValueFactory("lastName"));

//Attachment column
        TableColumn emailCol = new TableColumn();

        emailCol.setText("Attachment");

        emailCol.setMaxWidth(100);

        emailCol.setCellValueFactory(new PropertyValueFactory("email"));

        TableView tableView = new TableView();

        //tableView.setItems(data);
      
        
        
        
        
        tableView.setItems(getProduct(tablearray, k));

        tableView.getColumns().addAll(numberCol, invitedCol, firstNameCol, lastNameCol, emailCol);

        return tableView;
    }

    public TableView Make() {


        TableColumn numberCol = new TableColumn();

        numberCol.setText("Serial");

        numberCol.setCellValueFactory(new PropertyValueFactory("numBer"));
        numberCol.setMaxWidth(60);

        TableColumn invitedCol = new TableColumn<Person, Boolean>();

        invitedCol.setText("Select");

        invitedCol.setMaxWidth(60);

        invitedCol.setCellValueFactory(new PropertyValueFactory("invited"));

        invitedCol.setCellFactory(new Callback<TableColumn<Person, Boolean>, TableCell<Person, Boolean>>() {

            public TableCell<Person, Boolean> call(TableColumn<Person, Boolean> p) {

                return new CheckBoxTableCell<Person, Boolean>();
            }
        });

        TableColumn firstNameCol = new TableColumn();

        firstNameCol.setText("Subject");
        firstNameCol.setMinWidth(250);

        firstNameCol.setCellValueFactory(new PropertyValueFactory("firstName"));

        TableColumn lastNameCol = new TableColumn();

        lastNameCol.setText("From ID");
        lastNameCol.setMinWidth(250);

        lastNameCol.setCellValueFactory(new PropertyValueFactory("lastName"));

        TableColumn emailCol = new TableColumn();

        emailCol.setText("Attachment");

        emailCol.setMaxWidth(100);

        emailCol.setCellValueFactory(new PropertyValueFactory("email"));

        TableView tableView = new TableView();


        tableView.setItems(getProduct());

        tableView.getColumns().addAll(numberCol, invitedCol, firstNameCol, lastNameCol, emailCol);

        return tableView;
    }


    public static class Person {

        private BooleanProperty invited;

        private StringProperty firstName;

        private StringProperty lastName;

        private StringProperty email;

        private IntegerProperty numBer;

        public Person(int number, boolean invited, String fName, String lName, String email) {

            this.invited = new SimpleBooleanProperty(invited);

            this.firstName = new SimpleStringProperty(fName);

            this.lastName = new SimpleStringProperty(lName);

            this.email = new SimpleStringProperty(email);

            this.invited = new SimpleBooleanProperty(invited);

            this.numBer = new SimpleIntegerProperty(number);

            this.invited.addListener(new ChangeListener<Boolean>() {

                public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {

                    int foo;
                    foo = numBerProperty().getValue();
                    int fn = foo - 1;

                    if (t1.booleanValue()) {

                        ans[foo] = 1;
                        
                        System.out.println(foo + " " + ans[foo]);
                        
                    } else {
                        ans[foo] = 0;
                        
                        System.out.println(foo + " " + ans[foo]);
                       
                    }
                 
                }

            });

        }

        Person() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        public BooleanProperty invitedProperty() {
            return invited;
        }

        public StringProperty firstNameProperty() {
            return firstName;
        }

        public StringProperty lastNameProperty() {
            return lastName;
        }

        public StringProperty emailProperty() {
            return email;
        }

        public IntegerProperty numBerProperty() {
            return numBer;
        }

        public void setLastName(String lastName) {
            this.lastName.set(lastName);
        }

        public void setFirstName(String firstName) {
            this.firstName.set(firstName);
        }

        public void setEmail(String email) {
            this.email.set(email);
        }

        public void setNumber(int numBer) {
            this.numBer.set(numBer);
        }
    }

    public static class CheckBoxTableCell<S, T> extends TableCell<S, T> {

        private final CheckBox checkBox;

        private ObservableValue<T> ov;

        public CheckBoxTableCell() {

            this.checkBox = new CheckBox();

            this.checkBox.setAlignment(Pos.CENTER);

            setAlignment(Pos.CENTER);

            setGraphic(checkBox);

        }

        @Override
        public void updateItem(T item, boolean empty) {

            super.updateItem(item, empty);

            if (empty) {

                setText(null);

                setGraphic(null);

            } else {

                setGraphic(checkBox);

                if (ov instanceof BooleanProperty) {

                    checkBox.selectedProperty().unbindBidirectional((BooleanProperty) ov);

                }

                ov = getTableColumn().getCellObservableValue(getIndex());

                if (ov instanceof BooleanProperty) {

                    checkBox.selectedProperty().bindBidirectional((BooleanProperty) ov);
                }
            }
        }
    }

    public ObservableList<Person> getProduct(ArrayList<String> tablearray, int k) {
        data = FXCollections.observableArrayList();
        int j = 0;
        for (int i = 0; i < (k * 3) - 1; i += 3) {
            data.add(new Person(j + 1, false, tablearray.get(i), tablearray.get(i + 1), tablearray.get(i + 2)));
            j++;
        }
        return data;
    }

    public ObservableList<Person> getProduct() {
        ObservableList<Person> data;
        data = FXCollections.observableArrayList();

        return data;
    }

}

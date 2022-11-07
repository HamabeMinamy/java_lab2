import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

public class BookGUI extends JFrame {
    MyLinkedList<Book> list= new MyLinkedList<>();
    JTextField isbn  = new JTextField(20);
    JTextField title = new JTextField(20);
    JButton addButton = new JButton("Add");
    JButton edit = new JButton("Edit");
    JButton save = new JButton("Save");
    JButton delete = new JButton("delete");
    JButton search = new JButton("Search");
    JButton more = new JButton("More>>");
    JButton load = new JButton("Load Test Data");
    JButton displayAll = new JButton("Display All");
    JButton displayAllByIsbn = new JButton("Display All by ISBN");
    JButton displayAllByTitle = new JButton("Display All by Title");
    JButton exit = new JButton("Exit");
    JButton[] allButton = {save,addButton,edit,delete,search,more,
            load,displayAll,displayAllByIsbn,displayAllByTitle,exit};
    String[] column = {"ISBN", "Title", "Available"};
    DefaultTableModel model = new DefaultTableModel(column, 0);
    Listener allListener = new Listener();
    JTable table = new JTable(model);
    boolean isAscendingByISBN = true;
    boolean isAscendingByTitle = true;




    public BookGUI() {
        Date date = new Date();
        //JTextArea
        JTextArea area = new JTextArea("Student Name and ID: LIU Tao Tao(20084489d)\n" +
                "Student Name and ID: XueZi Ning()\n" +
                date);
        //second part:
        ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER); // align header to center
        table.getSelectionModel().addListSelectionListener(allListener);
        JScrollPane p1 = new JScrollPane(table);


        //third part:
        JPanel body= new JPanel(new GridLayout(3, 1));//third part's whole panel
        //top
        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER));
        top.add(new JLabel("ISBN:"));
        top.add(isbn);
        top.add(new JLabel("Title:"));
        top.add(title);

        //center
        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addButton.addActionListener(allListener);
        edit.addActionListener(allListener);
        save.setEnabled(false);
        save.addActionListener(allListener);
        delete.addActionListener(allListener);
        search.addActionListener(allListener);
        center.add(addButton);
        center.add(edit);
        center.add(save);
        center.add(delete);
        center.add(search);
        center.add(more);

        //bottom
        displayAll.addActionListener(allListener);
        displayAllByIsbn.addActionListener(allListener);
        displayAllByTitle.addActionListener(allListener);
        exit.addActionListener(allListener);
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        load.addActionListener(allListener);
        bottom.add(load);
        bottom.add(displayAll);
        bottom.add(displayAllByIsbn);
        bottom.add(displayAllByTitle);
        bottom.add(exit);

        //add all to whole body
        body.add(top);
        body.add(center);
        body.add(bottom);

        setLayout(new GridLayout(3, 1));
        add(area);
        add(p1);
        add(body);

    }
    class Listener implements ActionListener, ListSelectionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addButton) {
                String ISBN = isbn.getText();
                String inputTitle = title.getText();
                System.out.println(ISBN + "No" + inputTitle);
                if (!ISBN.equals("") && !inputTitle.equals("")) {
                    for (Book current : list) {
                        if (current.getISBN().equals(ISBN)) {
                            JOptionPane.showMessageDialog
                                    (null, "Book ISBN exists in the current database");
                            return;
                        }
                    }
                    addNewBook(ISBN, inputTitle);
                    showData();
                } else {
                    JOptionPane.showMessageDialog
                            (null, "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (e.getSource() == load) {
                String[] listOfISBN = {"0131450913", "0131857576", "0132222205"};
                String[] listOfTitle = {"HTML How to Program", "C++ How to Program", "Java How to Program"};
                for (int i = 0; i < 3; i++) {
                    boolean uniqueBook = true;
                    String ISBN = listOfISBN[i];
                    String testTitle = listOfTitle[i];
                    for (Book current : list) {
                        if (current.getISBN().equals(ISBN)) {
                            uniqueBook = false;
                            JOptionPane.showMessageDialog
                                    (null, "Book ISBN exists in the current database");

                        }

                    }
                    if (uniqueBook) {
                        addNewBook(ISBN, testTitle);
                        showData();
                    }

                }

            } else if (e.getSource() == edit) {
                String ISBN = isbn.getText();
                boolean hasBook = false;
                if(!ISBN.equals("")) {
                    for (Book current : list) {
                        if (current.getISBN().equals(ISBN)) {
                            title.setText(current.getTitle());
                            hasBook = true;
                            break;
                        }

                    }
                }
                if(!hasBook){
                    JOptionPane.showMessageDialog
                            (null, "Book ISBN has not existed in the current database");
                    return;

                }
                switchButton();
            }
            else if (e.getSource() == save) {
                int row = table.getSelectedRow();
                String ISBN = isbn.getText();
                String inputTitle = title.getText();

                    if (!ISBN.equals("") && !inputTitle.equals("")) {
                        if (row != -1) {
                            String selectedISBN = (String) model.getValueAt(row, 0);
                            for (Book current : list) {
                                if (current.getISBN().equals(ISBN)) {
                                    JOptionPane.showMessageDialog
                                            (null, "Book ISBN exists in the current database");
                                    return;
                                } else if (selectedISBN.equals(current.getISBN())) {
                                    current.setISBN(ISBN);
                                    current.setTitle(inputTitle);
                                    showData();
                                    switchButton();
                                }
                            }
                        }
                        else{
                            for (Book current : list) {
                                if (current.getISBN().equals(ISBN)) {
                                    current.setTitle(inputTitle);
                                    showData();
                                    switchButton();
                                }


                            }

                        }
                    }
                    else {
                        JOptionPane.showMessageDialog
                                (null, "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
                    }



            }
            else if(e.getSource() == delete){
                String ISBN = isbn.getText();
                boolean hasBook = false;
                if(!ISBN.equals("")) {
                    for (Book current : list) {
                        if (current.getISBN().equals(ISBN)) {
                            list.remove(current);
                            hasBook = true;
                            showData();
                            isbn.setText(null);
                            isbn.setText(null);
                            break;
                        }

                    }
                }
                if(!hasBook){
                    JOptionPane.showMessageDialog
                            (null, "Book ISBN has not existed in the current database");
                }

            }
            else if (e.getSource() == search){
                String ISBN = isbn.getText();
                String inputTitle = title.getText();
                if(!ISBN.equals("") && !inputTitle.equals("")) {
                    showSelectedData(ISBN,inputTitle);
                    isbn.setText(null);
                    title.setText(null);
                }
            } else if (e.getSource() == displayAll) {
                showData();
            }
            else if(e.getSource() == displayAllByIsbn){
                isAscendingByISBN = showSortedDataByISBN(isAscendingByISBN);

            }
            else if (e.getSource() == displayAllByTitle){
                isAscendingByTitle = showSortedDataByISBN(isAscendingByTitle);
            }
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            int row = table.getSelectedRow();
            if (row != -1) {
                String selectedISBN = (String) model.getValueAt(row, 0);
                String selectedTitle = (String) model.getValueAt(row, 1);
                isbn.setText(selectedISBN);
                title.setText(selectedTitle);
            }
        }
    }

    private void showData() {
        model.setRowCount(0);
        Object[] row = new Object[3];
        for (Book book :
                list) {
            row[0] = book.getISBN();
            row[1] = book.getTitle();
            row[2]= book.isAvailable();
            model.addRow(row);

        }
    }
    private boolean showSortedDataByISBN(boolean isAscending){
        model.setRowCount(0);
        Object[] row = new Object[3];
        Book[] books = new Book[list.size()];
        books = list.toArray(books);
        if (isAscending)
            Arrays.sort(books, Comparator.comparing(Book::getISBN));
        else
            Arrays.sort(books, Comparator.comparing(Book::getISBN).reversed());
        for (Book book :
                books) {
            row[0] = book.getISBN();
            row[1] = book.getTitle();
            row[2]= book.isAvailable();
            model.addRow(row);

        }
        return !isAscending;
    }

    private void showSelectedData(String ISBN, String inputTitle){
        model.setRowCount(0);
        Object[] row = new Object[3];
        for (Book current : list) {
            if (current.getISBN().contains(ISBN) && current.getTitle().contains(inputTitle)) {
                row[0] = current.getISBN();
                row[1] = current.getTitle();
                row[2]= current.isAvailable();
                model.addRow(row);
            }

        }

    }

    private void addNewBook(String ISBN, String inputTitle) {
        Book newBook = new Book();
        newBook.setISBN(ISBN);
        newBook.setTitle(inputTitle);
        list.add(newBook);
        isbn.setText(null);
        title.setText(null);
        model.fireTableDataChanged();
    }
    private void switchButton(){
        for (JButton button :
                allButton) {
            button.setEnabled(!button.isEnabled());
        }
    }

    public static void main(String[] args){
        BookGUI frame = new BookGUI();
        frame.setTitle("Library Admin System");
        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
public class BookGUI extends JFrame implements ActionListener, ListSelectionListener{
    private final MyLinkedList<Book> list = new MyLinkedList<>();
    private final JTextField index = new JTextField(4);
    private final JTextField isbn = new JTextField(20);
    private final JTextField title = new JTextField(20);
    private final JButton addButton = new JButton("Add");
    private final JButton edit = new JButton("Edit");
    private final JButton save = new JButton("Save");
    private final JButton delete = new JButton("delete");
    private final JButton search = new JButton("Search");
    private final JButton more = new JButton("More>>");
    private final JButton load = new JButton("Load Test Data");
    private final JButton displayAll = new JButton("Display All");
    private final JButton displayAllByIsbn = new JButton("Display All by ISBN");
    private final JButton displayAllByTitle = new JButton("Display All by Title");
    private final JButton exit = new JButton("Exit");
    private final JButton borrow = new JButton("Borrow");
    private final JButton buttonReturn = new JButton("Return");
    private final JButton reserve = new JButton("Reserve");
    private final JButton waitingQueue = new JButton("Waiting Queue");
    private final JButton[] allButton = {save, addButton, edit, delete, search, more,
        load, displayAll, displayAllByIsbn, displayAllByTitle, exit};
    private final JButton[] allDialogButton = {borrow,buttonReturn,reserve,waitingQueue};
    private final String[] column = {"ISBN", "Title", "Available"};
    private final DefaultTableModel model = new DefaultTableModel(column, 0);
    private final JTable table = new JTable(model);
    private boolean isAscendingByISBN = true;
    private boolean isAscendingByTitle = true;
    private final JDialog dialog = new JDialog(this);
    private final JTextArea dialogArea = new JTextArea(4,1);
    private final JTextArea area2 = new JTextArea(1,1);
    private final JPanel panel = new JPanel(new FlowLayout());


    public BookGUI() {
        //access the database:

        Date date = new Date();
        //JTextArea
        JTextArea area = new JTextArea("Student Name and ID: LIU Tao Tao(20084489d)\n" +
                "Student Name and ID: XueZi Ning()\n" +
                date);
        //second part:
        ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER); // align header to center
        table.getSelectionModel().addListSelectionListener(this);
        JScrollPane p1 = new JScrollPane(table);


        //third part:
        JPanel body = new JPanel(new GridLayout(3, 1));//third part's whole panel
        //top
        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER));
        top.add(new JLabel("Index:"));
        top.add(index);
        top.add(new JLabel("ISBN:"));
        top.add(isbn);
        top.add(new JLabel("Title:"));
        top.add(title);

        //center
        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addButton.addActionListener(this);
        edit.addActionListener(this);
        save.setEnabled(false);
        save.addActionListener(this);
        delete.addActionListener(this);
        search.addActionListener(this);
        more.addActionListener(this);
        center.add(addButton);
        center.add(edit);
        center.add(save);
        center.add(delete);
        center.add(search);
        center.add(more);

        //bottom
        displayAll.addActionListener(this);
        displayAllByIsbn.addActionListener(this);
        displayAllByTitle.addActionListener(this);
        exit.addActionListener(this);
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        load.addActionListener(this);
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
        setTitle("Library Admin System");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        //JDialog
        dialog.setLayout(new BorderLayout());
        borrow.setEnabled(false);
        switchButton(allDialogButton);
        borrow.addActionListener(this);
        buttonReturn.addActionListener(this);
        reserve.addActionListener(this);
        waitingQueue.addActionListener(this);
        panel.add(borrow);
        panel.add(buttonReturn);
        panel.add(reserve);
        panel.add(waitingQueue);
        dialog.add(dialogArea,BorderLayout.NORTH);
        dialog.add(panel,BorderLayout.CENTER);
        dialog.add(area2,BorderLayout.SOUTH);
        dialog.setVisible(false);
    }

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
                writeTxt();
            } else {
                JOptionPane.showMessageDialog
                        (null, "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (e.getSource() == load) {
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
                    writeTxt();
                }

            }

        }
        else if (e.getSource() == edit) {
            String ISBN = isbn.getText();
            boolean hasBook = false;
            if (!ISBN.equals("")) {
                for (Book current : list) {
                    if (current.getISBN().equals(ISBN)) {
                        title.setText(current.getTitle());
                        hasBook = true;
                        break;
                    }

                }
            }
            if (!hasBook) {
                JOptionPane.showMessageDialog
                        (null, "Book ISBN has not existed in the current database");
                return;

            }
            switchButton(allButton);
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
                            writeTxt();
                            switchButton(allButton);
                        }
                    }
                } else {
                    for (Book current : list) {
                        if (current.getISBN().equals(ISBN)) {
                            current.setTitle(inputTitle);
                            showData();
                            writeTxt();
                            switchButton(allButton);

                        }


                    }

                }
            } else {
                JOptionPane.showMessageDialog
                        (null, "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
            }


        }
        else if (e.getSource() == delete) {
            String ISBN = isbn.getText();
            boolean hasBook = false;
            if (!ISBN.equals("")) {
                for (Book current : list) {
                    if (current.getISBN().equals(ISBN)) {
                        list.remove(current);
                        hasBook = true;
                        showData();
                        writeTxt();
                        isbn.setText(null);
                        isbn.setText(null);
                        break;
                    }

                }
            }
            if (!hasBook) {
                JOptionPane.showMessageDialog
                        (null, "Book ISBN has not existed in the current database");
            }

        }
        else if (e.getSource() == search) {
            String ISBN = isbn.getText();
            String inputTitle = title.getText();
            if (!ISBN.equals("") && !inputTitle.equals("")) {
                showSelectedData(ISBN, inputTitle);
            }
            else if(!index.getText().equals("")){
                retrieveFromTxt(index.getText());
            }
            index.setText(null);
            isbn.setText(null);
            title.setText(null);
        }
        else if (e.getSource() == displayAll) {
            showData();
        }
        else if (e.getSource() == displayAllByIsbn) {
            isAscendingByISBN = showSortedDataByISBN(isAscendingByISBN);

        }
        else if (e.getSource() == displayAllByTitle) {
            isAscendingByTitle = showSortedDataByISBN(isAscendingByTitle);
        }
        else if (e.getSource() == more) {
            dialog.setVisible(true);
            String ISBN = isbn.getText();
            if (!ISBN.equals("")) {
                for (Book book :
                        list) {
                    if (book.getISBN().equals(ISBN)) {
                        dialogArea.setText("ISBN: " +
                                book.getISBN() + "\nTitle: " +
                                book.getTitle() + "\nAvailable: " +
                                book.isAvailable());




                    }
                }

            }
        }
        else if (e.getSource() == borrow){
            switchButton(allDialogButton);
            for (Book book :
                    list) {
                if(book.getISBN().equals(isbn.getText())){
                    book.setAvailable(false);
                    dialogArea.setText("ISBN: " +
                            book.getISBN() + "\nTitle: " +
                            book.getTitle() + "\nAvailable: " +
                            book.isAvailable());
                }

            }
            area2.setText("The book is borrowed.");
        }
        else if (e.getSource() == buttonReturn){
            for (Book book :
                    list) {
                if(book.getISBN().equals(isbn.getText())){
                    if(book.getReservedQueue().getSize() == 0){
                        switchButton(allDialogButton);
                        book.setAvailable(true);
                        area2.setText("The book is returned.");
                    }
                    else{
                        area2.setText("The book is returned.\n" +
                                "The book is now borrowed by " +
                                book.getReservedQueue().getList().getFirst());
                    }
                    book.getReservedQueue().dequeue();
                    dialogArea.setText("ISBN: " +
                            book.getISBN() + "\nTitle: " +
                            book.getTitle() + "\nAvailable: " +
                            book.isAvailable());
                }

            }


        }
        else if (e.getSource() == reserve){
            for(Book book:list){
                if(book.getISBN().equals(isbn.getText())){
                    String input = JOptionPane.showInputDialog(null,"What's your name?",
                            "Input");
                    book.getReservedQueue().enqueue(input);
                    area2.setText("The book is reserved by " +
                            input+ ".");
                }
            }
        }
        else if (e.getSource() == waitingQueue){
            for (Book book :
                    list) {
                if (book.getISBN().equals(isbn.getText())) {
                    StringBuilder output = new StringBuilder("The waiting list:\n");
                    for (String name :
                            book.getReservedQueue().getList()) {
                        output.append(name);
                        output.append("\n");

                    }
                    area2.setText(output.toString());
                }
                }
        }
        else if (e.getSource() == exit){
            System.exit(0);
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

    private void showData() {
        model.setRowCount(0);
        Object[] row = new Object[3];
        for (Book book :
                list) {
            row[0] = book.getISBN();
            row[1] = book.getTitle();
            row[2] = book.isAvailable();
            model.addRow(row);

        }
    }

    private boolean showSortedDataByISBN(boolean isAscending) {
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
            row[2] = book.isAvailable();
            model.addRow(row);

        }
        return !isAscending;
    }

    private void showSelectedData(String ISBN, String inputTitle) {
        model.setRowCount(0);
        Object[] row = new Object[3];
        for (Book current : list) {
            if (current.getISBN().contains(ISBN) && current.getTitle().contains(inputTitle)) {
                row[0] = current.getISBN();
                row[1] = current.getTitle();
                row[2] = current.isAvailable();
                model.addRow(row);
            }

        }

    }

    private void addNewBook(String ISBN, String inputTitle) {
        Book newBook = new Book();
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon("img.png"));
        panel.setBounds(70, 75, 600, 450);
        panel.add(label);
        newBook.setISBN(ISBN);
        newBook.setTitle(inputTitle);
        list.add(newBook);
        isbn.setText(null);
        title.setText(null);
        model.fireTableDataChanged();

    }

    private void switchButton(JButton[] allButton) {
        for (JButton button :
                allButton) {
            button.setEnabled(!button.isEnabled());
        }
    }
    private void writeTxt() {
        try{
            PrintWriter outputStream = new PrintWriter(new FileWriter("databaseManagement.txt"));
            int index =0;

            for (Book book :
                    list) {
                index++;
                outputStream.write(index + "\n");
                outputStream.write(book.getISBN() +"\n");
                outputStream.write(book.getTitle() + "\n");
                if (book.isAvailable())
                    outputStream.write("Yes\n");
                else
                    outputStream.write("No\n");
            }

            if (outputStream != null){
                outputStream.close();
            }

        }
        catch (Exception e){
        }

    }
    private void retrieveFromTxt(String input){
        try{
            BufferedReader inputStream = new BufferedReader(new FileReader("databaseManagement.txt"));
            String l;
            while ((l=inputStream.readLine())!= null){
                if (l.equals(input)){
                    String ISBN = inputStream.readLine();
                    String title = inputStream.readLine();
                    showSelectedData(ISBN,title);
                }
            }
        }
        catch (Exception e){

        }

    }

    public static void main(String[] args) {
        BookGUI frame = new BookGUI();

    }

}

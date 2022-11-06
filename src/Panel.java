import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Iterator;

public class Panel extends JFrame {
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
    String[] column = {"ISBN", "Title", "Available"};
    DefaultTableModel model = new DefaultTableModel(column, 0);
    Listener allListener = new Listener();
    JTable table = new JTable(model);



    public Panel() {
        Date date = new Date();
        //JTextArea
        JTextArea area = new JTextArea("Student Name and ID: LIU Tao Tao(20084489d)\n" +
                "Student Name and ID: XueZi Ning()\n" +
                date);
        //second part:
        ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER); // align header to center
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
        center.add(addButton);
        center.add(edit);
        center.add(save);
        center.add(delete);
        center.add(search);
        center.add(more);

        //bottom
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
    class Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == addButton){
                String ISBN = isbn.getText();
                String inputTitle = title.getText();
                System.out.println(ISBN + "No" + inputTitle);
                if (!ISBN.equals("")  && !inputTitle.equals("") ) {
                    for (Book current : list) {
                        if (current.getISBN().equals(ISBN)) {
                            JOptionPane.showMessageDialog
                                    (null, "The list already have this book");
                            return;
                        }
                    }
                    addNewBook(ISBN, inputTitle);
                }
                else{
                    JOptionPane.showMessageDialog
                            (null,"Invalid input","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
            else if (e.getSource() == load) {
                String[] listOfISBN = {"0131450913","0131857576","0132222205"};
                String[] listOfTitle = {"HTML How to Program","C++ How to Program","Java How to Program"};
                for (int i = 0; i < 3; i++) {
                    boolean uniqueBook = true;
                    String ISBN = listOfISBN[i];
                    String testTitle = listOfTitle[i];
                    for (Book current : list) {
                        if (current.getISBN().equals(ISBN)) {
                            uniqueBook = false;
                            JOptionPane.showMessageDialog
                                    (null, "The list already have this book");

                        }

                    }
                    if (uniqueBook) {
                        addNewBook(ISBN, testTitle);
                    }

                }

            }
            else if (e.getSource() == edit){
                int row = table.getSelectedRow();
                String selectedISBN = (String) model.getValueAt(row,0);
                String selectedTitle = (String) model.getValueAt(row,1);
                isbn.setText(selectedISBN);
                title.setText(selectedTitle);

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
        model.addRow((new Object[]{ISBN, inputTitle, newBook.isAvailable()}));
    }

    public static void main(String[] args){
        Panel frame = new Panel();
        frame.setTitle("Library Admin System");
        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.Date;

public class Panel extends JFrame {
    public Panel(){
        Date date = new Date();
        //JTextArea
        JTextArea area = new JTextArea("Student Name and ID: LIU Tao Tao(20084489d)\n" +
                "Student Name and ID: XueZi Ning()\n" +
                date);
        //second part:
        String[] column = {"ISBN", "Title", "Available"};
        DefaultTableModel model = new DefaultTableModel(column,0);
        JTable table = new JTable(model);
        ((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER); // align header to center
        JScrollPane p1 = new JScrollPane(table);

        //third part:
        class Bottom {
            JTextField isbn;
            JTextField title;
            JPanel body;
            Bottom() {
                body = new JPanel(new GridLayout(3, 1));//third part's whole panel
                //top
                JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER));
                isbn = new JTextField(20);
                title = new JTextField(20);
                top.add(new JLabel("ISBN:"));
                top.add(isbn);
                top.add(new JLabel("Title:"));
                top.add(title);

                //center
                JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER));
                JButton addButton = new JButton("Add");
                JButton edit = new JButton("Edit");
                JButton save = new JButton("Save");
                save.setEnabled(false);
                JButton delete = new JButton("delete");
                JButton search = new JButton("Search");
                JButton more = new JButton("More>>");
                center.add(addButton);
                center.add(edit);
                center.add(save);
                center.add(delete);
                center.add(search);
                center.add(more);

                //bottom
                JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
                JButton load = new JButton("Load Test Data");
                JButton displayAll = new JButton("Display All");
                JButton displayAllByIsbn = new JButton("Display All by ISBN");
                JButton displayAllByTitle = new JButton("Display All by Title");
                JButton exit = new JButton("Exit");
                bottom.add(load);
                bottom.add(displayAll);
                bottom.add(displayAllByIsbn);
                bottom.add(displayAllByTitle);
                bottom.add(exit);

                //add all to whole body
                body.add(top);
                body.add(center);
                body.add(bottom);
            }

        }
        setLayout(new GridLayout(3,1));
        Bottom bottom2 = new Bottom();
        add(area);
        add(p1);
        add(bottom2.body);
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

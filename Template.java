import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

class Details implements Comparable<Details>{
    String name, number, email, address;
    Details(String name, String number, String email, String address){
        this.name=name;
        this.number=number;
        this.email=email;
        this.address=address;
    }
    @Override
    public String toString() {
        return number + "," + name + "," + email + "," + address;
    }

    @Override
    public int compareTo(Details o) {
        return name.toLowerCase().compareTo(o.name.toLowerCase());
    }
}

class Template{
    public static void main(String[] args) {
        // designing main frame
        JFrame f = new JFrame("Address Book");
        JLabel heading = new JLabel("Address Book");
        heading.setBounds(180, 0, 100, 30);
        JButton add = new JButton("Add");
        JButton delete = new JButton("Delete");
        JButton all_contacts = new JButton("All Contacts");
        JLabel slabel = new JLabel("Search Here: ");
        JTextField sbar = new JTextField();
        JButton sbutton = new JButton("Search");
        add.setBounds(0, 50, 160, 30);
        delete.setBounds(160, 50, 160, 30);
        all_contacts.setBounds(320, 50, 160, 30);
        slabel.setBounds(25, 200, 250, 40);
        sbar.setBounds(150, 200, 200, 30);
        sbutton.setBounds(150, 250, 200, 30);

        // tree set and file handling initialise
        TreeSet<Details> contacts = new TreeSet<>();
        File file = new File("my_contacts.txt");

        // action listeners for add button
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // setting up labels
                JFrame f = new JFrame("Add Contact");
                f.setLocation(200, 100);
                JLabel name = new JLabel("Enter Name: ");
                JLabel phone_no = new JLabel("Enter Phone no: ");
                JLabel email = new JLabel("Enter email ID:");
                JLabel address = new JLabel("Enter address: ");
                name.setBounds(30, 20, 100, 30);
                phone_no.setBounds(30, 60, 100, 30);
                email.setBounds(30, 100, 100, 30);
                address.setBounds(30, 140, 100, 30);
                f.add(name);
                f.add(phone_no);
                f.add(email);
                f.add(address);

                // setting up text fields
                JTextField n = new JTextField();
                JTextField p = new JTextField();
                JTextField eid = new JTextField();
                JTextField a = new JTextField();
                n.setBounds(130, 20, 170, 30);
                p.setBounds(130, 60, 170, 30);
                eid.setBounds(130, 100, 170, 30);
                a.setBounds(130, 140, 170, 30);
                f.add(n);
                f.add(p);
                f.add(eid);
                f.add(a);

                // creating save button
                JButton save = new JButton("Save");
                save.setBounds(80, 200, 80, 30);
                f.add(save);

                // frame standard setting
                f.setLayout(null);
                f.setSize(400, 300);
                f.setVisible(true);

                // dialog box
                save.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e){
                        if(n.getText().length()==0 || p.getText().length()==0) {
                            JOptionPane.showMessageDialog(f, "Enter name and Phone number", "Add Contact", 0);
                        }
                        else if (p.getText().length()!=10) {
                            JOptionPane.showMessageDialog(f, "Enter 10 Digit number", "Contact", 0);
                        }
                        else{
                            String name = n.getText();
                            String number = p.getText();
                            String email = eid.getText();
                            String address = a.getText();
                            contacts.add(new Details(name, number, email, address));
                            try{
                                // loading previous contacts into tree set
                                loadPersons(contacts);
                                // adding new contacts into tree set
                                FileWriter fw = new FileWriter("my_contacts.txt");
                                PrintWriter pw = new PrintWriter(fw);
                                Details dt;
                                String line;
                                List<Details> list = new ArrayList<>(contacts);
                                for (Details details : list) {
                                    dt = details;
                                    line = dt.name + "," + dt.number + "," + dt.email + "," + dt.address;
                                    pw.println(line);
                                }
                                pw.flush();
                                JOptionPane.showMessageDialog(f, "Successfully added", "Add Contact", 0);
                                pw.close();
                                fw.close();
                            }catch (IOException ex) {
                                ex.printStackTrace();
                            }

                        }
                    }
                });
            }
        });

        // delete button action listener
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame f3 = new JFrame("Delete Contact");
                // f3.getContentPane().setBackground(Color.CYAN);
                f3.setLocation(400, 100);
                JLabel delete = new JLabel("Enter Name or Phone Number to be deleted");
                delete.setBounds(105, 70, 250, 40);
                f3.add(delete);
                delete.setVisible(true);
                JTextField t5 = new JTextField();
                t5.setBounds(100, 120, 150, 30);
                f3.add(t5);
                JButton del = new JButton("Delete");
                f3.add(del);
                del.setBounds(100, 250, 100, 25);
                f3.setLayout(null);
                f3.setVisible(true);
                f3.setSize(400, 400);
                del.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String s = t5.getText();
                        loadPersons(contacts);
                        Iterator<Details> it = contacts.iterator();
                        boolean found=false;
                        while (it.hasNext()){
                            Details c = it.next();
                            if(s.toLowerCase().equals(c.name.toLowerCase())){
                                it.remove();
                                found=true;
                                break;
                            }
                        }
                        if(found) {
                            JOptionPane.showMessageDialog(f, "Successfully deleted.", "DELETE CONTACT", JOptionPane.INFORMATION_MESSAGE);
                            try {
                                FileWriter fw = new FileWriter("my_contacts.txt");
                                PrintWriter pw = new PrintWriter(fw);
                                Details dt;
                                String line;
                                List<Details> list = new ArrayList<>(contacts);
                                for (Details details : list) {
                                    dt = details;
                                    line = dt.name + "," + dt.number + "," + dt.email + "," + dt.address;
                                    pw.println(line);
                                }
                                pw.flush();
                                pw.close();
                                fw.close();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                        else {
                            JOptionPane.showMessageDialog(f, "Contact does not exist.", "DELETE CONTACT", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                });
            }
        });

        // All contacts action listener
        all_contacts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame f = new JFrame("All Contacts");
                f.setLocation(300, 100);
                ImageIcon ii = new ImageIcon("C:\\Users\\Siddharth\\Pictures\\Saved Pictures\\ab_icon.png");

                // adjust size of image icon
                Image img = ii.getImage();
                Image new_ii = img.getScaledInstance(35, 35,  java.awt.Image.SCALE_SMOOTH);
                ii = new ImageIcon(new_ii);
                JLabel l = new JLabel("List of all contacts", ii, SwingConstants.CENTER);
                // Loading contents of file into table
                loadPersons(contacts);
                List<Details> al = new ArrayList<>(contacts);
                String[][] data = new String[al.size()][4];
                try{
                    String[] tokens = null;
                    String nam, ph, em, ad;
                    FileReader fr = new FileReader("my_contacts.txt");
                    BufferedReader br = new BufferedReader(fr);
                    String line = br.readLine();
                    int x=0;
                    while(line!=null){
                        tokens = line.split(",");
                        data[x][0] = tokens[0];
                        data[x][1] = tokens[1];
                        data[x][2] = tokens[2];
                        data[x][3] = tokens[3];
                        x += 1;
                        line = br.readLine();
                    }
                }catch (Exception ex){
                    ex.getStackTrace();
                }
                String[] colname = {"Name", "Phone No.", "Email ID", "Address"};
                DefaultTableModel model = new DefaultTableModel(data, colname){
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        //all cells false
                        return false;
                    }
                };
                JTable table = new JTable(model);

                f.add(l);
                f.add(new JScrollPane(table));

                // frame standard setting
                f.setLayout(new FlowLayout());
                f.setSize(600, 600);
                f.setVisible(true);
                f.show();

            }
        });
        // search button action listener
        sbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String s = sbar.getText();
                    loadPersons(contacts);
                    Iterator<Details> it = contacts.iterator();
                    boolean found=false;
                    while (it.hasNext()){
                        Details c = it.next();
                        if(c.name.equalsIgnoreCase(s)) {
                            String message = "Name : "+ c.name+"\nPh no. : " + c.number+ "\nEmail : " + c.email+"\nAddress : " + c.address;
                            JOptionPane.showMessageDialog(f, message, "Contact", 1);
                            found = true;
                            break;
                        }
                    }
                    if(!found) {
                        JOptionPane.showMessageDialog(f, "Contact Does not Exist...!", "Contact", 0);
                    }
                } catch(Exception ex){
                    ex.getStackTrace();
                }
            }
        });

        // adding to frame 1
        f.add(add);
        f.add(delete);
        f.add(all_contacts);
        f.add(heading);
        f.add(slabel);
        f.add(sbar);
        f.add(sbutton);

        //frame settings
        f.setLayout(null);
        f.setSize(500, 500);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    static void loadPersons(TreeSet<Details> contacts){
        String[] tokens = null;
        String nam, ph, em, ad;
        try {
            FileReader fr = new FileReader("my_contacts.txt");
            BufferedReader br = new BufferedReader(fr);
            String l = br.readLine();
            while (l != null) {
                tokens = l.split(",");
                nam = tokens[0];
                ph = tokens[1];
                em = tokens[2];
                ad = tokens[3];
                Details d = new Details(nam, ph, em, ad);
                contacts.add(d);
                l = br.readLine();
            }
            fr.close();
            br.close();
        } catch (Exception ex){
            ex.getStackTrace();
        }
    }
}

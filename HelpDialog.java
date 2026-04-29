import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class HelpDialog extends JDialog implements ActionListener {
    public HelpDialog(JFrame parent, String name, String help, String pictureName){
        super(parent, name, true);
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        JButton ok = new JButton("OK");
        ok.addActionListener(this);
        cp.add(ok, BorderLayout.SOUTH);

        ImageIcon img = new ImageIcon(pictureName);
        JLabel L_Img = new JLabel(img, JLabel.CENTER);
        Font font = new Font("Times New Roman", Font.PLAIN, 12);
        JTextArea helpMSG = new JTextArea(help, 80, 16);
        helpMSG.setFont(font);
        helpMSG.setEditable(false);
        cp.add(helpMSG, BorderLayout.CENTER);
        cp.add(L_Img, BorderLayout.NORTH);
        MainFrame.MSG.setText("    Информационная система по инженерам");

        setSize(320, 350);
        setLocation(50, 100);
    }

    public void actionPerformed(ActionEvent e){
        dispose();
    }
}

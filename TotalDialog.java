import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

class TotalDialog extends JDialog implements ActionListener {
    public TotalDialog(JFrame parent, String name, java.util.List<TotalRecord> list){
        super(parent, "Подведение итогов", true);
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(new JLabel(name, JLabel.CENTER), BorderLayout.NORTH);
        JButton ok = new JButton("OK");
        ok.addActionListener(this);
        cp.add(ok, BorderLayout.SOUTH);

        TotalTableModel tableModel = new TotalTableModel(
                list, "Стаж", "Число", "Средняя зарплата"
        );
        JTable jtable = new JTable(tableModel);
        JScrollPane scrtable = new JScrollPane(jtable);
        jtable.setPreferredScrollableViewportSize(new Dimension(200, 150));
        add(scrtable, BorderLayout.CENTER);

        MainFrame.MSG.setText("   Итоговый запрос по стажу");
        setSize(360, 260);
        setLocation(50, 100);
    }

    public void actionPerformed(ActionEvent e){
        dispose();
    }
}

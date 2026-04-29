import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class EditPanel extends JPanel {
    JTextField tf1; // id
    JTextField tf2; // фамилия
    JTextField tf3; // зарплата
    JTextField tf4; // стаж

    public EditPanel(){
        setLayout(new GridLayout(3,4,2,2));
        JButton but1 = new JButton("Добавить");
        JButton but2 = new JButton("Обновить");
        JButton but3 = new JButton("Удалить");
        JButton but4 = new JButton("Удалить > ср. зарплаты");

        tf1 = new JTextField(""); // id
        tf2 = new JTextField(""); // фамилия
        tf3 = new JTextField(""); // зарплата
        tf4 = new JTextField(""); // стаж

        JLabel l1 = new JLabel("ID");
        JLabel l2 = new JLabel("Фамилия");
        JLabel l3 = new JLabel("Зарплата");
        JLabel l4 = new JLabel("Стаж");

        add(l1); add(l2); add(l3); add(l4);
        add(tf1); add(tf2); add(tf3); add(tf4);
        add(but1); add(but2); add(but3); add(but4);

        but1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insert();
            }
        });

        but2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                update();
            }
        });

        but3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                delete();
            }
        });

        but4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteGroup();
            }
        });
    }

    private void insert(){
        String sId = tf1.getText();
        String sName = tf2.getText();
        String sZp = tf3.getText();
        String sStag = tf4.getText();

        if (sId.equals("") || sName.equals("") || sZp.equals("") || sStag.equals("")){
            MainFrame.MSG.setText("   Задайте значения всех полей");
            return;
        }

        int id, stag;
        double zp;
        try{
            id = Integer.parseInt(sId);
            zp = Double.parseDouble(sZp);
            stag = Integer.parseInt(sStag);
        } catch (NumberFormatException e){
            MainFrame.MSG.setText("   Неверный формат числовых полей");
            return;
        }

        MainFrame.MSG.setText("   Запрос на добавление записи");
        EngineerWithCtag eng = new EngineerWithCtag(id, sName, zp, stag);
        if (!Global.table.addEng(eng))
            MainFrame.MSG.setText("   Запись не добавлена, возможно, нарушена уникальность ключа");
        Global.updateJTable(Global.table.getEngineers());
        clearFields();
    }

    private void update(){
        String sId = tf1.getText();
        String sName = tf2.getText();
        String sZp = tf3.getText();
        String sStag = tf4.getText();

        if (sId.equals("") || sName.equals("") || sZp.equals("") || sStag.equals("")){
            MainFrame.MSG.setText("   Задайте значения всех полей");
            return;
        }

        int id, stag;
        double zp;
        try{
            id = Integer.parseInt(sId);
            zp = Double.parseDouble(sZp);
            stag = Integer.parseInt(sStag);
        } catch (NumberFormatException e){
            MainFrame.MSG.setText("   Неверный формат числовых полей");
            return;
        }

        MainFrame.MSG.setText("   Запрос на обновление записи");
        EngineerWithCtag eng = new EngineerWithCtag(id, sName, zp, stag);
        if (!Global.table.updateEngineerByKey(eng))
            MainFrame.MSG.setText("   Запись не обновлена, возможно записи с таким ключом нет");
        Global.updateJTable(Global.table.getEngineers());
        clearFields();
    }

    private void delete(){
        String sId = tf1.getText();
        if (sId.equals("")){
            MainFrame.MSG.setText("   Задайте значение ключа (ID)");
            return;
        }
        int id;
        try{
            id = Integer.parseInt(sId);
        } catch (NumberFormatException e){
            MainFrame.MSG.setText("   Неверный формат ID");
            return;
        }

        MainFrame.MSG.setText("   Запрос на удаление записи по ключу");
        // создаем заглушку-инженера с этим id
        EngineerWithCtag eng = new EngineerWithCtag(id, "Noname", 0.0, 0);
        if (!Global.table.delEng(eng))
            MainFrame.MSG.setText("   Запись не удалена, возможно записи с таким ключом нет");
        Global.updateJTable(Global.table.getEngineers());
        clearFields();
    }

    private void deleteGroup(){
        if (!Global.table.deleteAvgZp())
            MainFrame.MSG.setText("   Записи не удалены, возможно таких записей нет");
        else
            MainFrame.MSG.setText("   Удалены записи с зарплатой выше средней");
        Global.updateJTable(Global.table.getEngineers());
        clearFields();
    }

    private void clearFields(){
        tf1.setText("");
        tf2.setText("");
        tf3.setText("");
        tf4.setText("");
    }
}

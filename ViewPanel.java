import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class ViewPanel extends JPanel implements ActionListener {
    JTextField num1; // левая граница зарплаты
    JTextField num2; // правая граница зарплаты
    JTextField tf;   // фильтр по фамилии

    public ViewPanel(){
        setLayout(new GridLayout(7,2,2,2));

        JButton but1 = new JButton("Средняя зарплата");
        JButton but2 = new JButton("Зарплата выше средней");
        JButton but3 = new JButton("Зарплата в диапазоне");
        JButton but4 = new JButton("Итог: по стажу");
        JButton but5 = new JButton("Число инженеров");
        JButton but6 = new JButton("Применить фильтр");
        JButton but7 = new JButton("Сортировать по ID");
        JButton but8 = new JButton("Сортировать по зарплате");
        JButton but9 = new JButton("Вывести все");

        tf = new JTextField("");
        JLabel l1 = new JLabel("Введите фильтр для фамилии", JLabel.CENTER);
        JLabel l2 = new JLabel("");
        JLabel l3 = new JLabel("Укажите границы зарплаты", JLabel.CENTER);

        but1.setActionCommand("Avg");
        but2.setActionCommand("AboveAvg");
        but3.setActionCommand("Between");
        but4.setActionCommand("Total");
        but5.setActionCommand("Count");
        but6.setActionCommand("Filter");
        but7.setActionCommand("SortId");
        but8.setActionCommand("SortSalary");
        but9.setActionCommand("All");

        num1 = new JTextField("",10);
        num2 = new JTextField("",10);
        JPanel p1 = new JPanel();
        p1.add(num1); p1.add(num2);

        add(l1); add(l2);
        add(tf); add(but6);
        add(but1); add(but9);
        add(l3); add(but2);
        add(p1); add(but3);
        add(but7); add(but8);
        add(but4); add(but5);

        but1.addActionListener(this);
        but2.addActionListener(this);
        but3.addActionListener(this);
        but4.addActionListener(this);
        but5.addActionListener(this);
        but6.addActionListener(this);
        but7.addActionListener(this);
        but8.addActionListener(this);
        but9.addActionListener(this);
    }

    private void showAvg(){
        MainFrame.MSG.setText("   Средняя зарплата");
        JOptionPane.showMessageDialog(MainFrame.frame,
                String.format("Средняя зарплата: %8.2f", Global.table.avgZp()));
    }

    private void showAboveAvg(){
        MainFrame.MSG.setText("   Инженеры с зарплатой выше средней");
        Global.updateJTable(Global.table.aboveAvgZp().getEngineers());
    }

    private void showBetween(){
        double a, b;
        try{
            a = Double.parseDouble(num1.getText());
            b = Double.parseDouble(num2.getText());
        } catch (NumberFormatException e){
            MainFrame.MSG.setText("   Задайте правильно границы диапазона");
            return;
        }
        MainFrame.MSG.setText(
            String.format("   Зарплата в диапазоне [%.2f, %.2f]", a, b)
        );
        Global.updateJTable(Global.table.betweenZp(a, b).getEngineers());
    }

    private void showTotal(){
        java.util.List <TotalRecord> list = Global.table.totalCountSumCtag();
        TotalDialog td = new TotalDialog(
                MainFrame.frame,
                "Итоги по стажу (кол-во и средняя зарплата)",
                list
        );
        td.setVisible(true);
    }

    private void showCount(){
        MainFrame.MSG.setText("   Число инженеров");
        JOptionPane.showMessageDialog(MainFrame.frame,
                String.format("Число инженеров: %5d", Global.table.getEngNum()));
    }

    private void showFilter(){
        String filter = tf.getText();
        if (filter.equals("")) {
            MainFrame.MSG.setText("   Введите фильтр");
            return;
        }
        MainFrame.MSG.setText(
                String.format("   Инженеры с фамилией, начинающейся с \"%s\"", filter));
        Global.updateJTable(Global.table.filterOfName(filter).getEngineers());
    }

    private void showSortId(){
        MainFrame.MSG.setText("   Сортировка по ID (естественный порядок)");
        Global.updateJTable(Global.table.sort().getEngineers());
    }

    private void showSortSalary(){
        MainFrame.MSG.setText("   Сортировка по зарплате (по возрастанию)");
        Global.updateJTable(Global.table.sort(new CompIdAsc()).getEngineers());
    }

    private void showAll(){
        MainFrame.MSG.setText("   Все записи без сортировки");
        Global.updateJTable(Global.table.getEngineers());
    }

    public void actionPerformed(ActionEvent e){
        String cmd = e.getActionCommand();
        if ("Avg".equals(cmd)) showAvg();
        else if ("AboveAvg".equals(cmd)) showAboveAvg();
        else if ("Between".equals(cmd)) showBetween();
        else if ("Total".equals(cmd)) showTotal();
        else if ("Count".equals(cmd)) showCount();
        else if ("Filter".equals(cmd)) showFilter();
        else if ("SortId".equals(cmd)) showSortId();
        else if ("SortSalary".equals(cmd)) showSortSalary();
        else showAll();
    }
}

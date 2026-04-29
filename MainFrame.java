import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class MainFrame implements ActionListener {
    String directoryName = "C:/";
    String fileName = "";
    File curFile;

    static JFrame frame;
    JPanel pMain;
    JTable VIS_TABLE;
    EditPanel editPanel;
    ViewPanel viewPanel;
    JLabel jFileName;
    static JLabel MSG;
    java.util.List<String> LINES;

    static String helpArr1 = "\n     Систему разработал студент группы ИТ/б-24-7-о\n " +
            "    Гущин Ростислав Русланович\n" +
            "     СевГУ - 2025.\n";

    static String helpArr2 = "\n     Информационная система осуществляет хранение и\n" +
            "     обработку данных об инженерах: id, фамилия,\n" +
            "     зарплата, стаж.\n";

    public MainFrame(){
        // 1. Внутреннее представление таблицы
        Global.table = new EngGroup("Список записей об инженерах");
        // 2. Список для JTable
        Global.engineers = new ArrayList<EngineerWithCtag>();
        // 3. Модель и JTable
        Global.tableModel = new EngineerTableModel(Global.engineers);
        VIS_TABLE = new JTable(Global.tableModel);
        JScrollPane scrtable = new JScrollPane(VIS_TABLE);
        VIS_TABLE.setPreferredScrollableViewportSize(new Dimension(400, 150));

        // Панели
        viewPanel = new ViewPanel();
        editPanel = new EditPanel();

        int WinSizeG = 700;
        int WinSizeV = 500;
        frame = new JFrame("Система хранения и обработки данных об инженерах");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container myC = frame.getContentPane();
        myC.setLayout(new BorderLayout(5,5));

        // Меню
        MenuIS s = new MenuIS();
        frame.setJMenuBar(s.mb1);
        s.newFile.addActionListener(this);
        s.openFile.addActionListener(this);
        s.saveFile.addActionListener(this);
        s.saveAsFile.addActionListener(this);
        s.closeFile.addActionListener(this);
        s.startEdit.addActionListener(this);
        s.stopEdit.addActionListener(this);
        s.help1.addActionListener(this);
        s.help2.addActionListener(this);

        // Главная панель
        pMain = new JPanel();
        pMain.setLayout(new BorderLayout());
        pMain.add(scrtable, BorderLayout.CENTER);
        pMain.add(editPanel, BorderLayout.SOUTH);
        jFileName = new JLabel("Без имени", JLabel.CENTER);
        pMain.add(jFileName, BorderLayout.NORTH);

        int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
        int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
        JScrollPane spMain = new JScrollPane(pMain, v, h);
        myC.add(spMain, BorderLayout.CENTER);

        myC.add(new JLabel("Информация об инженерах", JLabel.CENTER),
                BorderLayout.NORTH);

        MSG = new JLabel("   Информационная система по инженерам");
        MSG.setFont(new Font("Serif", Font.BOLD, 14));
        myC.add(MSG, BorderLayout.SOUTH);

        frame.setSize(WinSizeG, WinSizeV);
        frame.setLocation(50, 50);
        frame.setVisible(true);
    }

    // ---- ФАЙЛ ----

    public void NewFile(){
        Global.table.getEngineers().clear();
        Global.engineers.clear();
        Global.tableModel.fireTableDataChanged();
        pMain.remove(viewPanel);
        pMain.add(editPanel, BorderLayout.SOUTH);
        MSG.setText("   Создание базы данных");
        fileName = "";
        curFile = null;
        jFileName.setText("Без имени");
    }

    public void setFileFilter(JFileChooser fch){
        TextFilter text_filter = new TextFilter();
        fch.setFileFilter(text_filter);
    }

    public void OpenFile(){
        int rez; int n;
        MSG.setText("   Открытие файла");
        JFileChooser fch = new JFileChooser(directoryName);
        fch.setDialogTitle("Открытие файла");
        setFileFilter(fch);
        rez = fch.showDialog(frame, "Open");
        if (rez == JFileChooser.APPROVE_OPTION){
            curFile = fch.getSelectedFile();
            fileName = curFile.getAbsolutePath();
            n = fileName.lastIndexOf('\\');
            if (n > 0)
                directoryName = fileName.substring(0, n + 1);
            try{
                LINES = IO.inpLines(fileName);
                if (LINES != null) MSG.setText("   Успешный ввод данных");
                else MSG.setText("   Ошибка ввода данных");
            } catch (Exception e) {
                MSG.setText("   Ошибка ввода данных");
            }
            java.util.List<EngineerWithCtag> engList = Transfer.StringsToEngineer(LINES);
            if (engList != null){
                Global.table.getEngineers().clear();
                for (EngineerWithCtag eng: engList) Global.table.addEng(eng);
                Global.updateJTable(Global.table.getEngineers());
                pMain.remove(editPanel);
                pMain.add(viewPanel, BorderLayout.SOUTH);
                jFileName.setText(fileName);
            } else {
                MSG.setText("   Неверные данные в файле");
            }
        }
    }

    private void SaveDialog(){
        int rez; int n;
        JFileChooser fch = new JFileChooser(directoryName);
        fch.setDialogTitle("Сохранение файла");
        setFileFilter(fch);
        rez = fch.showDialog(frame, "Save");
        if (rez == JFileChooser.APPROVE_OPTION){
            curFile = fch.getSelectedFile();
            fileName = curFile.getAbsolutePath();
            n = fileName.lastIndexOf('\\');
            if (n > 0)
                directoryName = fileName.substring(0, n + 1);
        }
    }

    public void SaveFile(boolean fs){
        String old_file_name = fileName;
        MSG.setText("   Сохранение файла");
        if (fs) SaveDialog();
        else if (fileName.equals("")) SaveDialog();

        if (curFile == null){
            MSG.setText("   Файл для сохранения не выбран");
            return;
        }
        if ((!curFile.exists()) || fileName.equals(old_file_name)){
            LINES = Transfer.EngineerToStrings(Global.table.getEngineers());
            try{
                boolean f = IO.outpLines(fileName, LINES);
                if (f) {
                    MSG.setText("   Данные успешно сохранены");
                    jFileName.setText(fileName);
                } else MSG.setText("   Ошибка сохранения данных");
            } catch (Exception e) {
                MSG.setText("   Ошибка сохранения данных");
            }
        } else {
            JOptionPane.showMessageDialog(
                    frame,
                    "Ошибка: файл с заданным именем " + fileName + " существует");
            fileName = old_file_name;
        }
    }

    public void CloseWindow(){
        frame.dispose();
    }

    // ---- РЕДАКТИРОВАНИЕ ----

    public void StartEdit(){
        pMain.remove(viewPanel);
        pMain.add(editPanel, BorderLayout.SOUTH);
        MSG.setText("   Редактирование базы данных");
    }

    public void StopEdit(){
        pMain.remove(editPanel);
        pMain.add(viewPanel, BorderLayout.SOUTH);
        MSG.setText("   Просмотр базы данных");
    }

    // ---- ОБРАБОТКА МЕНЮ ----

    public void actionPerformed(ActionEvent e){
        String cmd = e.getActionCommand();
        if ("Новый".equals(cmd)) NewFile();
        else if ("Открыть".equals(cmd)) OpenFile();
        else if ("Сохранить".equals(cmd)) SaveFile(false);
        else if ("Сохранить как".equals(cmd)) SaveFile(true);
        else if ("Закрыть".equals(cmd)) CloseWindow();
        else if ("Начать редактирование".equals(cmd)) StartEdit();
        else if ("Закончить редактирование".equals(cmd)) StopEdit();
        else if ("О программе".equals(cmd)){
            HelpDialog helpMSG = new HelpDialog(
                    MainFrame.frame,
                    "О программе",
                    helpArr1,
                    "dinosaurs.gif" 
            );
            helpMSG.setVisible(true);
        }
        else if ("Описание ИС".equals(cmd)){
            HelpDialog helpMSG1 = new HelpDialog(
                    MainFrame.frame,
                    "Описание информационной системы",
                    helpArr2,
                    "horse.gif" 
            );
            helpMSG1.setVisible(true);
        }
    }
}

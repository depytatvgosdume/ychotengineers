import java.util.*;

public class Global {
    // Глобальные структуры
    public static EngGroup table;
    public static List<EngineerWithCtag> engineers;
    static EngineerTableModel tableModel;

    public static void updateJTable(List<EngineerWithCtag> list){
        engineers.clear();
        engineers.addAll(list);
        tableModel.fireTableDataChanged();
    }
}

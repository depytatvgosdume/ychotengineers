import javax.swing.table.AbstractTableModel;
import java.util.*;

public class EngineerTableModel extends AbstractTableModel {
    List<EngineerWithCtag> engineers;

    public EngineerTableModel(List<EngineerWithCtag> engineers){
        super();
        this.engineers = engineers;
    }

    @Override
    public int getRowCount() {
        return engineers.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int r, int c) {
        EngineerWithCtag e = engineers.get(r);
        switch (c) {
            case 0: return e.geti();
            case 1: return e.gets();
            case 2: return e.geto();
            case 3: return e.getStag();
            default: return "";
        }
    }

    @Override
    public String getColumnName(int c) {
        switch (c) {
            case 0: return "ID";
            case 1: return "Фамилия";
            case 2: return "Зарплата";
            case 3: return "Стаж";
        }
        return "";
    }
}

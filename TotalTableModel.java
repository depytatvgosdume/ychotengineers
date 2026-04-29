import javax.swing.table.AbstractTableModel;
import java.util.*;

public class TotalTableModel extends AbstractTableModel {
    // Модель таблицы для итогового запроса
    List<TotalRecord> records;
    String col1Name;
    String col2Name;
    String col3Name;

    public TotalTableModel(List<TotalRecord> records,
                           String col1Name,
                           String col2Name,
                           String col3Name) {
        super();
        this.records = records;
        this.col1Name = col1Name;
        this.col2Name = col2Name;
        this.col3Name = col3Name;
    }

    @Override
    public int getRowCount() {
        return records == null ? 0 : records.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int r, int c) {
        TotalRecord tr = records.get(r);
        switch (c) {
            case 0: return tr.ctag;
            case 1: return tr.num1;
            case 2: return tr.num2;
            default: return "";
        }
    }

    @Override
    public String getColumnName(int c) {
        switch (c) {
            case 0: return col1Name;
            case 1: return col2Name;
            case 2: return col3Name;
        }
        return "";
    }
}

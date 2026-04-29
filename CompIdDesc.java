import java.util.*;

public class CompIdDesc implements Comparator {
    // по убыванию зарплаты
    public int compare(Object ob1, Object ob2){
        EngineerWithCtag e1 = (EngineerWithCtag) ob1;
        EngineerWithCtag e2 = (EngineerWithCtag) ob2;
        return -Double.compare(e1.geto(), e2.geto());
    }
}

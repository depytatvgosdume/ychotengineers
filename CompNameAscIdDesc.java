import java.util.*;

public class CompNameAscIdDesc implements Comparator {
    // по возрастанию фамилии, в рамках одной фамилии по убыванию id
    public int compare(Object ob1, Object ob2){
        EngineerWithCtag e1 = (EngineerWithCtag) ob1;
        EngineerWithCtag e2 = (EngineerWithCtag) ob2;
        int c = e1.gets().compareTo(e2.gets());
        if (c < 0) return -1;
        if (c > 0) return 1;
        // фамилии равны → id по убыванию
        return Integer.compare(e2.geti(), e1.geti());
    }
}

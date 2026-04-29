public class TotalRecord {
    int ctag;    // стаж
    int num1;    // число инженеров
    double num2; // средняя зарплата

    public TotalRecord(int ctag, int num1, double num2){
        this.ctag = ctag;
        this.num1 = num1;
        this.num2 = num2;
    }

    public String toString(){
        return String.format("|%5d|%5d|%8.2f|", ctag, num1, num2);
    }
}

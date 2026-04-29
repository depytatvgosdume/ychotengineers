public class Engineer implements Comparable<Engineer> {
    private final static String ENGINEER_FORMAT_STRING = "Инженер: %d, %s, %.2f";
    private int i;       // id
    private String s;    // фамилия
    private double o;    // зарплата

    public Engineer(){
        i = 0; s = ""; o = 0.0;
    }

    public Engineer(int i, String s, double o){
        this.i = i; this.s = s; this.o = o;
    }

    public Engineer(Engineer eng){
        i = eng.i; s = eng.s; o = eng.o;
    }

    public int geti(){return i;}
    public String gets(){return s;}
    public double geto(){return o;}

    public void seti(int i){this.i = i;}
    public void sets(String s){this.s = s;}
    public void seto(double o){this.o = o;}

    @Override
    public String toString(){
        return String.format(ENGINEER_FORMAT_STRING, i, s, o);
    }

    @Override
    public boolean equals(Object obj){
        if (obj == this) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Engineer eng = (Engineer) obj;
        return (i == eng.i);
    }

    @Override
    public int hashCode(){
        return 7 * Integer.valueOf(i).hashCode();
    }

    @Override
    public int compareTo(Engineer eng){
        return Integer.compare(this.i, eng.i);
    }

    public double prem(double zp){
        return zp * 1.5;
    }
}

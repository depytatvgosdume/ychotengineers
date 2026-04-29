public class EngineerWithCtag extends Engineer {
    private final static String EWC_FORMAT_STRING = "%s, стаж: %d";
    private int stag; // стаж

    public EngineerWithCtag(){
        super();
        stag = 0;
    }

    public EngineerWithCtag(int i, String s, double o, int stag){
        super(i, s, o);
        this.stag = stag;
    }

    public EngineerWithCtag(EngineerWithCtag ewh){
        super(ewh);
        stag = ewh.stag;
    }

    public int getStag(){return stag;}
    public void setStag(int stag){this.stag = stag;}

    @Override
    public String toString(){
        return String.format(EWC_FORMAT_STRING, super.toString(), stag);
    }
}

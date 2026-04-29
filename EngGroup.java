import java.util.*;

public class EngGroup {
    private final static String GROUP_FORMAT_STRING =
            "Инженеры: %s, %5d записей";

    private String name;
    private List<EngineerWithCtag> engineer;

    public EngGroup() {
        name = "";
        engineer = new ArrayList<EngineerWithCtag>();
    }

    public EngGroup(String name){
        this.name = name;
        engineer = new ArrayList<EngineerWithCtag>();
    }

    public EngGroup(String name, List<EngineerWithCtag> list){
        this.name = name;
        engineer = new ArrayList<EngineerWithCtag>(list);
    }

    public void setName(String name){this.name = name;}
    public void setEngineers(List<EngineerWithCtag> engineer){this.engineer = engineer;}

    public String getName(){return name;}
    public List<EngineerWithCtag> getEngineers(){return engineer;}

    @Override
    public String toString(){
        return String.format(GROUP_FORMAT_STRING, name, getEngNum());
    }

    // Вставка
    public boolean addEng(EngineerWithCtag eng){
        if (getEng(eng.geti()) != null) return false;
        return engineer.add(eng);
    }

    // Удаление по объекту (по id в equals)
    public boolean delEng(EngineerWithCtag eng){
        return engineer.remove(eng);
    }

    // Удаление всех с ЗП выше среднего
    public boolean deleteAvgZp(){
        return engineer.removeAll(aboveAvgZp().engineer);
    }

    // Обновление по ключу
    public boolean updateEngineerByKey(EngineerWithCtag eng){
        EngineerWithCtag e = getEng(eng.geti());
        if (e != null){
            e.sets(eng.gets());
            e.seto(eng.geto());
            e.setStag(eng.getStag());
            return true;
        }
        return false;
    }

    // Получить по id
    public EngineerWithCtag getEng(int id){
        for (EngineerWithCtag eng: engineer)
            if (eng.geti() == id) return eng;
        return null;
    }

    public int getEngNum(){
        return engineer.size();
    }

    // Средняя зарплата
    public double avgZp(){
        int num = engineer.size();
        if (num == 0) return 0;
        double sum = 0;
        for (EngineerWithCtag eng: engineer)
            sum += eng.geto();
        return sum / num;
    }

    // Выше средней
    public EngGroup aboveAvgZp(){
        double avg = avgZp();
        EngGroup group = new EngGroup(
                String.format("%s: инженеры с зарплатой выше средней (%.2f)",
                        name, avg)
        );
        for (EngineerWithCtag eng: engineer)
            if (eng.geto() > avg) group.addEng(eng);
        return group;
    }

    // В диапазоне [b1, b2]
    public EngGroup betweenZp(double b1, double b2){
        EngGroup group = new EngGroup(
                String.format("%s: инженеры с зарплатой в диапазоне от %.2f до %.2f",
                        name, b1, b2)
        );
        for (EngineerWithCtag eng: engineer){
            if (eng.geto() >= b1 && eng.geto() <= b2) group.addEng(eng);
        }
        return group;
    }

    // Фильтр по фамилии
    public EngGroup filterOfName(String filter){
        EngGroup group = new EngGroup(
                String.format("%s: инженеры с фамилией на \"%s\"", name, filter)
        );
        if (filter != null && !filter.equals("")){
            filter = filter.toLowerCase();
            for (EngineerWithCtag eng: engineer){
                if (eng.gets().toLowerCase().startsWith(filter)) group.addEng(eng);
            }
        }
        return group;
    }

    // Итоги по стажу: стаж → количество, средняя зарплата
    public List<TotalRecord> totalCountSumCtag(){
        int n = engineer.size();
        if (n == 0) return null;

        List<EngineerWithCtag> engineerTemp = new ArrayList<EngineerWithCtag>();
        engineerTemp.addAll(engineer);

        SortedSet<Integer> ctagS = new TreeSet<Integer>();
        for (EngineerWithCtag eng: engineer) ctagS.add(eng.getStag());

        List<Integer> ctagL = new ArrayList<Integer>(ctagS);
        int m = ctagL.size();
        int ctag, count;
        double sum;

        List<TotalRecord> totRecList = new ArrayList<TotalRecord>();
        for (int i = 0; i < m; i++){
            ctag = ctagL.get(i);
            sum = 0;
            count = 0;
            Iterator<EngineerWithCtag> iter = engineerTemp.iterator();
            while (iter.hasNext()){
                EngineerWithCtag eng = iter.next();
                if (ctag == eng.getStag()){
                    count++;
                    sum += eng.geto();
                    iter.remove();
                }
            }
            double avg = (count == 0) ? 0 : sum / count;
            totRecList.add(new TotalRecord(ctag, count, avg));
        }
        return totRecList;
    }

    // Сортировки
    public EngGroup sort(){ // естественный порядок по id
        EngGroup group = new EngGroup(name, engineer);
        Collections.sort(group.engineer);
        return group;
    }

    public EngGroup sort(Comparator comp){
        EngGroup group = new EngGroup(name, engineer);
        Collections.sort(group.engineer, comp);
        return group;
    }
}

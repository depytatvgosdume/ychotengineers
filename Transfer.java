import java.util.*;

public class Transfer {
    // Приведение фамилии к нормальному виду
    public static String unificationString(String line){
        if (line == null) return null;
        line = line.trim();
        if (line.equals("")) return "";
        String[] words = line.split("\\s+");
        StringBuilder result = new StringBuilder();
        result.append(words[0].substring(0,1).toUpperCase());
        result.append(words[0].substring(1).toLowerCase());
        for (int i = 1; i < words.length; i++){
            result.append(" ");
            result.append(words[i].toLowerCase());
        }
        return result.toString();
    }

    // "id, фамилия, зарплата, стаж" → EngineerWithCtag
    public static EngineerWithCtag lineToEngineer(String line, int num){
        String[] words = line.split(",");
        if (words.length != num) return null;
        EngineerWithCtag eng = new EngineerWithCtag();
        eng.sets(unificationString(words[1]));
        try{
            eng.seti(Integer.parseInt(words[0].trim()));
            eng.seto(Double.parseDouble(words[2].trim()));
            eng.setStag(Integer.parseInt(words[3].trim()));
        }
        catch(NumberFormatException e){
            return null;
        }
        return eng;
    }

    public static List<EngineerWithCtag> StringsToEngineer(List<String> lines){
        if (lines == null || lines.isEmpty()) return null;
        List<EngineerWithCtag> engineers = new ArrayList<EngineerWithCtag>();
        for (int i = 0; i < lines.size(); i++){
            EngineerWithCtag eng = lineToEngineer(lines.get(i), 4);
            if (eng == null) return null;
            engineers.add(eng);
        }
        return engineers;
    }

    public static List<String> EngineerToStrings(List<EngineerWithCtag> engineer){
        if (engineer == null || engineer.isEmpty()) return null;
        List<String> lines = new ArrayList<String>();
        for (EngineerWithCtag eng: engineer){
            
            lines.add(String.format(Locale.US, "%d, %s, %.6f, %d",
                    eng.geti(), eng.gets(), eng.geto(), eng.getStag()));
        }
        return lines;
    }
}

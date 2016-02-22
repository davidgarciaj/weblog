/**
 * Read web server data and analyse
 * hourly access patterns.
 * 
 * @author David J. Barnes and Michael KÃ¶lling.
 * @version 2011.07.31
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;

    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer()
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        // Create the reader to obtain the data.
        reader = new LogfileReader();
    }

    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer(String reader)
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        // Create the reader to obtain the data.
        this.reader = new LogfileReader(reader);
    }

    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }

    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        int hour = 0;
        while(hour < hourCounts.length) {
            System.out.println(hour + ": " + hourCounts[hour]);
            hour++;
        }
    }
    
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
    
    /**
     * @return number of the acces to server
     */
    public int numberOfAccesses(){
        int total = 0;
        int hour = 0;
        while(hour < hourCounts.length) {
            total+= hourCounts[hour];
            hour++;
        }
        return total;
    }
    
    /**
     * @return number of the acces to server
     */
    public int busiestHour(){
        int mostAccess = -1;
        int hour = 0;
        while(hour < hourCounts.length) {
            if(mostAccess != -1){
                if(hourCounts[hour] >= hourCounts[mostAccess]){
                    mostAccess = hour;
                }
            }
            else if(hourCounts[hour] != 0){mostAccess = hour;}
            hour++;
        }
        if(mostAccess == -1){System.out.println("No se ha ejecutado el método analyzeHourlyData que nos da la información a analizar.");}
        return mostAccess;
    }
}

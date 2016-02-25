/**
 * Read web server data and analyse
 * hourly access patterns.
 * 
 * @author David J. Barnes and Michael Kölling.
 * @version 2011.07.31
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Where to calculate the daily access counts.
    private int[] dayCounts;
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
        // Create the array object to hold the daily
        // access counts.
        dayCounts = new int[31];
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
        // Create the array object to hold the daily
        // access counts.
        dayCounts = new int[31];
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
            int day = entry.getDay();
            dayCounts[day]++;
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
     * @return number of the acces to server in the hour with the most access
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
        if(mostAccess == -1){System.out.println("No ha habido accesos.");}
        return mostAccess;
    }

    /**
     * @return number of the acces to server in the hour with the lowwest number
     */
    public int quietesHour(){
        int lowAccess = -1;
        int hour = 0;
        while(hour < hourCounts.length) {
            if(lowAccess != -1){
                if(hourCounts[hour] <= hourCounts[lowAccess]){
                    lowAccess = hour;
                }
            }
            else if(hourCounts[hour] != 0){lowAccess = hour;}
            hour++;
        }
        if(lowAccess == -1){System.out.println("No ha habido accesos.");}
        return lowAccess;
    }

    /**
     * @return number of the acces to server in period of two hour with the most access
     */
    public String busiestPeriod(){
        String mostAccess = -1 + "";
        int numAccess = 0;
        int hour = 0;
        while(hour < hourCounts.length) {
            if(numAccess != 0){
                if((hourCounts[hour] + hourCounts[hour - 1]) >= numAccess){
                    numAccess = hourCounts[hour] + hourCounts[hour - 1];
                    mostAccess = hour - 1 + " - " + hour;
                }
            }
            else if(hour == 0 && (hourCounts[0] + hourCounts[23]) != 0){
                numAccess = hourCounts[23] + hourCounts[0];
                mostAccess = "23 - 0";
            }
            else if(hour != 0 && (hourCounts[hour] + hourCounts[hour - 1]) != 0){
                numAccess = hourCounts[hour] + hourCounts[hour - 1];
                mostAccess = hour - 1 + " - " + hour;
            }
            hour++;
        }
        if(numAccess == 0){System.out.println("No ha habido accesos.");}
        return mostAccess;
    }

    /** Analyze the hourly accesses only in the given date
     *
     * @param day   The given day
     * @param month The given month
     * @param year  The given year
     */
    public void analyzeHourlyDataInOneDay(int day, int month, int year)
    {
        boolean isInDate = false;
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            if(entry.getDay() == day && entry.getMonth() == month && entry.getYear() == year){
                isInDate = true;
            }
            if(isInDate){
                int hour = entry.getHour();
                hourCounts[hour]++;
                isInDate = false;
            }
        }
    }
}

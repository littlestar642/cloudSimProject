package source;

import java.util.Comparator;

class SortbyLength implements Comparator<JobVector> 
{ 
    // Used for sorting in acending order of 
    // roll number 
    public int compare(JobVector a,JobVector b) 
    { 
        return (int)a.getCloudlet().getCloudletLength() - (int)b.getCloudlet().getCloudletLength();
    } 
}
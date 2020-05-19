package source;

import java.util.Comparator;

class SortbyExpectationTime implements Comparator<JobVector> 
{ 
    // Used for sorting in acending order of 
    // roll number 
    public int compare(JobVector a,JobVector b) 
    { 
        return (int)a.expTime-(int)b.expTime;
    } 
}
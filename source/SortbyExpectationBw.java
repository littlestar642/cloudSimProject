package source;

import java.util.Comparator;

class SortbyExpectationBw implements Comparator<JobVector> 
{ 
    // Used for sorting in acending order of 
    // roll number 
    public int compare(JobVector a,JobVector b) 
    { 
        return (int)b.expBw-(int)a.expBw;
    } 
}
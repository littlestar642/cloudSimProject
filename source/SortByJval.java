package source;

import java.util.Comparator;

class SortbyJval implements Comparator<JobVector> 
{ 
    // Used for sorting in acending order of 
    // roll number 
    public int compare(JobVector a,JobVector b) 
    { 	
        return (int)(Math.abs(a.getJval()*100000000)-Math.abs(b.getJval()*100000000));
    } 
}

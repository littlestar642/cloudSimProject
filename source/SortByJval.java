package source;

import java.util.Comparator;

class SortbyJval implements Comparator<JobVector> 
{ 
    // Used for sorting in acending order of 
    // roll number 
    public int compare(JobVector a,JobVector b) 
    { 
        if(a.getJval()<0 && b.getJval()<0){
            return (int)(-b.getJval()*100000000)+(int)a.getJval()*100000000;
        }
        else if(a.getJval()>0 && b.getJval()>0){
            return (int)(a.getJval()*100000000)-(int)b.getJval()*100000000;
        }
        else if(a.getJval()>0 && b.getJval()<0){
            return 1;
        }
        else{
            return -1;
        }
    } 
}
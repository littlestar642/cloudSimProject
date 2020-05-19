package source;

import java.util.Comparator;

import org.cloudbus.cloudsim.Vm;

class SortbyBw implements Comparator<Vm> 
{ 
    // Used for sorting in acending order of 
    // roll number 
    public int compare(Vm a,Vm b) 
    { 
        return (int)b.getBw() - (int)a.getBw();
    } 
}
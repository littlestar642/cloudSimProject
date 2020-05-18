package source;

import java.util.Comparator;

import org.cloudbus.cloudsim.Cloudlet;

class SortbyLength implements Comparator<Cloudlet> 
{ 
    // Used for sorting in acending order of 
    // roll number 
    public int compare(Cloudlet a,Cloudlet b) 
    { 
        return (int)a.getCloudletLength() - (int)b.getCloudletLength();
    } 
}
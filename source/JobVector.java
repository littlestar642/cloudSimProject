package source;

import org.cloudbus.cloudsim.Cloudlet;

public class JobVector{

    public double classType;
    public double startTime;
    public double endTime;
    public double expTime;
    public double expBw;
    public double jVal;
    public double priority;
    public Cloudlet cl;


    public JobVector(final double classType, final double startTime, final double endTime,final double expTime, final double expBw,
            final double jVal, final double priority,final Cloudlet cl) {
        this.classType=classType;
        this.startTime=startTime;
        this.endTime=endTime;
        this.expBw=expBw;
        this.jVal=jVal;
        this.priority=priority;
        this.cl=cl;
        this.expTime=expTime;
    }

    public double getClassType(){
        return this.classType;
    }

    public Cloudlet getCloudlet(){
        return this.cl;
    }

    public void setJval(double val){
        this.jVal=val;
    }
    public void setPriority(double val){
        this.priority=val;
    }
    public void setEndTime(double val){
        this.endTime=val;
    }

    public void setStartTime(double val){
        this.startTime=val;
    }

    

}
package source;

public class Priority {

    int ind=1;

    public Priority(int i){
        this.ind=i;
    }

    public void increment(){
        this.ind++;
    }

    public int getPriority(){
        return this.ind;
    }

    public void resetPriority(){
        this.ind=1;
    }
}

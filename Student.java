public class Student extends Member {

    // private int maxItems = 3;
    // private int LoanPeriod = 

    Student(String id , String name){
        super(id,name);
    }

    @Override  public int getMaxItems(){ return 3;}
    @Override  public int getLoanPeriod(){ return 14;}


    
}

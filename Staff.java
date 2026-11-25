public class Staff extends Member {

    Staff(String id , String name){
        super(id,name);
    }

    @Override  public int getMaxItems(){ return 3;}
    @Override  public int getLoanPeriod(){ return 14;}


    
}

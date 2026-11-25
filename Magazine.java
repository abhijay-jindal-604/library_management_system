class Magazine extends BorrowableItem {

    private String issue;

    Magazine(String id, String title, String issue){
        super(id,title);
        this.issue = issue;
    }

    @Override 
    public int getFinePerDay(){
        return 1;
    }

    @Override 
    public String getTypeName(){
        return "MAGAZINE";
    }

    
    
}

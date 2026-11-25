class Book extends BorrowableItem {

    private String author;

    Book(String id, String title , String author){
        super(id,title);
        this.author = author;
    }

    @Override 
    public int getFinePerDay(){
        return 2;
    }

    @Override 
    public String getTypeName(){
        return "BOOK";
    }

    
    
}

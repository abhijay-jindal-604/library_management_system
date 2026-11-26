public class ReturnResult {

    private int fine;
    private Member issuedTo;
    private int newDueDay;

    public ReturnResult(int fine, Member issuedTo, int newDueDay) {
        this.fine = fine;
        this.issuedTo = issuedTo;
        this.newDueDay = newDueDay;
    }

    public int getFine(){
        return fine;
    }

    public Member getIssuedTo(){ return issuedTo;}

    public int getNewDueDay(){return newDueDay;}

    public boolean hasReissue(){
        return issuedTo != null;
    }

    
}

import java.util.*;

abstract class BorrowableItem extends LibraryItem implements Borrowable {


    private Member borrower;
    private int dueDay;
    private Queue<Member> reservations = new LinkedList<>();

    public BorrowableItem(String id, String title) {
        super(id, title);
    }

    public abstract int getFinePerDay();

    @Override 
    public boolean isOnLoan() { return borrower != null; }

    @Override
    public Member getBorrower() {
        return borrower;
    }

    @Override
    public int getDueDay() {
        return dueDay;
    }

    @Override 
    public void checkOut(Member member , int dueDay) throws ItemNotAvailableException{
        if(isOnLoan()) throw new ItemNotAvailableException(getId() + " is on loan");
        else{
            borrower = member;
            this.dueDay = dueDay;
        }

    }

    @Override 
    public String getStatus(){
        return isOnLoan() ? "ON_LOAN|" + borrower.getId() : "AVAILABLE";
    }

    @Override 
    public void checkIn() throws ItemNotOnLoanException{
        if(!isOnLoan()) throw new ItemNotOnLoanException(getId());
        else borrower = null;
    }

    @Override 
    public int fineAsOf(int today){

        if(!isOnLoan()) return 0;
        return Math.max(0 , today - dueDay) * getFinePerDay();

    }

    @Override 
    public void reserve(Member member) throws ReservationNotAllowedException{
        if(!isOnLoan()) throw new ReservationNotAllowedException(getId() + " is available");
        
        if(borrower == member) throw new ReservationNotAllowedException(member.getId() + " already has " + getId());

        if(reservations.contains(member)) throw new ReservationNotAllowedException(member.getId() + " already reserved " + getId());

        reservations.add(member);
    }
    @Override 
    public Member nextEligibleReserver(){
        Iterator<Member> it = reservations.iterator();

        while(it.hasNext()){
            Member m = it.next();
            if(m.canBorrow()){
                it.remove(); 
                return m ;
            }
        }

        return null;

    }
    
}

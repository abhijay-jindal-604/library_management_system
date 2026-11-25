interface Borrowable {
    abstract public String getId();
    abstract public String getTitle();
    abstract public int getFinePerDay();
    abstract public boolean isOnLoan();
    abstract public Member getBorrower();
    abstract public int getDueDay();
    abstract public void checkOut(Member member, int dueDay) throws ItemNotAvailableException;
    abstract public void checkIn() throws ItemNotOnLoanException;
    abstract public int fineAsOf(int today);
    abstract public void reserve(Member member) throws ReservationNotAllowedException;
    abstract public Member nextEligibleReserver();

}

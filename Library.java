import java.util.*;

class Library {
    private int today = 0; // increases day by day
    private TreeMap<String, LibraryItem> items = new TreeMap<>();   //ordered_map 
    private HashMap<String, Member> members = new HashMap<>();  //first come first serve?

    public int getToday(){
        return today;
    }
    
    public int advance(int days){
        today+= days;
        return today;
    }

    public void addItem(LibraryItem item){
        if(!items.containsKey(item.getId())){ // useless  guaranttteed by input

            items.put(item.getId(),item);
           
        }
    }

    public void addMember(Member member){
        if(!members.containsKey(member.getId())){

            members.put(member.getId(),member);
        }
    }

    private LibraryItem findItem(String id) throws ItemNotFoundException{

        if(!items.containsKey(id)) throw new ItemNotFoundException(id);

        return items.get(id);
    }

    private Member findMember(String id) throws MemberNotFoundException{
        if(!members.containsKey(id)) throw new MemberNotFoundException(id);

        return members.get(id);
    }

    public int borrow(String memberId , String itemId) throws LibraryException{

        Member member = findMember(memberId);
        LibraryItem item = findItem(itemId);

        if(!(item instanceof Borrowable)) throw new ItemNotAvailableException(itemId + " cannot be borrowed");

        Borrowable b = (Borrowable)item;


        member.ensureCanBorrow();

        int due = today + member.getLoanPeriod();

        b.checkOut(member, due);

        member.addItem(b);
        
        return due;
    }

    public ReturnResult returnItem(String itemId) throws LibraryException{

        LibraryItem item = findItem(itemId);
        if(!(item instanceof Borrowable)) throw new ItemNotOnLoanException(itemId);

        Borrowable b = (Borrowable) item ;

        if(!b.isOnLoan()) throw new ItemNotOnLoanException(itemId);

        Member borrower = b.getBorrower();
        int fine = b.fineAsOf(today);
        borrower.removeItem(b);
        b.checkIn();
        Member next = b.nextEligibleReserver();

        if(next!= null){
            int due = today + next.getLoanPeriod();
            b.checkOut(next,due);
            next.addItem(b);
            return new ReturnResult(fine, next, due);
        }

        return new ReturnResult(fine, null, 0);
    }

    public void reserve(String memberId , String itemId) throws LibraryException{

        Member member = findMember(memberId);
        LibraryItem item = findItem(itemId);

        if(!(item instanceof Borrowable)) throw new ItemNotAvailableException(itemId + " cannot be borrowed");

        Borrowable b = (Borrowable)item;

        b.reserve(member);

    }

    public List<LibraryItem> search(String txt){

        String text = txt.toLowerCase();

        ArrayList<LibraryItem> result = new ArrayList<>();



        for(Map.Entry<String,LibraryItem> e : items.entrySet()){

            if(e.getValue().getTitle().toLowerCase().contains(text)){
                result.add(e.getValue());
            }
            
        }

        result.sort(Comparator.comparing(LibraryItem::getTitle));

        return result;



    }

    public List<LibraryItem> inventory(){
        return new ArrayList<>(items.values());
    }

    public List<Borrowable> loansOf(String memberId)throws MemberNotFoundException{
        Member member = findMember(memberId);

        ArrayList<Borrowable> result = new ArrayList<>(member.getItems());

        result.sort(Comparator.comparing(Borrowable::getDueDay).thenComparing(Borrowable::getId));

        return result;

    }

    public List<Borrowable> overdue(){

        ArrayList<Borrowable> dues= new ArrayList<>();

        for(LibraryItem i : items.values()){

            if(!(i instanceof Borrowable)) continue;

            Borrowable b = (Borrowable) i;

            if(b.isOnLoan() && today > b.getDueDay()) dues.add(b);

        }

        dues.sort(Comparator.comparing(Borrowable::getDueDay).thenComparing(Borrowable::getId));

        return dues;

    }







}
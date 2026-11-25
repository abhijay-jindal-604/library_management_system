import java.util.*;

abstract class Member {

    private String id;
    private String name;
    private List<Borrowable> items = new ArrayList<>();

    public Member(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public abstract int getMaxItems();
    public abstract int getLoanPeriod();

    public boolean canBorrow() {
        return items.size() < getMaxItems();
    }

    public void ensureCanBorrow() throws BorrowLimitExceededException {
        if (!canBorrow()) {
            throw new BorrowLimitExceededException(id, getMaxItems());
        }
    }

    public boolean holds(Borrowable item) {
        return items.contains(item);
    }

    public void addItem(Borrowable item) throws BorrowLimitExceededException {
        ensureCanBorrow();
        items.add(item);
    }

    public void removeItem(Borrowable item) throws ItemNotOnLoanException {
        if (!holds(item)) {
            throw new ItemNotOnLoanException(item.getId());
        }
        items.remove(item);
    }

    public List<Borrowable> getItems() {
        return items;
    }
}

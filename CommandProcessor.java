import java.util.*;

class CommandProcessor {

    private final Library library;

    CommandProcessor(Library library){
        this.library = library;
    }

    public void run(Scanner in){
        while(in.hasNextLine()){
            String line = in.nextLine().trim();
            if(line.isBlank()) continue;
            System.out.println(process(line));
        }
    }

    private String process(String line){
        String[] parts = line.split("\\|");

        try {
            switch (parts[0]) {
                case "ADD_BOOK":

                    library.addItem(new Book(parts[1], parts[2], parts[3]));
                    return "OK";

                case "ADD_MAGAZINE":

                    library.addItem(new Magazine(parts[1], parts[2], parts[3]));
                    return "OK";

                case "ADD_REFERENCE":

                    library.addItem(new ReferenceBook(parts[1], parts[2], parts[3]));
                    return "OK";

                case "ADD_MEMBER":

                    library.addMember(createMember(parts[1], parts[2], parts[3]));
                    return "OK";

                case "BORROW":

                    return "OK due on day " + library.borrow(parts[1], parts[2]);

                case "RETURN":

                    return formatReturn(library.returnItem(parts[1]));

                case "RESERVE":

                    library.reserve(parts[1], parts[2]);
                    return "OK reserved";

                case "ADVANCE":

                    return "OK today is day " + library.advance(Integer.parseInt(parts[1]));

                case "SEARCH":

                    return formatSearch(library.search(parts[1]));

                case "LOANS":

                    return formatLoans(library.loansOf(parts[1]));

                case "OVERDUE":

                    return formatOverdue(library.overdue());

                case "INVENTORY":

                    return formatInventory(library.inventory());

                default:

                    throw new IllegalArgumentException("unknown command " + parts[0]);
            }
        } catch (LibraryException e) {
            return "ERROR " + e.getMessage();
        }
    }

    private Member createMember(String id, String name, String type){
        switch (type) {

            case "STUDENT": return new Student(id, name);
            case "STAFF":   return new Staff(id, name);
            case "FACULTY": return new Faculty(id, name);

            default:
                throw new IllegalArgumentException("unknown member type " + type);
        }
    }

    private String formatReturn(ReturnResult r){

        String out = "OK fine " + r.getFine();
        if(r.hasReissue()){
            out += ", issued to " + r.getIssuedTo().getId() + " due on day " + r.getNewDueDay();
        }

        return out;
    }

    

    private String formatSearch(List<LibraryItem> items){
        List<String> rows = new ArrayList<>();
        for(LibraryItem item : items){
            rows.add(item.getId() + "|" + item.getTypeName() + "|" + item.getTitle());
        }
        return joinRows(rows);
    }

    private String formatLoans(List<Borrowable> items){

        List<String> rows = new ArrayList<>();
        for(Borrowable b : items){

            rows.add(b.getId() + "|" + b.getTitle() + "|" + b.getDueDay());
        }
        return joinRows(rows);
    }

    private String formatOverdue(List<Borrowable> items){

        int today = library.getToday();
        List<String> rows = new ArrayList<>();
        for(Borrowable b : items){

            int daysLate = today - b.getDueDay();
            rows.add(b.getId() + "|" + b.getBorrower().getId() + "|" + daysLate + "|" + b.fineAsOf(today));
        }

        return joinRows(rows);
    }

    private String formatInventory(Collection<LibraryItem> items){
        List<String> rows = new ArrayList<>();
        for(LibraryItem item : items){

            rows.add(item.getId() + "|" + item.getTypeName() + "|" + item.getStatus());
        }

        return joinRows(rows);
    }

    private String joinRows(List<String> rows){
        
        if(rows.isEmpty()) return "NONE";
        return String.join("\n", rows);
    }
}

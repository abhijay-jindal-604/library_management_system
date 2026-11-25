class LibraryException extends Exception {
    public LibraryException(String message) {
        super(message);
    }
}


class ItemNotFoundException extends LibraryException {
    public ItemNotFoundException(String id) {
        super("item " + id + " not found");
    }
}

class MemberNotFoundException extends LibraryException {
    public MemberNotFoundException(String id) {
        super("member " + id + " not found");
    }
}


class ItemNotAvailableException extends LibraryException {
    public ItemNotAvailableException(String message) {
        super(message); // super(message) as two different outputs required according to whether not borrowable or on loan
    }
}

class ItemNotOnLoanException extends LibraryException {
    public ItemNotOnLoanException(String id) {
        super( id +  " is not on loan");
    }
}

class ReservationNotAllowedException extends LibraryException {
    public ReservationNotAllowedException(String message) {
        super(message);
    }
}

class BorrowLimitExceededException extends LibraryException {
    public BorrowLimitExceededException(String id , int max) {
        super(id + " has reached the limit of " + max);
    }
}

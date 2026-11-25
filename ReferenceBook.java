class ReferenceBook extends LibraryItem {

    private String author;

    ReferenceBook(String id, String title, String author) {
        super(id, title);
        this.author = author;
    }

    @Override
    public String getTypeName() {
        return "REFERENCE";
    }

    @Override
    public String getStatus() {
        return "NOT_BORROWABLE";
    }
}
public class Book {
    private String title; // store the title of the book
    private String ISBN; // store the ISBN of the book
    private boolean available; // keep the status of whether the book is available;
    // initially should be true
    private MyQueue<String> reservedQueue; // store the queue of waiting list

    public Book() {
        available = true;
        reservedQueue = new MyQueue<>();
    }

    public void setISBN(String s) {
        ISBN = s;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAvailable(boolean b) {
        available = b;
    }

    public MyQueue<String> getReservedQueue() {

        return reservedQueue;
    }

    public boolean isAvailable() {
        return available;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getTitle() {
        return title;
    }
}

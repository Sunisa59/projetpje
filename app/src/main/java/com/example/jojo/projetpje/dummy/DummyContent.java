package com.example.jojo.projetpje.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Book> ITEMS = new ArrayList<Book>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Book> ITEM_MAP = new HashMap<String, Book>();

    private static final int COUNT = 25;
/**
    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }
*/
    public void addItem(Book item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public void remove(int i){
        ITEMS.remove(i);
    }
/**
    private static Book createDummyItem(int position) {
        return new Book(String.valueOf(position), "Item " + position, "jojo","12346");
    }
*/
    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class Book {
        public String id;
        public String author;
        public String title;
        public String isbn;

        public Book( String isbn, String title, String author){
            this.author = author;
            this.title = title;
            this.isbn =isbn;
        }

        public Book(){

        }

        public String toString(){
            return " Author : " + this.author+"\n Book title : "+this.title+"\n ISBN : "+this.isbn;
        }


        public String getId() {
            return id;
        }

        public void setId(String id) {
           this.id=id;
        }

        public String getIsbn() {
            return isbn;
        }

        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }




    }
}

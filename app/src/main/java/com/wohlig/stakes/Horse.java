package com.wohlig.stakes;

import com.orm.SugarRecord;

/**
 * Created by Jay on 31-08-2015.
 */
public class Horse extends SugarRecord {

    public int bookId;
    public String horseName;
    public double total;

    public Horse() {
        super();
    }

    public Horse(int bookId, String horseName, double total) {
        super();
        this.bookId = bookId;
        this.horseName = horseName;
        this.total = total;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getHorseName() {
        return horseName;
    }

    public void setHorseName(String horseName) {
        this.horseName = horseName;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}

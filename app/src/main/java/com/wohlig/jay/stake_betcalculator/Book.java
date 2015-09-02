package com.wohlig.jay.stake_betcalculator;

import com.orm.SugarRecord;

/**
 * Created by Jay on 31-08-2015.
 */
public class Book extends SugarRecord {

    public String bookName;
    public String date;

    public Book() {
        super();
    }

    public Book(String bookName, String date) {

        super();
        this.bookName = bookName;
        this.date = date;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

package com.wohlig.stakes;

import com.orm.SugarRecord;

/**
 * Created by Jay on 31-08-2015.
 */
public class Bet extends SugarRecord {

    public int bookId;
    public int favorite;
    public int backlay;
    public double stake;
    public double odds;
    public long timestamp;

    public Bet() {
        super();
    }

    public Bet(int bookId, int favorite, int backlay, double stake, double odds, long timestamp) {
        super();
        this.bookId = bookId;
        this.favorite = favorite;
        this.backlay = backlay;
        this.stake = stake;
        this.odds = odds;
        this.timestamp = timestamp;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public int getBacklay() {
        return backlay;
    }

    public void setBacklay(int backlay) {
        this.backlay = backlay;
    }

    public double getStake() {
        return stake;
    }

    public void setStake(double stake) {
        this.stake = stake;
    }

    public double getOdds() {
        return odds;
    }

    public void setOdds(double odds) {
        this.odds = odds;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

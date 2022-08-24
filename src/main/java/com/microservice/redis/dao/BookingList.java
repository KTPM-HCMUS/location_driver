package com.microservice.redis.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BookingList implements Serializable {
    private List<BookingItem> bookingItems;

    public BookingList(){
        bookingItems = new ArrayList<>();
    }

    public BookingList(List<BookingItem> bookingItems) {
        this.bookingItems = bookingItems;
    }

    public List<BookingItem> getBookingItems() {
        return bookingItems;
    }

    public void setBookingItems(List<BookingItem> bookingItems) {
        this.bookingItems = bookingItems;
    }
}

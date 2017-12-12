package com.example.dell.dacn.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DELL on 18/10/17.
 */

public class Entry {

    @SerializedName("entry")
    @Expose
    private List<News> entry;

    public Entry(List<News> entry) {
        this.entry = entry;
    }

    public List<News> getEntry() {
        return entry;
    }

    public void setEntry(List<News> entry) {
        this.entry = entry;
    }

}

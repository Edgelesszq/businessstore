package com.businessstore.model;

import java.util.Objects;

public class SearchHistory {
    private String history;

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public SearchHistory(String history) {
        this.history = history;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchHistory history1 = (SearchHistory) o;
        return Objects.equals(history, history1.history);
    }

    @Override
    public int hashCode() {

        return Objects.hash(history);
    }
    //    public int hashCode(){
//        return 1;
//    }
//    public boolean equals(Object b){
//        return true;
//    }
}

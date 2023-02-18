package model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class WalletDate {

    private LocalDate date;

    private int deposit;
    
    private String content;

    public LocalDate getDate() {
        return date;
    }

    public int getDeposit() {
        return deposit;
    }

    public String getContent() {
        return content;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "WalletDate{" +
                "date=" + date +
                ", deposit=" + deposit +
                ", content='" + content + '\'' +
                '}';
    }
}

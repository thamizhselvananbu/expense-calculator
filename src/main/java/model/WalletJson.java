package model;

import java.util.List;

public class WalletJson {

    private String date;

    private int totalIncome;

    private int totalExpenditure;

    private List<Transactions> transactions;

    public WalletJson(String date, int totalIncome, int totalExpenditure, List<Transactions> transactions) {
        this.date = date;
        this.totalIncome = totalIncome;
        this.totalExpenditure = totalExpenditure;
        this.transactions = transactions;
    }

    public String getDate() {
        return date;
    }

    public int getTotalIncome() {
        return totalIncome;
    }

    public int getTotalExpenditure() {
        return totalExpenditure;
    }

    public List<Transactions> getTransactions() {
        return transactions;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTotalIncome(int totalIncome) {
        this.totalIncome = totalIncome;
    }

    public void setTotalExpenditure(int totalExpenditure) {
        this.totalExpenditure = totalExpenditure;
    }

    public void setTransactions(List<Transactions> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "WalletJson{" +
                "date='" + date + '\'' +
                ", totalIncome=" + totalIncome +
                ", totalExpenditure=" + totalExpenditure +
                ", transactions=" + transactions +
                '}';
    }
}

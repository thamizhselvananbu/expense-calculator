package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class WalletJson {

    private String period;

    @JsonProperty("total_income")
    private int totalIncome;

    @JsonProperty("total_expenditure")
    private int totalExpenditure;

    @JsonProperty("transactions")
    private List<Transactions> walletDates;

    public WalletJson() {

    }

    public WalletJson(String period, int totalIncome, int totalExpenditure, List<Transactions> walletDates) {
        this.period = period;
        this.totalIncome = totalIncome;
        this.totalExpenditure = totalExpenditure;
        this.walletDates = walletDates;
    }

    public String getPeriod() {
        return period;
    }

    public int getTotalIncome() {
        return totalIncome;
    }

    public int getTotalExpenditure() {
        return totalExpenditure;
    }

    public List<Transactions> getWalletDates() {
        return walletDates;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setTotalIncome(int totalIncome) {
        this.totalIncome = totalIncome;
    }

    public void setTotalExpenditure(int totalExpenditure) {
        this.totalExpenditure = totalExpenditure;
    }

    public void setWalletDates(List<Transactions> walletDates) {
        this.walletDates = walletDates;
    }

    @Override
    public String toString() {
        return "WalletJson{" +
                "period='" + period + '\'' +
                ", totalIncome=" + totalIncome +
                ", totalExpenditure=" + totalExpenditure +
                ", walletDates=" + walletDates +
                '}';
    }
}

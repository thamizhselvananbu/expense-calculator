package model;

public class Transactions {

    private String date;

    private int amount;

    private String content;

    public Transactions() {

    }

    public Transactions(String date, int amount, String content) {
        this.date = date;
        this.amount = amount;
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public int getAmount() {
        return amount;
    }

    public String getContent() {
        return content;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Transactions{" +
                "date='" + date + '\'' +
                ", amount=" + amount +
                ", content='" + content + '\'' +
                '}';
    }
}

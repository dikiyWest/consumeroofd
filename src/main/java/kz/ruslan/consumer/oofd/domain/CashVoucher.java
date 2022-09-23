package kz.ruslan.consumer.oofd.domain;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public final class CashVoucher {
    private String companyName;
    private String IIN;
    private String FP;
    private LocalDateTime date;
    private Double totalSum;
    private List<Purchase> items;


    public CashVoucher() {
    }

    public CashVoucher(CashVoucher o) {
        this.companyName = o.companyName;
        this.IIN = o.IIN;
        this.FP = o.FP;
        this.date = o.date;
        this.totalSum = o.totalSum;
        this.items = new ArrayList<Purchase>();
        for (Purchase item : o.getItems()) {
            this.items.add(new Purchase(item));
        }
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIIN() {
        return IIN;
    }

    public void setIIN(String IIN) {
        this.IIN = IIN;
    }

    public String getFP() {
        return FP;
    }

    public void setFP(String FP) {
        this.FP = FP;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyyHH:mm");
        this.date = LocalDateTime.parse(date, formatter);
    }

    public Double getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(Double totalSum) {
        this.totalSum = totalSum;
    }

    public List<Purchase> getItems() {
        return items;
    }

    public void setItems(List<Purchase> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "CashVoucher{" +
                "companyName='" + companyName + '\'' +
                ", IIN='" + IIN + '\'' +
                ", FP='" + FP + '\'' +
                ", date=" + date +
                ", totalSum=" + totalSum +
                ", items=" + items +
                '}';
    }
}

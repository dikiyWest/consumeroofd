package kz.ruslan.consumer.oofd.domain;


import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
public final class CashVoucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyName;
    private String IIN;
    private String FP;
    private LocalDateTime date;
    private Double totalSum;


    @ElementCollection(targetClass = Purchase.class, fetch = FetchType.EAGER)
    private List<Purchase> purchases;

    @CreationTimestamp
    private LocalDateTime createdDate;


    public CashVoucher() {
    }

    public CashVoucher(CashVoucher o) {
        this.companyName = o.companyName;
        this.IIN = o.IIN;
        this.FP = o.FP;
        this.date = o.date;
        this.totalSum = o.totalSum;
        this.purchases = new ArrayList<Purchase>();
        for (Purchase item : o.getPurchases()) {
            this.purchases.add(new Purchase(item));
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
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

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    @Override
    public String toString() {
        return "CashVoucher{" +
                "companyName='" + companyName + '\'' +
                ", IIN='" + IIN + '\'' +
                ", FP='" + FP + '\'' +
                ", date=" + date +
                ", totalSum=" + totalSum +
                ", purchases=" + purchases +
                '}';
    }
}

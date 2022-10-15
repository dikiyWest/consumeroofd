package kz.ruslan.consumer.oofd.domain;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;


@Embeddable
public class Purchase {

    private String name;
    private Double cost;
    private Double count;
    private Double totalCost;


    public Purchase() {
    }

    public Purchase(Purchase p) {
        this.name = p.name;
        this.cost = p.cost;
        this.count = p.count;
        this.totalCost = p.totalCost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "name='" + name + '\'' +
                ", cost=" + cost +
                ", count=" + count +
                ", totalCost=" + totalCost +
                '}';
    }
}

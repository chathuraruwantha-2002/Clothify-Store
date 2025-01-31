package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Order {
    private int orderId;
    private double subTotal;
    private double discount;
    private double tax;
    private double grandTotal;
    private String isReturned;
    private String paymentType;
    private String date;
    private int empId;
    private int custId;

    public Order(int orderId, double subTotal, double discount, double tax, String isReturned, String paymentType, String date, int empId, int custId) {
        this.orderId = orderId;
        this.subTotal = subTotal;
        this.discount = discount;
        this.tax = tax;
        this.isReturned = isReturned;
        this.paymentType = paymentType;
        this.date = date;
        this.empId = empId;
        this.custId = custId;
        this.grandTotal = subTotal - discount + tax;
    }


}



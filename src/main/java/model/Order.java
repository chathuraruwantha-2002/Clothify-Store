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
    private double totalAmount;
    private int isReturned;
    private String paymentType;
    private String date;
    private int empId;
    private int custId;
}

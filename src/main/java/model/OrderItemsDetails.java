package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderItemsDetails {

    private int productId;
    private String productName;
    private int qty;
    private double unitPrice;
    private double totalPrice;
}

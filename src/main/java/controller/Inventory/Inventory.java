package controller.Inventory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Inventory {
    private int productId;
    private String productName;
    private int supplierId;
    private String categoryName;
    private int quantityInStock;
    private String lastRestockedDate;
}

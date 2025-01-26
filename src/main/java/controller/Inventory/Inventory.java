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

    public String getProductId() {
        return "P" + String.format("%03d", productId);
    }
    public int getProductID() {
        return productId;
    }
    public String getSupplierId() {
        return "S" + String.format("%03d", supplierId);
    }
    public int getSupplierID() {
        return supplierId;
    }
}

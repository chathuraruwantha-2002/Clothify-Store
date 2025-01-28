package controller.Products;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {

    private int ProductID;
    private String name;
    private String size;
    private String imageUrl;
    private double price;
    private int categoryId;
    private int userId;
    private int inventoryId;
    private int supplierId;
    private String categoryName;
    private String supplierName;
    private int qty;

}

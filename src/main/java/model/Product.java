package model;

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

    public Product (int ProductID, String name, String  size, String imageUrl, double price, int categoryId, int userId, int supplierId, int qty ){
        this.ProductID = ProductID;
        this.name = name;
        this.size = size;
        this.imageUrl = imageUrl;
        this.price = price;
        this.categoryId = categoryId;
        this.userId = userId;
        this.supplierId = supplierId;
        this.qty = qty;
    }

}

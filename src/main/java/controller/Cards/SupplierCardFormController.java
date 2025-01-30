package controller.Cards;

import controller.Suppliers.SuppliersFormController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Product;
import model.Supplier;

public class SupplierCardFormController {


    @FXML
    private ImageView SupplierImage;

    @FXML
    private Label SupplierName;

    @FXML
    private Label SupplierPhno;
    private SuppliersFormController suppliersFormController;
    private Supplier supplier;

    public boolean setData(Supplier supplier, SuppliersFormController suppliersFormController) {
        this.suppliersFormController = suppliersFormController;
        this.supplier = supplier;
        SupplierName.setText(supplier.getName());
        SupplierPhno.setText(supplier.getPhone());

        if (supplier.getGender().equals("Male")) {
            SupplierImage.setImage(new ImageView("/img/man.png").getImage());
        }else{
            SupplierImage.setImage(new ImageView("/img/woman.png").getImage());
        }
        return (supplier != null && suppliersFormController != null ? true : false);
    }
    public void CardData(MouseEvent mouseEvent) {

    }

}

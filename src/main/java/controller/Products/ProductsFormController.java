package controller.Products;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.Cards.ProductCardController;
import controller.Suppliers.SuppliersController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import model.Product;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProductsFormController implements Initializable {

    @FXML
    private ImageView btnAddNewProduct;

    @FXML
    public AnchorPane productDetailsarea;

    @FXML
    public AnchorPane overlayPane;

    @FXML
    private JFXComboBox CategorySideView;

    @FXML
    private JFXTextField PriceSideView;

    @FXML
    private Label ProductIDSideView;

    @FXML
    private ImageView ProductImgSideView;

    @FXML
    private JFXTextField ProductNameSideView;

    @FXML
    private JFXTextField QtySideView;

    @FXML
    private JFXComboBox SizeSideView;

    @FXML
    private JFXTextField fieldSearchProducts;

    @FXML
    private GridPane ProductsContainer;

    @FXML
    private JFXComboBox SupllierNameSideView;

    private Product product;

    private String category;




    @Override
    public void initialize(URL location, ResourceBundle resources) {


        LoadGridCards(new ProductsController().CategoryProductsList("Gents"));
        category = "Gents";
        clearform();

        // ComboBox setup for Category and Size
        ObservableList<String> categoryValues = FXCollections.observableArrayList("Ladies", "Gents", "Kids");
        CategorySideView.setItems(categoryValues);

        ObservableList<String> sizeValues = FXCollections.observableArrayList("S", "M", "L", "XL", "XXL");
        SizeSideView.setItems(sizeValues);

        //load all suppliers to the combobox
        SupllierNameSideView.setItems(FXCollections.observableArrayList(new SuppliersController().getAllSupplierNames()));
    }

    public void viewProductDetailsSide(Product product){
        this.product = product;

        // image
        Image image = new Image(getClass().getResourceAsStream(product.getImageUrl()));
        ProductImgSideView.setImage(image);
        // image size
       // ProductImgSideView.setFitWidth(167);
       // ProductImgSideView.setFitHeight(248);

        // position the image
       // ProductImgSideView.setLayoutX(74);  //  X position (horizontal)
       // ProductImgSideView.setLayoutY(74);  //  Y position (vertical)


        ProductIDSideView.setText(String.format("P%03d", product.getProductID()));
        ProductNameSideView.setText(product.getName());
        CategorySideView.setValue(product.getCategoryName());
        SupllierNameSideView.setValue(product.getSupplierName());
        QtySideView.setText(String.valueOf(product.getQty()));
        SizeSideView.setValue(product.getSize());
        PriceSideView.setText(String.valueOf(product.getPrice()));

    }

    public void FSearchProducts(KeyEvent keyEvent) {
        LoadGridCards(new ProductsController().searchProduct(fieldSearchProducts.getText(),category));
        System.out.println(category);
    }

    public void LoadGridCards(List<Product> productlist){

        ProductsContainer.getChildren().clear();

        int column = 0;
        int row = 1;

        try{
            for (Product product :productlist) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/view/ProductCard.fxml"));
                VBox productCard = fxmlLoader.load();
                ProductCardController productcardController = fxmlLoader.getController();
                boolean result = productcardController.setData(product,this);

                if(result){
                    if(column == 4){
                        column = 0;
                        ++row;
                    }
                    ProductsContainer.add(productCard,column++,row);
                    GridPane.setMargin(productCard,new Insets(5.5));
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void btnupdate(MouseEvent mouseEvent) {
        product.setName(ProductNameSideView.getText());
        product.setCategoryId(CategorySideView.getSelectionModel().getSelectedIndex()+1);
        product.setSupplierName((String) SupllierNameSideView.getValue());
        product.setSupplierId(new SuppliersController().getSupplierIdByName((String) SupllierNameSideView.getValue()));
        product.setQty(Integer.parseInt(QtySideView.getText()));
        product.setSize((String) SizeSideView.getValue());
        product.setPrice(Double.parseDouble(PriceSideView.getText()));
        if (product.getImageUrl() != null) {
            product.setImageUrl(product.getImageUrl());
        }

        boolean result = false;
        try {
            result = new ProductsController().updateProduct(product);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (result) {
            LoadGridCards(new ArrayList<>(new ProductsController().CategoryProductsList((String) CategorySideView.getValue())));
            new Alert(Alert.AlertType.INFORMATION,"Product Updated Successfully.!!").show();
        } else {
            new Alert(Alert.AlertType.ERROR,"Product Updation Failed.!!").show();
        }
        clearform();
    }

    public void btndelete(MouseEvent mouseEvent) {
        boolean result = false;
        try {
            result = new ProductsController().deleteProduct(product);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(result){
            LoadGridCards(new ArrayList<>(new ProductsController().CategoryProductsList((String) CategorySideView.getValue())));
            new Alert(Alert.AlertType.INFORMATION,"Product Deleted Successfully.!!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Product Deletion Failed.!!").show();
        }
        clearform();
    }

    @FXML
    private void loadAddProductForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddNewProduct.fxml"));
            Parent newForm = loader.load();

            AddNewProductFormController addProductFormController = loader.getController();

            addProductFormController.setParentController(this);


            overlayPane.getChildren().clear();
            overlayPane.getChildren().add(newForm);

            overlayPane.setVisible(true);
            overlayPane.setMouseTransparent(false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearform(){
        ProductIDSideView.setText("");
        ProductNameSideView.setText("");
        CategorySideView.setValue("");
        SupllierNameSideView.setValue("");
        QtySideView.setText("");
        SizeSideView.setValue("");
        PriceSideView.setText("");
    }

    public void btnfAddNewProduct(MouseEvent mouseEvent) {
        loadAddProductForm();
    }


    public void fKidsButton(MouseEvent mouseEvent) {
        LoadGridCards(new ProductsController().CategoryProductsList("Kids"));
        category = "Kids";
    }

    public void fGentsButton(MouseEvent mouseEvent) {
        LoadGridCards(new ProductsController().CategoryProductsList("Gents"));
        category = "Gents";
    }

    public void fLadiesButton(MouseEvent mouseEvent) {
        LoadGridCards(new ProductsController().CategoryProductsList("Ladies"));
        category = "Ladies";
    }

    public void btnUpdateImg(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String absolutePath = selectedFile.getAbsolutePath();
            System.out.println("Absolute file path: " + absolutePath);


            // Get the index where 'img\\products\\' starts
            int index = absolutePath.indexOf("img\\products\\");

            if (index != -1) {
                // Extract relative path from absolute path
                String relativePath = absolutePath.substring(index);

                // Replace backslashes with forward slashes
                relativePath = relativePath.replace("\\", "/");
                String finalPath = "/" + relativePath;

                System.out.println("Final path: " + finalPath);
                ProductImgSideView.setImage(new Image(finalPath));
                product.setImageUrl(finalPath);

            } else {
                System.out.println("folder not found in path!");
            }
        }
    }




}
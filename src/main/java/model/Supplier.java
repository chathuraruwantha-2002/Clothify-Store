package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Supplier {

    private int SupplierId;
    private int UserId;
    private String Gender;
    private String name;
    private String Company;
    private String Address;
    private String Phone;
    private String Email;
}

package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee {
    private int EmployeeId;
    private String Name;
    private String Contact;
    private String Email;
    private String Address;
    private int UserId;
    private String Role;
    private String Gender;

}

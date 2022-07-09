package pl.michal.rca.models;

import lombok.*;



@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Reimbursement {
    private int id;
    private int userId;
    private double receiptsValue;
    private int days;
    private int mileage;
    private  String acceptance = "In progress";
    private double total;
    private String username;

}

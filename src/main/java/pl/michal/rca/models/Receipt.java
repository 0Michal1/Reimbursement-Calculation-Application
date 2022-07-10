package pl.michal.rca.models;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Receipt {
    private int id;
    private int typeId;
    private double value;
    private int reimbursementId;
    private String name;
}

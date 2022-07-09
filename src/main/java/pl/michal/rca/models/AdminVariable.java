package pl.michal.rca.models;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AdminVariable {
    private int id;
    private String type;
    private String name;
    private Double value;
    private String state;

    public AdminVariable(String type,String name,Double value, String state){
        this.type = type;
        this.name = name;
        this.value = value;
        this.state = state;
    }
}

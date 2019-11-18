package be.julien.winterboots.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Entity
@Data
public class Product {

    public static final int NO_ID = -1;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private long previousId = NO_ID;
    private long nextId = NO_ID;

    @NotBlank
    private String name;

    @Digits(integer = 10, fraction = 2)
    @Min(0)
    private BigDecimal price;

}

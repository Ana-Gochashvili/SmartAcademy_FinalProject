package utils;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class OrderDataBuilder {
    private int id;
    private int petId;
    private int quantity;
    private Date shipDate;
    private String status;
    private boolean complete;
}

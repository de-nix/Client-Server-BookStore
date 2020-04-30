package ro.ubb.catalog.web.dto;

import lombok.*;

import javax.persistence.Entity;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class PurchaseDto extends BaseDto {
    private Long idClient;
    private Long idBook;
    private Date date;


}

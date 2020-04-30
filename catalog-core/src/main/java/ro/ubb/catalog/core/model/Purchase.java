package ro.ubb.catalog.core.model;

import lombok.*;

import javax.persistence.Entity;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class Purchase extends BaseEntity<Long> {
    private Long idClient;
    private Long idBook;
    private Date date;


}

package ro.ubb.catalog.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Purchase;
import ro.ubb.catalog.web.dto.PurchaseDto;
@Component
public class PurchaseConverter extends BaseConverter<Purchase, PurchaseDto> {
    @Override
    public Purchase convertDtoToModel(PurchaseDto dto) {
        Purchase purchase = Purchase.builder().idBook(dto.getIdBook()).idClient(dto.getIdClient()).date(dto.getDate()).build();
        purchase.setId(dto.getId());
        return purchase;
    }

    @Override
    public PurchaseDto convertModelToDto(Purchase purchase) {
        PurchaseDto purchaseDto = PurchaseDto.builder().idBook(purchase.getIdBook()).idClient(purchase.getIdClient()).date(purchase.getDate()).build();
        purchaseDto.setId(purchase.getId());
        return purchaseDto;
    }
}

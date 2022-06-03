package api.soldout.io.soldout.service.sell;


import api.soldout.io.soldout.dtos.entity.SaleDto;
import api.soldout.io.soldout.repository.sell.SaleRepository;
import api.soldout.io.soldout.service.sell.command.SaleBidCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * .
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

  private final SaleRepository saleRepository;

  /**
   * 판매 입찰 등록을 위한 엔티티 객체 생성.
   * .@param command
   */

  public void saleBid(SaleBidCommand command) {

    SaleDto saleDto = SaleDto.builder()
        .userId(command.getUserId())
        .productId(command.getProductId())
        .size(command.getSize())
        .price(command.getPrice())
        .type(command.getType())
        .day(command.getPeriod())
        .build();

    saleRepository.saveSale(saleDto);

  }


}

package api.soldout.io.soldout.service.product;

import api.soldout.io.soldout.dtos.entity.ProductDto;
import api.soldout.io.soldout.service.product.command.AddProductCommand;
import java.util.List;

/**
 * .
 */

public interface ProductService {

  void addProduct(AddProductCommand command);

  List<ProductDto> findAll();

}

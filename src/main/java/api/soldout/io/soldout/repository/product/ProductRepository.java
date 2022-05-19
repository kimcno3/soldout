package api.soldout.io.soldout.repository.product;

import api.soldout.io.soldout.dtos.ProductDto;
import java.util.List;

/**
 * .
 */

public interface ProductRepository {

  ProductDto save(ProductDto product);

  List<ProductDto> findAll();

}

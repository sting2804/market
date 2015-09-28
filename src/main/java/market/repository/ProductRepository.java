package market.repository;

import market.domain.Product;
import org.springframework.data.repository.Repository;

public interface ProductRepository extends Repository<Product, Long> {
}

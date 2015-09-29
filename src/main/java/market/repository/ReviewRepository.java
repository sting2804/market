package market.repository;

import market.domain.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ReviewRepository extends CrudRepository<Review, Long>, PagingAndSortingRepository<Review, Long> {
}

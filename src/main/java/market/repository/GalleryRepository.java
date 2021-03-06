package market.repository;


import market.domain.Gallery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GalleryRepository extends CrudRepository<Gallery, Long>, PagingAndSortingRepository<Gallery, Long> {
}

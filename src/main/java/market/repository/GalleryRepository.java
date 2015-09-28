package market.repository;


import market.domain.Gallery;
import org.springframework.data.repository.CrudRepository;

public interface GalleryRepository extends CrudRepository<Gallery, Long> {
}

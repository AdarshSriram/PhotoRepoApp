package api.photorepoapp.photorepoappapi.filestore;

import api.photorepoapp.photorepoappapi.models.Image;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepo extends JpaRepository<Image, UUID> {
	Optional<Image> findByName(String name);
	
	Optional<Image> findById(UUID id);
	
	Optional<Image> findByUserId(UUID userId);
}

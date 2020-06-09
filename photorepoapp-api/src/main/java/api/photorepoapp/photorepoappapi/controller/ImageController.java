package api.photorepoapp.photorepoappapi.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



import java.net.URI;

import api.photorepoapp.photorepoappapi.filestore.AmazonStore;
import api.photorepoapp.photorepoappapi.filestore.ImageRepo;
import api.photorepoapp.photorepoappapi.models.Image;
import imageBucket.Bucket;




@RestController
@RequestMapping
public class ImageController {
	
@Autowired
private ImageRepo imageRepo;

@Autowired
private final AmazonStore amazonStore;
	

public ImageController(AmazonStore amazonStore) {
		
		this.amazonStore = amazonStore;
	}

@GetMapping("/gallery")
public Collection<Image> getGallery(){
	return imageRepo.findAll();
}


@GetMapping("/gallery/{userId}")
public ResponseEntity<Image> getImagebyId(@PathVariable UUID userId ){
	Optional<Image> image = imageRepo.findByUserId(userId);
	
	return image.map(response -> ResponseEntity.ok().body(response))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
}


@PostMapping(path = "/upload/{userId}", 
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE
			)
public ResponseEntity<Image> uploadImage(@PathVariable UUID userId, 
										String imageName, 
										@RequestParam("imageFile") MultipartFile file 
										) {
	
	if (file.isEmpty()) {throw new IllegalStateException("Cannot upload empty file");}
	
	Map<String, String> metaData = new HashMap<>();
	metaData.put("Content-Type", file.getContentType());
	metaData.put("Content-Length", String.valueOf(file.getSize()));
	
	String path = Bucket.USER_IMAGE.getBucketName()+"/" + userId.toString();
	UUID imgId = UUID.randomUUID();
	String filename = file.getName()+ "-"+ imgId.toString();
	

	
	try {
	amazonStore.save(path, filename, Optional.of(metaData), file.getInputStream());

	Image img = new Image (imgId, imageName, userId, filename);
		
		return ResponseEntity.created(new URI("/images/" + img.getId()))
	            .body(img);
	}
	catch(Exception e) {
		System.out.println("Could not save file"); 
		return ResponseEntity.notFound().build();
	}
	
	
}



}
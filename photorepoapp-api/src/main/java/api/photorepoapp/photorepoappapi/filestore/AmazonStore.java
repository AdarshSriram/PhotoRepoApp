package api.photorepoapp.photorepoappapi.filestore;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

@Service
public class AmazonStore {
	
	private  final AmazonS3 s3;
	
	@Autowired
	public AmazonStore(AmazonS3 s3) {
		this.s3 = s3;
	}

	public void save(String path, 
			String filename, 
			Optional<Map<String, 
			String>> optionMetaData, 
			InputStream inputstream) {
		
		ObjectMetadata objectMetaData = new ObjectMetadata();
		
		optionMetaData.ifPresent(map->{
			if (!map.isEmpty()) {
				map.forEach((key,  value)-> objectMetaData.addUserMetadata(key, value));
				}
			}
		);	
		
		try {
			s3.putObject(path, filename, inputstream, objectMetaData);
		}
		catch(AmazonServiceException e){
			throw new IllegalStateException("Could not store file to S3 Bucket");
			}
		
	}
}

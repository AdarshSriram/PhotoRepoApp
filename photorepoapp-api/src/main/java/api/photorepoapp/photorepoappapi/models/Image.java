package api.photorepoapp.photorepoappapi.models;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.*;

import org.springframework.data.annotation.Id;

public class Image {
	
	@Id
	@GeneratedValue
	private UUID id;
	
	private String name; 
	
	@GeneratedValue
	private UUID userId;
	
	private String imageUrl;
	
	public UUID getId() {
		return id;
	}


	public void setId(UUID id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public UUID getUserId() {
		return userId;
	}


	public void setUserId(UUID userId) {
		this.userId = userId;
	}


	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	@Override
	public int hashCode() {
		return Objects.hash(id, imageUrl, name, userId);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Image))
			return false;
		Image other = (Image) obj;
		return Objects.equals(id, other.id) && Objects.equals(imageUrl, other.imageUrl)
				&& Objects.equals(name, other.name) && Objects.equals(userId, other.userId);
	}


	public Image(UUID id, String name, UUID userId, String imageUrl) {
		super();
		this.id = id;
		this.name = name;
		this.userId = userId;
		this.imageUrl = imageUrl;
	}


	
	
	
}
package com.sgcore.sgProducts.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sgcore.sgProducts.dto.ProductDto;
import com.sgcore.sgProducts.entity.ProductEntity;
import com.sgcore.sgProducts.repository.ProductRepo;

@Service
public class ProductService {

	@Autowired
	ProductRepo productRepo;
	// method to upload product to data base
	public ResponseEntity<String> addProduct(String name,String description,boolean show,MultipartFile image ) throws IOException
	{		

		
			ProductEntity servicesEntity = new ProductEntity();
			servicesEntity.setProductName(name);
			servicesEntity.setProductDescription(description);
			servicesEntity.setProductShow(show);
			if (image != null && !image.isEmpty()) {
	            byte[] imageBytes = image.getBytes(); // Read file as bytes
	            servicesEntity.setProductImage(imageBytes);
	        } else {
	            return ResponseEntity.badRequest().body("Image path is missing or invalid");
	        }
			Optional<ProductEntity> result =Optional.of(productRepo.save(servicesEntity)) ;
			 if(result.isPresent())
		            return ResponseEntity.status(HttpStatus.CREATED).body("product uploaded successfully");
				else
		            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload product");
		
		
		
	}

	// method to get all the products from data base and map to productDto
	public ResponseEntity<List<ProductDto>> getProducts() {
		Optional<List<ProductEntity>>  result =Optional.of(productRepo.findAll()) ;
		if(result.isPresent())
		{
			List<ProductDto> productDto= result.get().stream().map(product->{
				ProductDto dto = new ProductDto();
				dto.setProductName(product.getProductName());
				dto.setProductDescription(product.getProductDescription());
				dto.setProductId(product.getProductId());
				dto.setProductShow(product.isProductShow());
				dto.setProductImage("http://localhost:8088/SGPRODUCTS/image/" +product.getProductId());
				return dto;
			}).toList();
			 return ResponseEntity.status(HttpStatus.OK).body(productDto);
		}
           
	
		else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	//method to get the image  from data base 
	public ResponseEntity<byte[]> findById(int id) {
		Optional<ProductEntity> product = productRepo.findById(id);
		
		if(product.isPresent())
		{
			System.out.println("sending ");
			 return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // Adjust to IMAGE_PNG or other type if needed
	                .body(product.get().getProductImage());
		}
		else
		{
			System.out.println("notsending");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(product.get().getProductImage());
		}
	}

	// method to get selected product from data base and map to productDto
	public ResponseEntity<List<ProductDto>> getSelectedProducts() {
		Optional<List<ProductEntity>>  result =Optional.of(productRepo.findByProductShow(true)) ;
		if(result.isPresent())
		{
			 // Convert entities to DTOs with image URLs
		    List<ProductDto> servicesDtos = result.get().stream().map(product -> {
		    	ProductDto dto = new ProductDto();
		        dto.setProductId(product.getProductId());
		        dto.setProductName(product.getProductName());
		        dto.setProductDescription(product.getProductDescription());
		        dto.setProductShow(product.isProductShow());
		       
		        dto.setProductImage("http://localhost:8088/SGPRODUCTS/image/" + product.getProductId()); // Construct image URL
		        System.out.println( dto.getProductImage());
		        return dto;
		    }).toList();
		    return ResponseEntity.status(HttpStatus.CREATED).body(servicesDtos);
		}
		return null;
	}

	public ResponseEntity<String> updateShow(int id, ProductDto productDto) {
		Optional<ProductEntity> result =productRepo.findById(id) ;
		System.out.println(productDto.isProductShow());
		if(result.isPresent())
		{
			result.get().setProductShow(productDto.isProductShow());
			Optional<ProductEntity>  productEntity =Optional.of(productRepo.save(result.get()) );
			if (productEntity.isPresent())
			{
				 return ResponseEntity.status(HttpStatus.OK).body("update successfull");
			}
			else
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("update fail");
				
			
		}
		
		 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("record not found");
	}

	public ResponseEntity<String> updateProduct(int productId, String productName, String productDescription,
			Boolean productShow, MultipartFile productImage) throws IOException {
		
		Optional<ProductEntity> present =productRepo.findById(productId);
		if(present.isPresent())
		{		
			System.out.println("yes is present");
			ProductEntity servicesEntity = new ProductEntity();
			servicesEntity.setProductId(productId);
			servicesEntity.setProductName(productName);
			servicesEntity.setProductDescription(productDescription);
			servicesEntity.setProductShow(productShow);
			if (productImage != null && !productImage.isEmpty()) {
	            byte[] imageBytes = productImage.getBytes(); // Read file as bytes
	            servicesEntity.setProductImage(imageBytes);
	        } else {
	            return ResponseEntity.badRequest().body("Image path is missing or invalid");
	        }
			Optional<ProductEntity> result =Optional.of(productRepo.save(servicesEntity)) ;
			 if(result.isPresent())
		            return ResponseEntity.status(HttpStatus.CREATED).body("product updated successfully");
				else
		            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload product");
		
		}

		else
		{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("product not found");

		}
	}

	public ResponseEntity<String> deleteProducts(List<Integer> productIds) {
		
		productRepo.deleteAllById(productIds);
		return ResponseEntity.status(HttpStatus.OK).body("successfuly deleted");
	}
	
}

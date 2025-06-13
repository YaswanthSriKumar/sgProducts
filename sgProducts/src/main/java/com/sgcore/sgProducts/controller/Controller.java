package com.sgcore.sgProducts.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sgcore.sgProducts.dto.ProductDto;
import com.sgcore.sgProducts.service.ProductService;

@RestController
public class Controller {

	@Autowired
	ProductService productService;
	
	// method to uplode a product
	@PostMapping("/uploadproduct")
	public ResponseEntity<String> uploadService( @RequestParam("productName") String productName,
	        @RequestParam("productDescription") String productDescription,
	        @RequestParam("productShow") Boolean productShow,
	        @RequestParam("productImage") MultipartFile productImage ) throws IOException
	{
		System.out.println("got in");
		System.out.println("service name"+productName);
		System.out.println("service image"+productImage);
		return productService.addProduct(productName,productDescription,productShow,productImage);
		
	}
	//method to get all the product
	@GetMapping("/getproducts")
	public ResponseEntity<List<ProductDto>> getServices()
	{
		System.out.println("got");
		return productService.getProducts();
	}
	//to get the image
	@GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable int id) {
    	System.out.println("second");
        return productService.findById(id);
    }
    // to get the selected products
    @GetMapping("/getSelected")
    public ResponseEntity<List<ProductDto>> getSelectedProducts()
    {
    	return productService.getSelectedProducts();
    }
    @PutMapping("/updateShow/{id}")
    public ResponseEntity<String> updateShow(@PathVariable int id, @RequestBody ProductDto productDto )
    {
    	System.out.println("got into update");
		System.out.println("inside controller: "+productDto.isProductShow());

    	return productService.updateShow(id,productDto);
    }
   @PutMapping("/updateproduct")
	public ResponseEntity<String> updateProduct( @RequestParam("productId") int productId,@RequestParam("productName") String productName,
	        @RequestParam("productDescription") String productDescription,
	        @RequestParam("productShow") Boolean productShow,
	        @RequestParam("productImage") MultipartFile productImage ) throws IOException
	{
		System.out.println("got in");
		System.out.println("service name"+productName);
		System.out.println("service image"+productImage);
		return productService.updateProduct(productId,productName,productDescription,productShow,productImage);
		
	}
   @DeleteMapping("deleteProducts/{productIds}")
   public ResponseEntity<String> deleteProducts(@PathVariable List<Integer> productIds)
   {
	return productService.deleteProducts(productIds);
	   
   }
}

package kr.ac.hansung.cse.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import kr.ac.hansung.cse.model.Product;
import kr.ac.hansung.cse.service.ProductService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private ProductService productService; // service layer 이용

	@RequestMapping
	public String adminPage() {
		return "admin"; // view의 logical name, tiles definition의 이름
	}

	@RequestMapping("/productInventory")
	public String getProducts(Model model) { // 상품 조회
		List<Product> products = productService.getProducts();

		model.addAttribute("products", products);

		return "productInventory";  // view's logical name
	}
	
	@RequestMapping(value="/productInventory/addProduct", method=RequestMethod.GET)  // get method 처리
	public String addProduct(Model model) {
		
		Product product = new Product();
		
		product.setCategory("컴퓨터");  // default
		
		model.addAttribute("product", product);
		
		return "addProduct";
	}

	// web form data -> object(filled with form data) spring이 객제 만들어서 객체에 값을 다 채워서 객체에 대한 주소값을 넘겨줌
	@RequestMapping(value="/productInventory/addProduct", method=RequestMethod.POST)  // post method 처리
	public String addProductPost(@Valid Product product, BindingResult result, HttpServletRequest request) {  // controller -> service -> dao (db접근)
		
		if (result.hasErrors()) {
			System.out.println("=== Web Form data has some errors ===");
			List<ObjectError> errors = result.getAllErrors();
			
			for(ObjectError error: errors) {
				System.out.println(error.getDefaultMessage());
			}
			return "addProduct";
		}
		
		MultipartFile productImage = product.getProductImage();
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");  // 배포시 webapp 밑이 패키징 될때 어디 디렉토리에 패키징 될지 알수없어서?? 
		Path savePath = Paths.get(rootDirectory + "\\resources\\images\\" + productImage.getOriginalFilename());  // full path
		
		if (productImage.isEmpty() == false) {
			System.out.println("---------------- file start ---------------");
			System.out.println("name : " + productImage.getName());
			System.out.println("filename : " + productImage.getOriginalFilename());
			System.out.println("size : " + productImage.getSize());
			System.out.println("savePath : " + savePath);
			System.out.println("----------------- file end ----------------\n");
		}
		
		if (productImage != null && !productImage.isEmpty()) {
			try {
				productImage.transferTo(new File(savePath.toString()));  // images 폴더에 저장하는 부분 ***
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		product.setImageFilename(productImage.getOriginalFilename());
		
		productService.addProduct(product);  // db에 저장
		
		return "redirect:/admin/productInventory";
	}
	
	@RequestMapping(value="/productInventory/deleteProduct/{id}", method=RequestMethod.GET)
	public String deleteProduct(@PathVariable int id, HttpServletRequest request) {  // spring에 의해 url에 있는 id값을 method 인자에 자동으로 넣어줌
		
		Product product = productService.getProductById(id);
		
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");  // 배포시 webapp 밑이 패키징 될때 어디 디렉토리에 패키징 될지 알수없어서?? 
		Path savePath = Paths.get(rootDirectory + "\\resources\\images\\" + product.getImageFilename());  // full path
		
		if (Files.exists(savePath)) {
			try {
				Files.delete(savePath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		productService.deleteProduct(product);
		
		return "redirect:/admin/productInventory";
	}
	
	@RequestMapping(value="/productInventory/updateProduct/{id}", method=RequestMethod.GET)
	public String updateProduct(@PathVariable int id, Model model) {
		
		Product product = productService.getProductById(id);
		
		model.addAttribute("product", product);
		
		return "updateProduct";
		
	}
	
	@RequestMapping(value="/productInventory/updateProduct", method=RequestMethod.POST)
	public String updateProductPost(@Valid Product product, BindingResult result, HttpServletRequest request) {  // controller -> service -> dao (db접근)
		
		if (result.hasErrors()) {
			System.out.println("=== Web Form data has some errors ===");
			List<ObjectError> errors = result.getAllErrors();
			
			for(ObjectError error: errors) {
				System.out.println(error.getDefaultMessage());
			}
			return "updateProduct";
		}
		
		MultipartFile productImage = product.getProductImage();
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");  // 배포시 webapp 밑이 패키징 될때 어디 디렉토리에 패키징 될지 알수없어서?? 
		Path savePath = Paths.get(rootDirectory + "\\resources\\images\\" + productImage.getOriginalFilename());  // full path
		
		if (productImage.isEmpty() == false) {
			System.out.println("---------------- file start ---------------");
			System.out.println("name : " + productImage.getName());
			System.out.println("filename : " + productImage.getOriginalFilename());
			System.out.println("size : " + productImage.getSize());
			System.out.println("savePath : " + savePath);
			System.out.println("----------------- file end ----------------\n");
		}
		
		product.setImageFilename(productImage.getOriginalFilename());
		
		if (productImage != null && !productImage.isEmpty()) {
			try {
				productImage.transferTo(new File(savePath.toString()));  // images 폴더에 저장하는 부분 ***
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println(product);
		
		productService.updateProduct(product);
		 
		return "redirect:/admin/productInventory";
	}
}

package kr.ac.hansung.cse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
public class CartController {
	
//	@Autowired
//	private UserService userService;

	@RequestMapping
	public String getCart(Model model) {
		
//		Authemtication authentication = securityContextHolder.getContext().getAuthentication();
//		String username = authentication.getName();
//		
//		User user = userService.getUserByUsername(username);
//		int cartId = user.getCartId();
//		model.addAttribute("cartId", cartId);
		
		model.addAttribute("cartId", 1);
		
		return "cart";
	}
}

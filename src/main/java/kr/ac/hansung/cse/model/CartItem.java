package kr.ac.hansung.cse.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class CartItem implements Serializable {

	private static final long serialVersionUID = -7296960050350583877L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="cartId")
	@JsonIgnore  // serialization 할 때 무시 (cylce 형성 방지)
	private Cart cart;  // db에서 foreign key
	
	@ManyToOne
	@JoinColumn(name="productId")
	private Product product;  // db에서 foreign key
	
	private int quantity;
	
	private double totalPrice;
	
}

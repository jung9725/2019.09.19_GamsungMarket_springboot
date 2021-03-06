package com.gamsung.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamsung.mapper.DealMapper;
import com.gamsung.mapper.MemberMapper;
import com.gamsung.mapper.ProductMapper;
import com.gamsung.mapper.ReviewMapper;
import com.gamsung.vo.Heart;
import com.gamsung.vo.Product;
import com.gamsung.vo.ProductFile;


@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	ProductMapper productMapper;
	
	@Autowired
	ReviewMapper reviewMapper;

	@Autowired
	DealMapper dealMapper;
	
	@Autowired
	MemberMapper memberMapper;

	@Autowired
	DealMapper reportMapper;

	/*	Product	*/
	
	@Override
	public Product findProductByProductNo(int productNo) {

		Product product = productMapper.selectProductByProductNo(productNo);
		
		//detail 이미지
		ProductFile titleFile = productMapper.selectProductFileByProductNo(product.getProductNo());
		product.setFile(titleFile);
		List<ProductFile> files = productMapper.selectProductFilesByProductNo(product.getProductNo());
		product.setFiles(files);
		product.setSellerNick(memberMapper.findMemberById(product.getSeller()).getNickname());
		
		return product;

	}

	@Override
	public ArrayList<Product> findProducts(HashMap<String , Object> params) {
		
		ArrayList<Product> products = productMapper.selectProducts(params);
		
		for(Product product : products) {
			ProductFile file = productMapper.selectProductFileByProductNo(product.getProductNo());
			product.setFile(file);
			product.setSellerNick(memberMapper.findMemberById(product.getSeller()).getNickname());
		}
		
		return products;
	}
	
	@Override
	public Integer registerProductTx(Product product) {
		productMapper.insertProduct(product);
		int newProductNo = product.getProductNo();
		
		// 대표이미지
		ProductFile titleFile = product.getFile();
		titleFile.setProductNo(newProductNo);
		productMapper.insertProductFile(titleFile);
		
		insertProductFiles(product, newProductNo);
		
		return newProductNo;
		
	}

	
	@Override
	public void insertProductFiles(Product product, int productNo) {
		
		// 이미지
		for (ProductFile file : product.getFiles()) {
			file.setProductNo(productNo);
			productMapper.insertProductFile(file);
		}
		
	}

	@Override
	public List<Product> findMyProductList(String memberId) {
		
		List<Product> products = productMapper.selectMyProductList(memberId);
		for(Product product : products) {
			product.setFile(productMapper.selectProductFileByProductNo(product.getProductNo()));
			product.setDeals(dealMapper.selectDealsByProductNo(product.getProductNo()));//요청받은 거래
		}
		
		return products;
	}
	

	@Override
	public List<Product> findMyRequestProductList(String memberId) {
		List<Product> products = productMapper.selectMyRequestProductList(memberId);
		for(Product product : products) {
			product.setFile(productMapper.selectProductFileByProductNo(product.getProductNo()));
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("buyer", memberId);
			params.put("productNo", product.getProductNo());

			product.setDeals(dealMapper.selectDealsByBuyer(params));//요청한 거래
		}
		
		return products;
	}

	@Override
	public void updateProductCount(int productNo) {
		productMapper.updateProductCount(productNo);
	}
	

	@Override
	public void deleteProduct(int productNo) {
		productMapper.deleteProduct(productNo);
		
	}

	@Override
	public void deleteProductFile(int productFileNo) {
		productMapper.deleteProductFile(productFileNo);
		
	}

	@Override
	public void updateProductFile(ProductFile productFile) {
		productMapper.updateProductFile(productFile);
		
	}

	@Override
	public void updateProduct(Product product) {
		productMapper.updateProduct(product);
		
	}
	
	//메인
	@Override
	public List<Product> findMain() {
		List<Product> main = productMapper.selectMain();
		
		//제품 이미지
		for(Product product : main) {
			product.setFile(productMapper.selectProductFileByProductNo(product.getProductNo()));
		}
		
		return main;
	}
	
	@Override
	public ArrayList<Product> findProductSearch(String keyword) {
		ArrayList<Product> products = productMapper.selectProductSearch(keyword);
		
		for(Product product : products) {
			ProductFile file = productMapper.selectProductFileByProductNo(product.getProductNo());
			product.setFile(file);
			product.setSellerNick(memberMapper.findMemberById(product.getSeller()).getNickname());
		}
		
		return products;
	}
	
	/*	Heart	*/

	@Override
	public void insertHeart(Heart heart) {
		
		productMapper.insertHeart(heart);
		
	}

	@Override
	public void deleteHeart(String id, int productNo) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("productNo", productNo);
		
		productMapper.deleteHeart(params);
		
	}

	@Override
	public Heart findHeart(String id, int productNo) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("productNo", productNo);
		
		Heart heart = productMapper.selectHeart(params);
		
		return heart;
	}
	
	@Override
	public boolean findHeartCount(String id, int productNo) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("productNo", productNo);
		
		boolean check = productMapper.selectHeartCount(params);
		
		return check;

	}
	
	@Override
	public List<Product> findMyHeartList(String memberId) {
		
		List<Product> heartlist = productMapper.selectMyHeartList(memberId);
		
		for(Product product : heartlist) {
			product.setFile(productMapper.selectProductFileByProductNo(product.getProductNo()));
		}
		
		return heartlist;
	}

	@Override
	public Integer findHeartCountByProductNo(int productNo) {
		Integer heartcount = productMapper.selectHeartCountByProductNo(productNo);
		
		return heartcount;
	}

}

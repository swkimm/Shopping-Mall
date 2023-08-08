package com.example.shop.controller;

import com.example.shop.domain.Categories;
import com.example.shop.domain.ProductLike;
import com.example.shop.request.InsertLikeRequest;
import com.example.shop.request.product.DeleteImgRequest;
import com.example.shop.request.product.DeleteLinkRequest;
import com.example.shop.request.product.ProductRequest;
import com.example.shop.request.product.ProductUpdateRequest;
import com.example.shop.response.*;
import com.example.shop.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;
    
    @PostMapping("/products/getList")
    public List<ProductListResponse> getAllList() {
        return productService.getAllList();
    }    @PostMapping("/products/getListWithCategoryId")
    public List<ProductListResponse> getListWithCategoryId(@RequestBody Map<String, Integer> request) {
        Integer categoryId = request.get("categoryId");
        return productService.getListWithCategoryId(categoryId);
    }

    @PostMapping("/products/item/getList")
    public List<ProductListResponse> getList(@RequestBody Map<String, Object> request) {
        Integer categoryId = (Integer) request.get("categoryId");
        Integer memberId = (Integer) request.get("memberId");
        System.out.println("categoryId = " + categoryId);
        System.out.println("memberId = " + memberId);
        return productService.getItemList(categoryId, memberId);
    }

    @PostMapping("/products/item/searchByBrand")
    public List<ProductListResponse> searchByBrand(@RequestBody Map<String, Object> request) {
        Integer categoryId = (Integer) request.get("categoryId");
        String brand = (String) request.get("brand");
        Integer memberId = (Integer) request.get("memberId");
        System.out.println("categoryId = " + categoryId);
        System.out.println("brand = " + brand);
        return productService.searchByBrand(categoryId, brand, memberId);
    }

    @PostMapping("/products/item/searchByColor")
    public List<ProductListResponse> searchByColor(@RequestBody Map<String, Object> request) {
        Integer categoryId = (Integer) request.get("categoryId");
        String color = (String) request.get("color");
        Integer memberId = (Integer) request.get("memberId");

        System.out.println("categoryId = " + categoryId);
        System.out.println("color = " + color);
        return productService.searchByColor(categoryId, color, memberId);
    }

    @PostMapping("/categories/getCategoryList")
    public List<Categories> getCategoryList() {
        return productService.getCategoryList();
    }

    @PostMapping(value = "/products/saveProduct", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String saveProduct(@RequestParam("cId") Integer cId,
                              @RequestParam("pName") String pName,
                              @RequestParam("pPrice") Integer pPrice,
                              @RequestParam("pStock") Integer pStock,
                              @RequestParam("pBrand") String pBrand,
                              @RequestParam("pDesc") String pDesc,
                              @RequestParam("pSize") String pSize,
                              @RequestParam("pColor") String pColor,
                              @RequestParam("pStatus") String pStatus,
                              @RequestParam("linkName") List<String> linkName,
                              @RequestParam("files") MultipartFile[] files) throws Exception {

        ProductRequest productRequest = ProductRequest.builder()
                .cId(cId)
                .pName(pName)
                .pPrice(pPrice)
                .pStock(pStock)
                .pBrand(pBrand)
                .pDesc(pDesc)
                .pSize(pSize)
                .pColor(pColor)
                .pStatus(pStatus)
                .build();


        System.out.println("productRequest = " + productRequest);
        for (MultipartFile file : files) {
            System.out.println(file.getOriginalFilename());
        }
        
        for (String link : linkName) {
            System.out.println("link = " + link);
        }

        productService.saveProduct(productRequest, files, linkName);
        return "이미지 등록";
    }

    @PostMapping("/products/getYoutubeLinkList/{pId}")
    public List<YoutubeLinkResponse> getYoutubelinkList(@PathVariable Integer pId, HttpServletResponse response) {

        System.out.println("pId = " + pId);
        return productService.getYoutubeLinkList(pId);
    }


    @PostMapping("/products/deleteProduct")
    public void deleteProduct(@RequestBody Map<String, Integer> request) {
        Integer pid = request.get("pid");
        System.out.println("pid = " + pid);
        productService.deleteProduct(pid);
    }

    @PostMapping("/products/getProduct/{pId}")
    public ProductGetResponse getProduct(@PathVariable Integer pId) {
        ProductGetResponse get = productService.getProduct(pId);
        System.out.println("get = " + get);
        return get;
    }

    @PostMapping("/products/deleteImg/{pId}")
    public void deleteImg(@PathVariable Integer pId, @RequestBody DeleteImgRequest deleteImgRequest) {
        deleteImgRequest.setPId(pId);
        productService.deleteImg(deleteImgRequest);
    }

    @PostMapping("/products/deleteLink/{pId}")
    public void deleteLink(@PathVariable Integer pId, @RequestBody DeleteLinkRequest deleteLinkRequest) {
        deleteLinkRequest.setPId(pId);
        productService.deleteLink(deleteLinkRequest);
    }

    @PostMapping("/products/addLink/{pId}")
    public String addLink(@PathVariable Integer pId, @RequestBody Map<String, Object> request) {
        System.out.println("pId = " + pId);
        String linkName = (String) request.get("linkName");
        System.out.println("linkName = " + linkName);
        productService.addLink(pId, linkName);
        return "링크가 등록되었습니다.";
    }

    @PostMapping("/products/getEventProductList")
    public List<EventProductListResponse> getEventProductList(@RequestBody Map<String, String> request) {
        String pStatus = request.get("event");
        System.out.println("pStatus = " + pStatus);

        return productService.getEventProductList(pStatus);
    }

    @PostMapping(value = "/products/updateProduct/{pId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void updateProduct(@PathVariable Integer pId, @ModelAttribute ProductUpdateRequest productUpdateRequest, @RequestParam(value = "files", required = false) MultipartFile[] files) throws IOException {

        productUpdateRequest.setPId(pId);
        System.out.println("pId = " + pId);
        System.out.println("productUpdateRequest = " + productUpdateRequest);
        productService.updateProduct(productUpdateRequest, files);
    }

    @PostMapping("/products/getBrandList")
    public List<GetBrandCountResponse> getBrandList(@RequestBody Map<String, Integer> request) {
        Integer categoryId = request.get("categoryId");
        return productService.getBrandList(categoryId);
    }

    @PostMapping("/products/getColorList")
    public List<GetColorCountResponse> getColorList(@RequestBody Map<String, Integer> request) {
        Integer categoryId = request.get("categoryId");
        return productService.getColorList(categoryId);
    }

    @PostMapping("/products/insertLike")
    public void insertLike(@RequestBody Map<String, Object> request) {
        Integer memberId = (Integer) request.get("memberId");
        Integer productId = (Integer) request.get("productId");
        Boolean liked = (Boolean) request.get("liked");
        System.out.println("Liked = " + liked);
        System.out.println("memberId = " + memberId);
        System.out.println("productId = " + productId);
        InsertLikeRequest insertLikeRequest = new InsertLikeRequest();
        insertLikeRequest.setMemberId(memberId);
        insertLikeRequest.setProductId(productId);
        insertLikeRequest.setLiked(liked);
        System.out.println("insertLikeRequest = " + insertLikeRequest);
        if (liked.equals(true)) {
            productService.insertLike(insertLikeRequest);
        } else {
            productService.deleteLike(insertLikeRequest);
        }
    }

    @PostMapping("/products/likedProducts")
    public List<Integer> getLikedProducts(@RequestBody Map<String, Integer> request) {
        Integer memberId = request.get("memberId");
        return productService.getLikedProducts(memberId);
    }

    @PostMapping("/products/getLikedCount/{productId}")
    public Integer getLikedCount(@PathVariable Integer productId) {
        return productService.getLikedCount(productId);
    }

}

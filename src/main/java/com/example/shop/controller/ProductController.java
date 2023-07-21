package com.example.shop.controller;

import com.example.shop.domain.Categories;
import com.example.shop.request.*;
import com.example.shop.response.EventProductListResponse;
import com.example.shop.response.ProductGetResponse;
import com.example.shop.response.ProductListResponse;
import com.example.shop.response.YoutubeLinkResponse;
import com.example.shop.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
    }

    @PostMapping("/products/helmet/getList")
    public List<ProductListResponse> getList(@RequestBody Map<String, Object> request) {
        Integer categoryId = (Integer) request.get("helmetCategoryId");
        System.out.println("categoryId = " + categoryId);
        return productService.getHelmetList(categoryId);
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

//    @PostMapping("/products/updateProduct/{pId}")
//    public void updateProduct(@PathVariable("pId") Integer pId, ProductGetResponse productGetResponse,
//                                @RequestParam(value = "addFiles", required = false) MultipartFile[] addFiles,
//                                @RequestParam(value = "removeFiles", required = false) List<String> removeFileNames) throws Exception {
//
//        productService.updateProduct(pId, productGetResponse, addFiles, removeFileNames);
//        System.out.println("productGetResponse = " + productGetResponse);
//    }

}

package com.example.shop.controller;

import com.example.shop.domain.Categories;
import com.example.shop.domain.Products;
import com.example.shop.request.DeleteImgRequest;
import com.example.shop.request.ProductRequest;
import com.example.shop.request.ProductUpdateRequest;
import com.example.shop.response.ProductGetResponse;
import com.example.shop.response.ProductListResponse;
import com.example.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/products/getList")
    public List<ProductListResponse> getList() {
        return productService.getList();
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
                .build();

        System.out.println("productRequest = " + productRequest);
        for (MultipartFile file : files) {
            System.out.println(file.getOriginalFilename());
        }

        productService.saveProduct(productRequest, files);
        return "이미지 등록";
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
        return get;
    }


    @PostMapping("/products/deleteImg/{pId}")
    public void deleteImg(@PathVariable Integer pId, @RequestBody DeleteImgRequest deleteImgRequest) {

        deleteImgRequest.setPId(pId);
        productService.deleteImg(deleteImgRequest);
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

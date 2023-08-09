package com.example.shop.service;

import com.example.shop.domain.Categories;
import com.example.shop.domain.ProductLike;
import com.example.shop.domain.Products;
import com.example.shop.mapper.ProductMapper;
import com.example.shop.request.InsertLikeRequest;
import com.example.shop.request.product.DeleteImgRequest;
import com.example.shop.request.product.DeleteLinkRequest;
import com.example.shop.request.product.ProductRequest;
import com.example.shop.request.product.ProductUpdateRequest;
import com.example.shop.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.core.sync.RequestBody;

import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    private S3Client s3;

    @Value("${aws.s3.bucketName}")
    private String bucketName;


    public List<ProductListResponse> getItemList(Integer categoryId, Integer memberId) {
        return productMapper.getItemList(categoryId, memberId);
    }

    public List<ProductListResponse> searchByBrand(Integer categoryId, String brand, Integer memberId) {
        return productMapper.searchByBrand(categoryId, brand, memberId);
    }
    public List<ProductListResponse> searchByColor(Integer categoryId, String color, Integer memberId) {
        return productMapper.searchByColor(categoryId, color, memberId);
    }

    public List<Categories> getCategoryList() {
        return productMapper.getCategoryList();
    }

    public Integer saveProduct(ProductRequest productRequest, MultipartFile[] files, List<String> youtubeLink) throws IOException {

        Products products = Products.builder()
                .cId(productRequest.getCId())
                .pName(productRequest.getPName())
                .pPrice(productRequest.getPPrice())
                .pStock(productRequest.getPStock())
                .pBrand(productRequest.getPBrand())
                .pDesc(productRequest.getPDesc())
                .pSize(productRequest.getPSize())
                .pColor(productRequest.getPColor())
                .pStatus(productRequest.getPStatus())
                .build();

        productMapper.saveProduct(products);

        for (String link : youtubeLink) {
            if (link != null && !link.isEmpty()) {
                productMapper.saveYoutubeLink(products.getPId(), link);
            }
        }

        for (MultipartFile file : files) {
            if (file.getSize() > 0) {
                String key = "shop/" + products.getPId() + "/" + file.getOriginalFilename();
                PutObjectRequest por = PutObjectRequest.builder()
                        .key(key)
                        .acl(ObjectCannedACL.PUBLIC_READ)
                        .bucket(bucketName)
                        .build();

                RequestBody rb = RequestBody.fromInputStream(file.getInputStream(), file.getSize());
                s3.putObject(por, rb);

                productMapper.insertFileName(products.getPId(), file.getOriginalFilename());
            }
        }
        return products.getPId();

    }

    public void deleteProduct(Integer pid) {
        productMapper.deleteProduct(pid);
    }

    public ProductGetResponse getProduct(Integer pId) {
        ProductGetResponse get = productMapper.getProduct(pId);
        return get;
    }

    public boolean updateProduct(ProductUpdateRequest productUpdateRequest, MultipartFile[] addFiles) throws IOException {

        // 수정
        // 새 파일 추가
        boolean hasDuplicateFiles = false;
        Set<String> fileNames = new HashSet<>();

        if (addFiles != null) {
            for (MultipartFile file : addFiles) {
                if (file != null && file.getSize() > 0) {
                    String originalFileName = file.getOriginalFilename();
                    if (fileNames.contains(originalFileName)) {
                        hasDuplicateFiles = true;
                        break;
                    }
                    fileNames.add(originalFileName);
                    if (hasDuplicateFiles) {
                        // Handle the case when duplicate files are found

                    } else {
                        productMapper.insertFileName(productUpdateRequest.getPId(), file.getOriginalFilename());
                        // s3에 파일(객체) 업로드
                        String key = "shop/" + productUpdateRequest.getPId() + "/" + file.getOriginalFilename();
                        PutObjectRequest por = PutObjectRequest.builder()
                                .key(key)
                                .acl(ObjectCannedACL.PUBLIC_READ)
                                .bucket(bucketName)
                                .build();
                        RequestBody rb = RequestBody.fromInputStream(file.getInputStream(), file.getSize());
                        s3.putObject(por, rb);
                    }
                }
            }
        }

        // 게시물(Board) 테이블 수정
        int cnt = productMapper.updateProduct(productUpdateRequest);

        // 파일 업로드
        return cnt == 1;

    }

    public void deleteImg(DeleteImgRequest deleteImgRequest) {
        productMapper.deleteImg(deleteImgRequest);
    }

    public void deleteLink(DeleteLinkRequest deleteLinkRequest) {
        productMapper.deleteLink(deleteLinkRequest);
    }
    public List<ProductListResponse> getAllList() {
        return productMapper.getAllList();
    }

    public List<YoutubeLinkResponse> getYoutubeLinkList(Integer productId) {
        return productMapper.getYoutubeLinkList(productId);
    }


    public void addLink(Integer pId, String linkName) {
        System.out.println("pId1111 = " + pId);
        System.out.println("linkName1111 = " + linkName);

        productMapper.addLink(pId, linkName);
    }

    public List<EventProductListResponse> getEventProductList(String pStatus) {
        return productMapper.getEventProductList(pStatus);
    }

    public List<ProductListResponse> getListWithCategoryId(Integer categoryId) {
        return productMapper.getListWithCategoryId(categoryId);
    }

    public List<GetBrandCountResponse> getBrandList(Integer categoryId) {
        return productMapper.getBrandList(categoryId);
    }

     public List<GetColorCountResponse> getColorList(Integer categoryId) {
        return productMapper.getColorList(categoryId);
    }


    public void insertLike(InsertLikeRequest insertLikeRequest) {
        productMapper.insertLike(insertLikeRequest);
    }

    public void deleteLike(InsertLikeRequest insertLikeRequest) {
        productMapper.deleteLike(insertLikeRequest);
    }

    public List<Integer> getLikedProducts(Integer memberId) {
        return productMapper.getLikedProducts(memberId);
    }

    public Integer getLikedCount(Integer productId) {
        return productMapper.getLikedCount(productId);
    }

    public List<ProductListResponse> getLikedProductId(Integer memberId) {
        return productMapper.getLikedProductId(memberId);
    }

    public void deleteLikeByProductId(Integer memberId, Integer productId) {
        productMapper.deleteLikeByProductId(memberId, productId);
    }
}

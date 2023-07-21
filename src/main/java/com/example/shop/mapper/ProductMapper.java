package com.example.shop.mapper;

import com.example.shop.domain.Categories;
import com.example.shop.domain.Products;
import com.example.shop.request.DeleteImgRequest;
import com.example.shop.request.DeleteLinkRequest;
import com.example.shop.request.ProductRequest;
import com.example.shop.request.ProductUpdateRequest;
import com.example.shop.response.EventProductListResponse;
import com.example.shop.response.ProductGetResponse;
import com.example.shop.response.ProductListResponse;
import com.example.shop.response.YoutubeLinkResponse;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {

    @Select("""
            SELECT
            	p.pId
            	, c.categoryId
            	, c.categoryName
            	, p.pName
            	, p.pPrice
            	, p.pBrand
            	, p.pStatus
            	, p.pStock
            	, p.pSize
            	, p.pColor
            	, pf.fileName
            FROM Products p
            LEFT JOIN 
                Categories c ON c.categoryId = p.cId
            LEFT JOIN 
                ProductFile pf ON pf.productId = p.pId
            WHERE c.categoryId = #{categoryId};
            """)
    @ResultMap("productResultMap")
    List<ProductListResponse> getProductList(Integer categoryId);


    @Select("""
            SELECT * FROM Categories;
            """)
    List<Categories> getCategoryList();

    @Insert("""
            INSERT INTO Products 
                ( cId
                , pName
                , pPrice
                , pStock
                , pBrand
                , pDesc
                , pStatus
                , pSize
                , pColor )
            VALUES 
                ( #{cId}
                , #{pName}
                , #{pPrice}
                , #{pStock}
                , #{pBrand}
                , #{pDesc}
                , #{pStatus} 
                , #{pSize}
                , #{pColor} )
            """)
    @Options(useGeneratedKeys = true, keyProperty = "pId")
    Integer saveProduct(Products products);


    @Insert("""
            INSERT INTO ProductFile (productId, fileName)
            VALUES (#{pId}, #{originalFilename})
            """)
    void insertFileName(Integer pId, String originalFilename);

    @Insert("""
        INSERT INTO
            ProductYoutubeLink
            (productId, linkName)
        VALUES
            (#{pId}, #{linkName})
        """)
    void saveYoutubeLink(Integer pId, String linkName);


    @Delete("""
            DELETE FROM Products 
            WHERE pId = #{pid};
            """)
    void deleteProduct(Integer pid);

    @Select("""
            SELECT
                p.pId
                , p.cId
                , c.categoryName
                , p.pName
                , p.pPrice
                , p.pStock
                , p.pBrand
                , p.pDesc
                , p.pStatus
                , p.pSize
                , p.pColor
                , pf.fileName
                , pyl.linkName
            FROM Products p
            LEFT JOIN Categories c ON c.categoryId = p.cId
            LEFT JOIN ProductFile pf ON pf.productId = p.pId
            LEFT JOIN ProductYoutubeLink pyl ON pyl.productId = p.pId
            WHERE pId = #{pId};
            """)
    @ResultMap("productGetResultMap")
    ProductGetResponse getProduct(Integer pId);

    @Update("""
            UPDATE Products
            SET
                pStatus = #{pStatus}
                , pName = #{pName}
                , pPrice = #{pPrice}
                , pBrand = #{pBrand}
                , pDesc = #{pDesc}
                , pStock = #{pStock}
                , pSize = #{pSize}
                , pColor = #{pColor}
            WHERE pId = #{pId}
            """)
    int updateProduct(ProductUpdateRequest productUpdateRequest);


    @Delete("""
            DELETE FROM ProductFile
            WHERE productId = #{pId} AND fileName = #{fileName};
            """)
    void deleteImg(DeleteImgRequest deleteImgRequest);

    @Delete("""
            DELETE FROM ProductYoutubeLink
            WHERE productId = #{pId} AND linkName = #{linkName};
            """)
    void deleteLink(DeleteLinkRequest deleteLinkRequest);

    @Select("""
            SELECT
                p.pId
                , c.categoryId
                , c.categoryName
                , p.pName
                , p.pPrice
                , p.pBrand
                , p.pStatus
                , p.pStock
                , p.pSize
                , p.pColor
                , pf.fileName
            FROM Products p
            LEFT JOIN
                Categories c ON c.categoryId = p.cId
            LEFT JOIN
                ProductFile pf ON pf.productId = p.pId
            """)
    @ResultMap("productResultMap")
    List<ProductListResponse> getAllList();


    @Select("""
            SELECT * FROM ProductYoutubeLink
            WHERE productId = #{productId}
            """)
    List<YoutubeLinkResponse> getYoutubeLinkList(Integer productId);

    // saveYoutubeLink와 동일함
    @Insert("""
            INSERT INTO
            ProductYoutubeLink
            (productId, linkName)
        VALUES
            (#{pId}, #{linkName})
            """)
    void addLink(Integer pId, String linkName);


    @Select("""
            SELECT
                p.pId
                , c.categoryId
                , c.categoryName
                , p.pName
                , p.pPrice
                , p.pBrand
                , p.pStatus
                , p.pStock
                , p.pSize
                , p.pColor
                , pf.fileName
            FROM Products p
            LEFT JOIN
                Categories c ON c.categoryId = p.cId
            LEFT JOIN
                ProductFile pf ON pf.productId = p.pId
            WHERE 
                p.pStatus = #{pStatus}
            """)
    @ResultMap("eventProductResultMap")
    List<EventProductListResponse> getEventProductList(String pStatus);
}

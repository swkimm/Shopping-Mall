package com.example.shop.mapper;

import com.example.shop.domain.Categories;
import com.example.shop.domain.Products;
import com.example.shop.request.DeleteImgRequest;
import com.example.shop.request.ProductUpdateRequest;
import com.example.shop.response.ProductGetResponse;
import com.example.shop.response.ProductListResponse;
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
            LEFT JOIN Categories c ON c.categoryId = p.cId
            LEFT JOIN ProductFile pf ON pf.productId = p.pId;  
            """)
    @ResultMap("productResultMap")
    List<ProductListResponse> getList();


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
            FROM Products p
            LEFT JOIN Categories c ON c.categoryId = p.cId
            LEFT JOIN ProductFile pf ON pf.productId = p.pId
            WHERE pId = #{pId};
            """)
    @ResultMap("productGetResultMap")
    ProductGetResponse getProduct(Integer pId);

    void deleteFileNameByPIdAndFileName(Integer pId, String fileName);

    @Update("""
            UPDATE Products
            SET
                 pName = #{pName}
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

//    @Update("""
//            UPDATE Products
//            SET
//                , pName = #{pName}
//                , pPrice = #{pPrice}
//                , pBrand = #{pBrand}
//                , pDesc = #{pDesc}
//                , pStatus = #{pStatus}
//                , pStock = #{pStock}
//                , pSize = #{pSize}
//                , pColor = #{pColor}
//            WHERE pId = #{pId}
//            """)
//    void updateProduct(Integer pId, ProductUpdateRequest productUpdateRequest);
}

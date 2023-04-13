package cn.edu.hstc.sport_mall.controller;

import cn.edu.hstc.sport_mall.pojo.Product;
import cn.edu.hstc.sport_mall.pojo.ProductImage;
import cn.edu.hstc.sport_mall.service.ProductImageService;
import cn.edu.hstc.sport_mall.service.ProductService;
import cn.edu.hstc.sport_mall.util.ImageUtil;
import cn.edu.hstc.sport_mall.util.UploadedImageFile;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller层-产品图片管理
 */
@Controller
public class ProductImageController {

    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    /*
     * 查询产品的图片信息
     */
    @RequestMapping("admin_productImage_list")
    public String list(int pid){
        Subject currentUser = SecurityUtils.getSubject();

        Product product = productService.selGet(pid);    //获取该产品的信息

        ProductImage single = new ProductImage();
        single.setPid(pid);
        single.setType("type_single");
        List<ProductImage> pisSingleList = productImageService.selList(single);    //查询该产品的单个图片集合

        ProductImage detail = new ProductImage();
        detail.setPid(pid);
        detail.setType("type_detail");
        List<ProductImage> pisDetailList = productImageService.selList(detail);    //查询该产品的详情图片集合

        //把产品信息 & 产品单个图片信息 & 产品详情图片集合存放于session中，以便前端页面分页显示
        currentUser.getSession().setAttribute("pisSingle", pisSingleList);
        currentUser.getSession().setAttribute("pisDetail", pisDetailList);
        currentUser.getSession().setAttribute("p", product);

        return "admin/listProductImage";
    }

    /*
     * 为指定产品新增图片
     */
    @RequestMapping("admin_productImage_add")
    public String add(ProductImage productImage, HttpSession session, UploadedImageFile uploadedImageFile){
        String type = productImage.getType();
        int result = productImageService.insAdd(productImage);

        if (result > 0){    //若数据库记录添加成功，则上传图片，保证完整性
            String fileName = productImage.getId() + ".jpg";
            String folder;
            String smallFolder = null;
            String middleFoler = null;

            if (type.equals("type_single")){
                folder = session.getServletContext().getRealPath("img/productSingle");
                smallFolder = session.getServletContext().getRealPath("img/productSingle_small");
                middleFoler = session.getServletContext().getRealPath("img/productSingle_middle");
            }else {
                folder = session.getServletContext().getRealPath("img/productDetail");
            }

            File file = new File(folder, fileName);

            if (!file.getParentFile().exists()){    //如果图片的上传路径不存在，则创建
                file.getParentFile().mkdirs();
            }

            try {
                uploadedImageFile.getImage().transferTo(file);    //把图片上传至指定的路径
                BufferedImage img = ImageUtil.change2jpg(file);
                ImageIO.write(img, "jpg", file);    //确保图片的格式是jpg，而不仅仅是后缀名

                if (type.equals("type_single")){    //如果图片是type_single类型
                    File smallFile = new File(smallFolder, fileName);
                    File middleFile = new File(middleFoler, fileName);

                    //修改成指定尺寸，并上传到指定目录
                    ImageUtil.resizeImage(file, 56, 56, smallFile);
                    ImageUtil.resizeImage(file, 217, 190, middleFile);

                    sendFirstProductImageToEs(productImage);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        return "redirect:/admin_productImage_list?pid=" + productImage.getPid();
    }

    /*
     * 删除指定商品的图片
     */
    @RequestMapping("admin_productImage_delete")
    public String delete(HttpSession session, int id){
        ProductImage productImage = productImageService.selById(id);    //先从数据库查询该图片的信息

        String folder = null;
        String smallFolder = null;
        String middleFoler = null;

        if (productImage.getType().equals("type_single")){    //如果图片是type_single类型，则删除三个路径下的三张图片
            folder = session.getServletContext().getRealPath("img/productSingle");
            smallFolder = session.getServletContext().getRealPath("img/productSingle_small");
            middleFoler = session.getServletContext().getRealPath("img/productSingle_middle");

            new File(folder, productImage.getId() + ".jpg").delete();
            new File(smallFolder, productImage.getId() + ".jpg").delete();
            new File(middleFoler, productImage.getId() + ".jpg").delete();

            productImageService.delete(id);    //在数据库中把该记录同步删除，保证完整性
            ProductImage productImage1 = productImageService.selGetByPid(productImage.getPid());
            changeFirstProductImageToEs(productImage, productImage1);
        }else {    //否则，只需要删除1个路径下的1张图片
            folder = session.getServletContext().getRealPath("img/productDetail");
            new File(folder, productImage.getId() + ".jpg").delete();
            productImageService.delete(id);    //在数据库中把该记录同步删除，保证完整性
        }

        return "redirect:/admin_productImage_list?pid=" + productImage.getPid();
    }

    /*
     * 为es服务器新增的商品添加其第一张图片
     */
    private void sendFirstProductImageToEs(ProductImage productImage){
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("_id", new Integer(productImage.getPid()).toString()))
                .build();

        //查询es服务器中该商品是否已经有第一张图片了
        List<cn.edu.hstc.sport_mall.espojo.Product> products = elasticsearchOperations.queryForList(searchQuery, cn.edu.hstc.sport_mall.espojo.Product.class);
        if (products.get(0).getFirstProductImage() == null){    //如果还没有，则添加
            UpdateRequest updateRequest = new UpdateRequest();
            Map<String, Object> firstProductImage = new HashMap<>();
            Map<String, Object> params = new HashMap<>();
            params.put("id", productImage.getId());
            params.put("pid", productImage.getPid());
            params.put("type", productImage.getType());
            firstProductImage.put("firstProductImage", params);
            updateRequest.doc(firstProductImage);

            UpdateQuery updateQuery = new UpdateQueryBuilder()    //更新
                    .withIndexName("product")
                    .withType("product")
                    .withId(new Integer(productImage.getPid()).toString())
                    .withUpdateRequest(updateRequest)
                    .build();
            elasticsearchOperations.update(updateQuery);
        }
    }

    /*
     * 删除商品的第一张图片后，同步更新es服务器关于firstProductImage的信息
     */
    private void changeFirstProductImageToEs(ProductImage productImage, ProductImage newProductImage){
        UpdateRequest updateRequest = new UpdateRequest();
        Map<String, Object> firstProductImage = new HashMap<>();

        if (newProductImage == null){    //如果该商品没有其它图片了，置为null
            firstProductImage.put("firstProductImage", null);
        }else {    //如果该商品还有其它图片，编号最小的那张图片成为新的firstProductImage
            Map<String, Object> params = new HashMap<>();
            params.put("id", newProductImage.getId());
            params.put("pid", newProductImage.getPid());
            params.put("type", newProductImage.getType());
            firstProductImage.put("firstProductImage", params);
        }
        updateRequest.doc(firstProductImage);

        UpdateQuery updateQuery = new UpdateQueryBuilder()    //更新
                .withIndexName("product")
                .withType("product")
                .withId(new Integer(productImage.getPid()).toString())
                .withUpdateRequest(updateRequest)
                .build();
        elasticsearchOperations.update(updateQuery);
    }
}

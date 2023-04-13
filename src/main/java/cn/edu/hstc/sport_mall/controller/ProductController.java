package cn.edu.hstc.sport_mall.controller;

import cn.edu.hstc.sport_mall.pojo.Category;
import cn.edu.hstc.sport_mall.pojo.Product;
import cn.edu.hstc.sport_mall.pojo.Property;
import cn.edu.hstc.sport_mall.pojo.PropertyValue;
import cn.edu.hstc.sport_mall.service.CategoryService;
import cn.edu.hstc.sport_mall.service.ProductService;
import cn.edu.hstc.sport_mall.service.PropertyService;
import cn.edu.hstc.sport_mall.service.PropertyValueService;
import cn.edu.hstc.sport_mall.util.PageUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.*;

/**
 * Controller层-产品管理
 */
@Controller
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PropertyValueService propertyValueService;
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    /*
     * 根据传入的产品编号，查询当前页面的产品信息
     */
    @RequestMapping("admin_product_list")
    public String list(int cid, PageUtil pageUtil){
        Subject currentUser = SecurityUtils.getSubject();

        Category category = categoryService.selGet(cid);    //获取指定编号的分类信息

        PageHelper.offsetPage(pageUtil.getStart(), pageUtil.getCount());    //设置当前页的起始记录 & 记录数
        List<Product> productList = productService.selListForAdmin(cid);    //返回该分类的产品集合
        int total = (int) new PageInfo<>(productList).getTotal();    //查询该分类的产品个数

        pageUtil.setTotal(total);
        pageUtil.setParam("&cid=" + category.getId());

        //把分类信息 & 分页信息 & 当前页面的产品集合存放于session中，以便前端页面分页显示
        currentUser.getSession().setAttribute("page", pageUtil);
        currentUser.getSession().setAttribute("c", category);
        currentUser.getSession().setAttribute("ps", productList);

        return "admin/listProduct";
    }

    /*
     * 为指定分类添加一个新产品
     */
    @RequestMapping("admin_product_add")
    public String add(Product product){
        //向数据库中新增商品记录
        product.setCreateDate(new Date());
        int result = productService.insAdd(product);

        //向es服务器中同步新增商品记录
        if (result > 0){
            cn.edu.hstc.sport_mall.espojo.Product product1 = new cn.edu.hstc.sport_mall.espojo.Product(
                    product.getId(), product.getName(), product.getPromotePrice(), product.getSaleCount(), product.getReviewCount()
            );

            IndexQuery indexQuery = new IndexQueryBuilder()
                    .withIndexName("product")
                    .withType("product")
                    .withObject(product1)
                    .build();
            elasticsearchOperations.index(indexQuery);
        }

        return "redirect:admin_product_list?cid=" + product.getCid();
    }

    /*
     * 根据编号获取单个产品信息
     */
    @RequestMapping("admin_product_edit")
    public String get(Model model, int id) {
        Product product = productService.selGet(id);
        model.addAttribute("p", product);

        return "admin/editProduct";
    }

    /*
     * 更新指定产品的信息
     */
    @RequestMapping("admin_product_update")
    public String update(Product product, int inputNumber) throws IOException {
        //更新数据库
        product.setStock( product.getStock() + inputNumber );    //修改库存量
        productService.update(product);

        //更新es
        UpdateRequest request = new UpdateRequest();
        request.doc(
                XContentFactory.jsonBuilder()
                        .startObject()
                        .field("name",product.getName())
                        .field("promotePrice", product.getPromotePrice())
                        .endObject()
        );
        UpdateQuery updateQuery = new UpdateQueryBuilder()
                .withUpdateRequest(request)
                .withClass(cn.edu.hstc.sport_mall.espojo.Product.class)
                .withId(new Integer(product.getId()).toString())
                .build();
        elasticsearchOperations.update(updateQuery);

        return "redirect:admin_product_list?cid=" + product.getCid();
    }

    /*
     * 查询产品已经绑定了的属性
     */
    @RequestMapping("admin_property_bind")
    public String bind(int pid){
        Subject currentUser = SecurityUtils.getSubject();

        Product product = productService.selGet(pid);    //查询指定编号的产品信息
        List<PropertyValue> propertyValueList = propertyValueService.selByPid(pid);    //查询指定编号产品的属性信息

        List<Property> allPropertyList = propertyService.selList(product.getCid());    //查询该产品所属分类的属性信息

        //得到该产品未有的 & 该产品所属分类拥有的 属性信息
        for (int i = 0; i < propertyValueList.size(); i++){
            allPropertyList.remove(propertyValueList.get(i).getProperty());
        }
        List<Property> idlePropertyList = allPropertyList;

        currentUser.getSession().setAttribute("p", product);
        currentUser.getSession().setAttribute("pvs", propertyValueList);
        currentUser.getSession().setAttribute("idlepts", idlePropertyList);

        return "admin/bindProperty";
    }
}
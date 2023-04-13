package cn.edu.hstc.sport_mall.controller;

import cn.edu.hstc.sport_mall.pojo.Product;
import cn.edu.hstc.sport_mall.pojo.PropertyValue;
import cn.edu.hstc.sport_mall.service.ProductService;
import cn.edu.hstc.sport_mall.service.PropertyValueService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Controller层-产品属性值设置
 */
@Controller
public class PropertyValueController {

    @Autowired
    private PropertyValueService propertyValueService;
    @Autowired
    private ProductService productService;

    /*
     * 查询指定编号的产品的属性值集合
     */
    @RequestMapping("admin_propertyValue_edit")
    public String selByPid(int pid){
        Subject currentUser = SecurityUtils.getSubject();

        Product product = productService.selGet(pid);    //查询该产品信息
        List<PropertyValue> propertyValueList = propertyValueService.selByPid(pid);    //查询该产品的属性值信息

        currentUser.getSession().setAttribute("p", product);
        currentUser.getSession().setAttribute("pvs", propertyValueList);

        return "admin/editPropertyValue";
    }

    /*
     * 修改指定编号的产品属性值
     */
    @RequestMapping("admin_propertyValue_update")
    @ResponseBody
    public String update(String value, int id){
        int result = propertyValueService.update(value, id);

        if (result > 0)    //修改成功，返回success
            return "success";
        else
            return "false";

    }

    /*
     * 为产品添加新属性，并设置属性值
     */
    @RequestMapping("admin_product_property_add")
    public String add(PropertyValue propertyValue){
        propertyValueService.insAdd(propertyValue);

        return "redirect:admin_property_bind?pid=" + propertyValue.getPid();
    }

    /*
     * 删除产品的属性
     */
    @RequestMapping("admin_productproperty_delete")
    public String delPropertyByProduct(int pid, int ptid){
        propertyValueService.delProductProperty(pid, ptid);

        return "redirect:admin_property_bind?pid=" + pid;
    }
}
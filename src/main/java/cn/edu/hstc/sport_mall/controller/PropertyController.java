package cn.edu.hstc.sport_mall.controller;

import cn.edu.hstc.sport_mall.pojo.Category;
import cn.edu.hstc.sport_mall.pojo.Property;
import cn.edu.hstc.sport_mall.service.CategoryService;
import cn.edu.hstc.sport_mall.service.PropertyService;
import cn.edu.hstc.sport_mall.util.PageUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Controller层：分类属性管理
 */
@Controller
public class PropertyController {

    @Autowired
    private PropertyService propertyService;
    @Autowired
    private CategoryService categoryService;

    /*
     * 查询当前页面的分类属性
     */
    @RequestMapping("admin_property_list")
    public String list(int cid, PageUtil pageUtil){
        Subject currentUser = SecurityUtils.getSubject();

        Category category = categoryService.selGet(cid);    //查询编号为 cid 的分类信息

        PageHelper.offsetPage(pageUtil.getStart(), pageUtil.getCount());    //设置当前页的起始记录 & 记录数
        List<Property> propertyList = propertyService.selList(cid);    //返回该分类的属性集合
        int total = (int) new PageInfo<>(propertyList).getTotal();    //查询该分类的属性个数

        pageUtil.setTotal(total);
        pageUtil.setParam("&cid="+category.getId());

        //把分类信息 & 分页信息 & 当前页面的属性集合存放于session中，以便前端页面分页显示
        currentUser.getSession().setAttribute("ps", propertyList);
        currentUser.getSession().setAttribute("c",category);
        currentUser.getSession().setAttribute("page", pageUtil);

        return "admin/listProperty";
    }

    /*
     * 为指定分类添加新属性
     */
    @RequestMapping("admin_property_add")
    public String add(String name, int cid){
        propertyService.insAdd(name, cid);

        return "redirect:admin_property_list?cid=" + cid;
    }

    /*
     * 获取单个属性信息
     */
    @RequestMapping("admin_property_edit")
    public String get(Model model, int id, int cid){
        Property property = propertyService.selGet(id);    //获取单个属性信息
        Category category = categoryService.selGet(cid);    //获取单个产品分类信息

        model.addAttribute("p", property);
        model.addAttribute("c", category);

        return "admin/editProperty";
    }

    /*
     * 更新属性信息
     */
    @RequestMapping("admin_property_update")
    public String update(String name, int id, int cid){
        Property property = new Property(id, name);
        propertyService.updata(property);

        return "redirect:admin_property_list?cid=" + cid;
    }

    /*
     * 删除指定分类的指定属性
     */
    @RequestMapping("admin_property_delete")
    public String deletePropertyByCategory(int cid, int ptid){
        propertyService.delCategoryProperty(cid, ptid);

        return "redirect:admin_property_list?cid=" + cid;
    }
}
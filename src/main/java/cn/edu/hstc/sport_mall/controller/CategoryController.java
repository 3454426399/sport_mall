package cn.edu.hstc.sport_mall.controller;

import cn.edu.hstc.sport_mall.pojo.Category;
import cn.edu.hstc.sport_mall.service.CategoryService;
import cn.edu.hstc.sport_mall.util.ImageUtil;
import cn.edu.hstc.sport_mall.util.PageUtil;
import cn.edu.hstc.sport_mall.util.UploadedImageFile;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

/**
 * Controller层：负责产品分类信息管理
 */
@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /*
     * 查询当前页面的分类信息
     */
    @RequestMapping("admin_category_list")
    public String list(PageUtil pageUtil){    //这里的pageUtil是通过无参构造方法创建的，传递过来的参数通过set方法进行赋值
        Subject currentUser = SecurityUtils.getSubject();

        //使用PageHelper分页插件实现分页
        PageHelper.offsetPage(pageUtil.getStart(),pageUtil.getCount());    //设置当前页的起始记录 & 记录数
        List<Category> categoryList = categoryService.selList();    //返回当前页面的分类集合
        int total = (int) new PageInfo<>(categoryList).getTotal();    //查询分类的总数

        pageUtil.setTotal(total);

        //把分页信息 & 当前页面的产品分类集合存放于session中，以便前端页面分页显示
        currentUser.getSession().setAttribute("page", pageUtil);
        currentUser.getSession().setAttribute("cs", categoryList);

        return "admin/listCategory";
    }

    /*
     * 新增一个产品分类
     */
    @RequestMapping("admin_category_add")
    public String add(Category category, HttpSession session, UploadedImageFile uploadedImageFile){
        int result = categoryService.insAdd(category);

        if (result > 0){    //如果新分类添加成功，则上传该分类的图片
            String folder = session.getServletContext().getRealPath("img/category");
            File file = new File(folder,category.getId() + ".jpg");    //以该分类的编号命名

            if (!file.getParentFile().exists()){    //如果图片的上传路径不存在，则创建
                file.getParentFile().mkdirs();
            }

            try {
                uploadedImageFile.getImage().transferTo(file);    //把图片上传至指定的路径
                BufferedImage img = ImageUtil.change2jpg(file);
                ImageIO.write(img,"jpg", file);    //确保图片的格式是jpg，而不仅仅是后缀名

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return "redirect:/admin_category_list";
    }

    /*
     * 获取单个产品分类信息
     */
    @RequestMapping("admin_category_edit")
    public String get(Model model, int id){
        Category category = categoryService.selGet(id);

        model.addAttribute("c", category);
        return "admin/editCategory";
    }

    /*
     * 更新产品分类信息
     */
    @RequestMapping("admin_category_update")
    public String update(Category category, HttpSession session ,UploadedImageFile uploadedImageFile){
        int result = categoryService.update(category);

        if (result > 0){    //已经更新数据库的产品分类信息，下面是更新产品图片
            String folder = session.getServletContext().getRealPath("img/category");
            File file = new File(folder, category.getId() + ".jpg");

            if (!file.getParentFile().exists()){    //若路径不存在，则创建
                file.getParentFile().mkdirs();
            }

            if (file.exists()){    //若该分类之前已经有照片了，则先删除旧的
                boolean bool = file.delete();
            }

            try {    //再上传新的，达到覆盖效果
                uploadedImageFile.getImage().transferTo(file);
                BufferedImage img = ImageUtil.change2jpg(file);
                ImageIO.write(img,"jpg",file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "redirect:/admin_category_list";
    }
}
package com._520it.wms.web.action;

import com._520it.wms.domain.Product;
import com._520it.wms.query.ProductQueryObject;
import com._520it.wms.service.IBrandService;
import com._520it.wms.service.IProductService;
import com._520it.wms.util.FileUploadUtil;
import com._520it.wms.util.RequirePermission;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProductAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    @Setter
    private IProductService productService;
    @Getter
    private Product product = new Product();
    @Setter
    private IBrandService brandService;
    @Setter
    private File pic;
    @Setter
    private String picFileName;
    @Getter
    @Setter
    private List<Long> ids = new ArrayList<>();
    @Getter
    private ProductQueryObject qo = new ProductQueryObject();

    @RequirePermission("商品列表")
    public String execute() throws Exception {
        try {
            putContext("pageResult", productService.query(qo));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return LIST;
    }
     @RequirePermission("选择商品列表")
        public String selectProductList() throws Exception {
            try {
                putContext("pageResult", productService.query(qo));
                putContext("brands", brandService.listAll());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "selectProductList";
        }

    @RequirePermission("商品删除")
    public String delete() throws Exception {
        try {
            if(StringUtils.isNotEmpty(product.getImagePath())){
                FileUploadUtil.deleteFile(product.getImagePath());
            }
            if (product.getId() != null) {
                productService.delete(product.getId());
            }
            putJson("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            putJson("删除失败");
        }
        return NONE;
    }

    @RequirePermission("商品编辑")
    public String input() throws Exception {
        try {
            putContext("brands", brandService.listAll());
            if (product.getId() != null) {
                product = productService.get(product.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return INPUT;
    }

    @RequirePermission("商品修改或保存")
    public String saveOrUpdate() throws Exception {
        try {
            //如果上传过来的路径不为空且用户上传了图片
            if(StringUtils.isNotEmpty(product.getImagePath()) && pic!=null){
                FileUploadUtil.deleteFile(product.getImagePath());
            }
            //如果存放了图片,没有存图则不进行设置操作
            if (pic != null) {
                String path = FileUploadUtil.uploadFile(pic, picFileName);
                product.setImagePath(path);
            }
            if (product.getId() != null) {
                productService.update(product);
                addActionMessage("更新成功");
            } else {
                productService.save(product);
                addActionMessage("保存成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            addActionError(e.getMessage());
        }
        return SUCCESS;
    }

    @RequirePermission("商品批量删除")
    public String batchDelete() throws Exception {
        try {
            if (ids.size() > 0) {
                productService.batchDelete(ids);
            }
            putJson("批量删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            putJson("批量删除失败");
        }
        return NONE;
    }
}

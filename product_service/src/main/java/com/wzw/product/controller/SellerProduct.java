package com.wzw.product.controller;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.wzw.exceptions.WzwRunException;
import com.wzw.product.Vo.*;
import com.wzw.product.entity.Category;
import com.wzw.product.entity.Info;
import com.wzw.product.handler.CustomCellWriteHandler;
import com.wzw.product.service.CategoryService;
import com.wzw.product.service.InfoService;
import com.wzw.result.ResponseEnum;
import com.wzw.templates.R;
import com.wzw.templates.ResultVo;
import com.wzw.util.RUtil;
import com.wzw.product.form.InfoForm;
import com.wzw.product.form.InfoUpdateForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 类目表 前端控制器
 * </p>
 *
 * @author 王梓崴
 * @since 2021-10-12
 */
@RestController
@RequestMapping(value = "/seller/product", method = RequestMethod.GET)
public class SellerProduct {
    @Autowired
    InfoService infoService;

    @Autowired
    CategoryService categoryService;

    //查找所有商品的分类
    @GetMapping("findAllProductCategory")
    public R findAllProductCategory() {
        List<Rpro> allRpro = categoryService.findAllProductCategory();
        RRpro rRpro = new RRpro();
        rRpro.setContent(allRpro);
        R success = RUtil.success(rRpro);
        return success;
    }

    //新增商品
    @PostMapping("add")
    public R add(@RequestBody InfoForm infoForm) {
        Info info = new Info();
        BeanUtils.copyProperties(infoForm, info);
        boolean save = infoService.save(info);
        if (save) {
            return RUtil.success(null);
        }
        return RUtil.fail(null);
    }

    //分页查询所有商品——分页
    @GetMapping("list/{page}/{size}")
    public R list(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Page<Info> objectPage = new Page<>(page, size);
        Page<Info> page1 = infoService.page(objectPage);
        List<Info> records = page1.getRecords();
        ArrayList<InfoVo> objects = new ArrayList<>();
        //转为前台需要的格式
        for (Info record : records) {
            InfoVo infoVo = new InfoVo();
            BeanUtils.copyProperties(record, infoVo);
            QueryWrapper<Category> objectQueryWrapper = new QueryWrapper<>();
            objectQueryWrapper.eq("category_type", record.getCategoryType());
            Category one = categoryService.getOne(objectQueryWrapper);
            infoVo.setCategoryName(one.getCategoryName());
            if (record.getProductStatus() == 0) {
                infoVo.setStatus(false);
            } else
                infoVo.setStatus(true);
            objects.add(infoVo);
        }
        ResultVo<InfoVo> objectResultVo = new ResultVo<>();
        objectResultVo.setContent(objects);
        objectResultVo.setSize((int) page1.getSize());
        objectResultVo.setTotal((int) page1.getTotal());
        return RUtil.success(objectResultVo);
    }

    //根据商品名称模糊查找所有商品——分页
    @GetMapping("like/{keyWord}/{page}/{size}")
    public R like(@PathVariable("keyWord") String keyWord, @PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        QueryWrapper<Info> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.like("product_name", keyWord);
        Page<Info> objectPage = new Page<>(page, size);
        Page<Info> list = infoService.page(objectPage, objectQueryWrapper);
        List<Info> records = list.getRecords();

        ArrayList<InfoVo> objects = new ArrayList<>();
        //转为前台需要的格式
        for (Info record : records) {
            InfoVo infoVo = new InfoVo();
            BeanUtils.copyProperties(record, infoVo);
            QueryWrapper<Category> objectWrapper = new QueryWrapper<>();
            objectWrapper.eq("category_type", record.getCategoryType());
            Category one = categoryService.getOne(objectWrapper);
            infoVo.setCategoryName(one.getCategoryName());
            if (record.getProductStatus() == 0) {
                infoVo.setStatus(false);
            } else
                infoVo.setStatus(true);
            objects.add(infoVo);
        }
        ResultVo<InfoVo> objectResultVo = new ResultVo<>();
        objectResultVo.setContent(objects);
        objectResultVo.setSize((int) list.getSize());
        objectResultVo.setTotal((int) list.getTotal());
        return RUtil.success(objectResultVo);
    }

    //根据商品类型查找所有商品——分页
    @GetMapping("findByCategory/{categoryType}/{page}/{size}")
    public R findByCategory(@PathVariable("categoryType") String categoryType, @PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        QueryWrapper<Info> objectsWrapper = new QueryWrapper<>();
        objectsWrapper.eq("category_type", categoryType);
        Page<Info> objectPage = new Page<>(page, size);
        Page<Info> list = infoService.page(objectPage, objectsWrapper);
        List<Info> records = list.getRecords();
        ArrayList<InfoVo> objects = new ArrayList<>();
        //转为前台需要的格式
        for (Info record : records) {
            InfoVo infoVo = new InfoVo();
            BeanUtils.copyProperties(record, infoVo);
            QueryWrapper<Category> objectWrapper = new QueryWrapper<>();
            objectWrapper.eq("category_type", record.getCategoryType());
            Category one = categoryService.getOne(objectWrapper);
            infoVo.setCategoryName(one.getCategoryName());
            if (record.getProductStatus() == 0) {
                infoVo.setStatus(false);
            } else
                infoVo.setStatus(true);
            objects.add(infoVo);
        }
        ResultVo<InfoVo> objectResultVo = new ResultVo<>();
        objectResultVo.setContent(objects);
        objectResultVo.setSize((int) list.getSize());
        objectResultVo.setTotal((int) list.getTotal());
        return RUtil.success(objectResultVo);
    }

    //根据商品id查找到商品并返回规定格式的信息
    @GetMapping("findById/{id}")
    public R findByCategory(@PathVariable("id") String id) {
        QueryWrapper<Info> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("product_id", id);
        Info info = infoService.getOne(objectQueryWrapper);

        //转为前台需要的格式

        CatigoryVo catigoryVo = new CatigoryVo();
        BeanUtils.copyProperties(info, catigoryVo);
        if (info.getProductStatus() == 0) {
            catigoryVo.setStatus(false);
        } else
            catigoryVo.setStatus(true);
        CatigoryType catigoryType = new CatigoryType();
        catigoryType.setCategoryType(info.getCategoryType());
        catigoryVo.setCategory(catigoryType);

        return RUtil.success(catigoryVo);
    }

    //根据商品id删除这个商品
    @DeleteMapping("delete/{id}")
    public R delete(@PathVariable("id") String id) {
        boolean b = infoService.removeById(id);
        if (b) {
            return RUtil.success(null);
        }
        return RUtil.fail(null);
    }

    //根据商品id修改其状态
    @PutMapping("updateStatus/{id}/{status}")
    public R updateStatus(@PathVariable("id") String id, @PathVariable("status") boolean status) {
        boolean b = infoService.updateStatus(id, status);
        if (b) {
            return RUtil.success(true);
        }
        return RUtil.fail("false");
    }

    //根据id修改商品信息
    @PutMapping("update")
    public R update(@RequestBody @Valid InfoUpdateForm infoUpdateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new WzwRunException(ResponseEnum.PRODUCT_EMPTY.getMsg());
        }
        Info info = new Info();
        Category category = new Category();
        BeanUtils.copyProperties(infoUpdateForm, info);
        BeanUtils.copyProperties(infoUpdateForm.getCategory(), category);
        info.setCategoryType(category.getCategoryType());
        boolean b = infoService.updateById(info);
        if (b) {
            return RUtil.success(null);
        }
        return RUtil.fail(null);
    }

    //文件下载
    @GetMapping("/export")
    public void exportData(HttpServletResponse response) throws Exception {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("UTF-8");
            String fileName = URLEncoder.encode("商品信息", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            List<ProductExcelVO> productExcelVOList = this.infoService.productExcelVOList();
            EasyExcel.write(response.getOutputStream(), ProductExcelVO.class)
                    .registerWriteHandler(new CustomCellWriteHandler())
                    .sheet("商品信息")
                    .doWrite(productExcelVOList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //文件的上传
    @PutMapping("import")
    public R importData(@RequestParam("file") MultipartFile multipartFile) {
        List<Info> productInfoList = null;
        try {
            productInfoList = this.infoService.excleToProductInfoList(multipartFile.getInputStream());//调用service层从excel得到了Info集合
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(productInfoList==null){
            return RUtil.fail("导入Excel失败！");
        }
        boolean result = this.infoService.saveBatch(productInfoList);//批量存入表中
        if(result)return RUtil.success(null);
        return RUtil.fail("导入Excel失败！");
    }
}



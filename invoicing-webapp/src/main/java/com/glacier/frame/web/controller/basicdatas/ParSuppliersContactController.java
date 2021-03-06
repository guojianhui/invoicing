package com.glacier.frame.web.controller.basicdatas;
 
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.glacier.core.controller.AbstractController;
import com.glacier.frame.dto.query.basicdatas.ParSuppliersContactQueryDTO;
import com.glacier.frame.entity.basicdatas.ParSuppliersContact;
import com.glacier.frame.service.basicdatas.ParComContactTypeService;
import com.glacier.frame.service.basicdatas.ParSuppliersContactService;
import com.glacier.frame.service.basicdatas.SuppliersService;
import com.glacier.jqueryui.util.JqPager;

/*** 
 * @ClassName:  ParSuppliersContactController
 * @Description: TODO(供应商联系人controller)
 * @author wuting
 * @email 920339213@QQ.com
 * @date 2015-1-13
 */
@Controller
@RequestMapping(value = "/suppliersContact")
public class ParSuppliersContactController extends AbstractController{

    @Autowired
    private ParSuppliersContactService parSuppliersContactService;
    
    @Autowired
    private ParComContactTypeService parComContactTypeService;
    
    @Autowired
    private SuppliersService suppliersService;
    
    //进入供应商联系人信息列表展示页面
    @RequestMapping(value = "/index.htm")
    private Object intoIndexPmember() {
        ModelAndView mav = new ModelAndView("basicdatas_mgr/suppliersContact_mgr/suppliersContact");
        return mav;
    } 
    
    //获取表格结构的所有供应商联系人数据
    @RequestMapping(value = "/list.json", method = RequestMethod.POST)
    @ResponseBody
    private Object listActionAsGridByMenuId(JqPager jqPager, ParSuppliersContactQueryDTO suppliersContactQueryDTO,String supplierId) {
       return parSuppliersContactService.listAsGrid(jqPager, suppliersContactQueryDTO);
    }
      
    //进入供应商联系人信息Detail信息页面
    @RequestMapping(value = "/intoDetail.htm")
    private Object intoMemberGradeDetailPage(String supplierContactId) { 
        ModelAndView mav = new ModelAndView("basicdatas_mgr/suppliersContact_mgr/suppliersContact_detail");
        if(StringUtils.isNotBlank(supplierContactId)){
            mav.addObject("suppliersContactDate", parSuppliersContactService.getParSuppliersContact(supplierContactId));
        }
        return mav;
    }
    
    //进入供应商联系人信息Form表单页面
    @RequestMapping(value = "/intoForm.htm")
    private Object intoGradeFormPnews(String supplierContactId) {
        ModelAndView mav = new ModelAndView("basicdatas_mgr/suppliersContact_mgr/suppliersContact_form");
        if(StringUtils.isNotBlank(supplierContactId)){
            mav.addObject("suppliersContactDate", parSuppliersContactService.getParSuppliersContact(supplierContactId));
        }
        mav.addObject("contactType", parComContactTypeService.getContactTypeCombo());
        mav.addObject("supplier", suppliersService.getSuppliersCombo());
        return mav;
    }
    
    //增加供应商联系人信息
    @RequestMapping(value = "/add.json", method = RequestMethod.POST)
    @ResponseBody
    private Object addGrade(@Valid ParSuppliersContact parSuppliersContact, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {// 后台校验的错误信息
            return returnErrorBindingResult(bindingResult);
        }
        return parSuppliersContactService.addParSuppliersContact(parSuppliersContact);
    }
    
    //修改供应商联系人信息
    @RequestMapping(value = "/edit.json", method = RequestMethod.POST)
    @ResponseBody
    private Object editGrade(@Valid ParSuppliersContact parSuppliersContact, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {// 后台校验的错误信息
            return returnErrorBindingResult(bindingResult);
        }
        return parSuppliersContactService.editParSuppliersContact(parSuppliersContact);
    }
    
    //批量删除供应商联系人信息
    @RequestMapping(value = "/del.json", method = RequestMethod.POST)
    @ResponseBody
    public Object delGrade(@RequestParam List<String> supplierContactIds,@RequestParam List<String> names) {
        return parSuppliersContactService.delParSuppliersContact(supplierContactIds, names);
    }
}

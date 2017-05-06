package com.beingjavaguys.controllers.cmsmenu;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.beingjavaguys.bean.cmsmenu.CMSMenuBean;
import com.beingjavaguys.bean.cmsmenu.CMSMenuUnitBean;
import com.beingjavaguys.bean.generic.BeanList;
import com.beingjavaguys.bean.other.CookSpecialityBean;
import com.beingjavaguys.services.cmsmenu.CMSMenuService;
import com.beingjavaguys.utility.validations.RestValidation;

@Controller
@RequestMapping("/cms/menu")
public class CMSMenuController {

	@Autowired
	CMSMenuService cmsMenuService;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	RestValidation restValidation;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody
	int add(HttpServletResponse response, @RequestBody CMSMenuBean cmsMenuBean) {
		int nenuId = 0;
		try {
			nenuId = cmsMenuService.add(cmsMenuBean, response);
			System.out.println(cmsMenuBean);
		} catch (Exception e) {
			response.setStatus(400);
		}
		return nenuId;
	}

	@RequestMapping(value = "/fileupload", headers = ("content-type=multipart/*"), method = RequestMethod.POST)
	public @ResponseBody
	void upload(HttpServletResponse response,
			@RequestParam("file") MultipartFile inputFile,
			@RequestParam(required = true) int menuid) {
		try {
			String rootPath = servletContext.getRealPath("/");
			String originalFilename = inputFile.getOriginalFilename();
			String folderPath = rootPath + File.separator + "image"
					+ File.separator + "menu_item" + File.separator;
			String fileName = originalFilename;
			File destinationFile = new File(folderPath + fileName);
			if (!destinationFile.exists()) {
				destinationFile.mkdirs();
			} else {
				boolean trigger = true;
				int i = 0;
				while (trigger) {
					if (destinationFile.exists()) {
						destinationFile = new File(folderPath + i + fileName);
						i++;
					} else {
						trigger = false;
					}
				}
			}
			inputFile.transferTo(destinationFile);
			String prevoiusFileName = cmsMenuService.uploadMenuImage(
					destinationFile.getName(), menuid, response);
			if (prevoiusFileName != null && !prevoiusFileName.trim().isEmpty()) {
				destinationFile = new File(folderPath + prevoiusFileName);
				destinationFile.delete();
			}
		} catch (Exception e) {
			response.setStatus(400);
		}
	}

	@RequestMapping(value = "/menuList", method = RequestMethod.GET)
	public @ResponseBody
	BeanList get(HttpServletResponse response,HttpServletRequest httpServletRequest,
			@RequestParam(required = false) String limit,
			@RequestParam(required = false) String pageno,
			@RequestParam(required = false) String cookId,
			@RequestParam(required = false) String catagoryId) {
		String url = httpServletRequest.getRequestURL().toString();
		int limitInt = 0, pagenoInt = 0;
		int cook_id = 0, catagory_id = 0;
		BeanList beanList = null;
		try {
			if (limit == null || limit.equalsIgnoreCase("null")
					|| limit.equals("")) {
				limitInt = 10;
			} else {
				restValidation.numberFormatValidation(limit);
				limitInt = Integer.parseInt(limit);
			}
			if (pageno == null || pageno.equalsIgnoreCase("null")
					|| pageno.equals("")) {

				pagenoInt = 1;
			} else {
				restValidation.numberFormatValidation(pageno);
				pagenoInt = Integer.parseInt(pageno);
			}

			if (cookId == null || cookId.equalsIgnoreCase("null")
					|| cookId.equals("")) {
				cook_id = 0;
			} else {
				restValidation.numberFormatValidation(cookId);
				cook_id = Integer.parseInt(cookId);
			}

			if (catagoryId == null || catagoryId.equalsIgnoreCase("null")
					|| catagoryId.equals("")) {
				catagory_id = 0;
			} else {
				restValidation.numberFormatValidation(catagoryId);
				catagory_id = Integer.parseInt(catagoryId);
			}

			beanList = cmsMenuService.get(limitInt, pagenoInt, cook_id,
					catagory_id, response,url);
		} catch (Exception e) {
			response.setStatus(400);
		}
		return beanList;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public @ResponseBody
	int edit(HttpServletResponse response, @RequestBody CMSMenuBean cmsMenuBean) {
		int menuId = 0;
		try {
			menuId = cmsMenuService.edit(cmsMenuBean, response);
		} catch (Exception e) {
			response.setStatus(400);
		}
		return menuId;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	void delete(HttpServletResponse response,
			@RequestParam(required = true) int id) {
		try {
			cmsMenuService.delete(id, response);
		} catch (Exception e) {
			response.setStatus(400);
		}
	}

	@RequestMapping(value = "/getMenuUnit", method = RequestMethod.GET)
	public @ResponseBody
	List<CMSMenuUnitBean> getMenuUnit(HttpServletResponse response) {
		List<CMSMenuUnitBean> cmsMenuUnitBeanList = null;
		try {
			cmsMenuUnitBeanList = cmsMenuService.getMenuUnit();
		} catch (Exception e) {
			response.setStatus(400);
		}
		return cmsMenuUnitBeanList;
	}

	@RequestMapping(value = "/allmenu", method = RequestMethod.GET)
	public @ResponseBody
	List<CMSMenuBean> get(HttpServletResponse response,
			@RequestParam(required = false) String cookId,
			@RequestParam(required = false) String specialityId) {
		int cook_id = 0, speciality_id = 0;
		List<CMSMenuBean> cmsMenuBeanList = null;
		try {
			if (cookId == null || cookId.equalsIgnoreCase("null")
					|| cookId.equals("")) {
				cook_id = 0;
			} else {
				restValidation.numberFormatValidation(cookId);
				cook_id = Integer.parseInt(cookId);
			}

			if (specialityId == null || specialityId.equalsIgnoreCase("null")
					|| specialityId.equals("")) {
				speciality_id = 0;
			} else {
				restValidation.numberFormatValidation(specialityId);
				speciality_id = Integer.parseInt(specialityId);
			}

			cmsMenuBeanList = cmsMenuService.get(cook_id, speciality_id,
					response);
		} catch (Exception e) {
			response.setStatus(400);
		}
		return cmsMenuBeanList;
	}

	@RequestMapping(value = "/addSpeciality", method = RequestMethod.POST)
	public @ResponseBody
	void addSpeciality(HttpServletResponse response,
			@RequestBody CookSpecialityBean cookSpecialityBean) {
		try {
			cmsMenuService.addSpeciality(cookSpecialityBean, response);
		} catch (Exception e) {
			response.setStatus(400);
		}
	}

	@RequestMapping(value = "/getCookSpeciality", method = RequestMethod.GET)
	public @ResponseBody
	CookSpecialityBean getCookSpeciality(HttpServletResponse response,
			@RequestParam(required = false) String cookId,
			@RequestParam(required = false) String speciality) {
		int cook_id = 0;
		CookSpecialityBean cookSpecialityBean = null;
		try {
			if (cookId == null || cookId.equalsIgnoreCase("null")
					|| cookId.equals("")) {
				cook_id = 0;
			} else {
				restValidation.numberFormatValidation(cookId);
				cook_id = Integer.parseInt(cookId);
			}
			cookSpecialityBean = cmsMenuService.getCookSpeciality(cook_id,
					speciality, response);
		} catch (Exception e) {
			response.setStatus(400);
		}
		return cookSpecialityBean;
	}

	@RequestMapping(value = "/deleteCookSpeciality", method = RequestMethod.POST)
	public @ResponseBody
	void deleteCookSpeciality(HttpServletResponse response,
			@RequestParam(required = false) String cookId,
			@RequestParam(required = false) String speciality) {
		int cook_id = 0;
		try {
			if (cookId == null || cookId.equalsIgnoreCase("null")
					|| cookId.equals("")) {
				cook_id = 0;
			} else {
				restValidation.numberFormatValidation(cookId);
				cook_id = Integer.parseInt(cookId);
			}
			cmsMenuService.deleteCookSpeciality(cook_id, speciality, response);
		} catch (Exception e) {
			response.setStatus(400);
		}
	}
	
	@RequestMapping(value = "/getCooksofitem", method = RequestMethod.GET)
	public @ResponseBody
	BeanList getCooksOfItem(HttpServletResponse response, HttpServletRequest httpServletRequest,
			@RequestParam(required = false) String limit,
			@RequestParam(required = false) String pageno,
			@RequestParam(required = false) String itemName) {
		String url = httpServletRequest.getRequestURL().toString();
		int limitInt = 0, pagenoInt = 0;
		BeanList beanList = null;
		try {
			if (limit == null || limit.equalsIgnoreCase("null")
					|| limit.equals("")) {
				limitInt = 10;
			} else {
				restValidation.numberFormatValidation(limit);
				limitInt = Integer.parseInt(limit);
			}
			if (pageno == null || pageno.equalsIgnoreCase("null")
					|| pageno.equals("")) {

				pagenoInt = 1;
			} else {
				restValidation.numberFormatValidation(pageno);
				pagenoInt = Integer.parseInt(pageno);
			}
			beanList = cmsMenuService.getCooksOfItem(limitInt, pagenoInt,itemName,response,url);
		} catch (Exception e) {
			response.setStatus(400);
		}
		return beanList;
	}
}

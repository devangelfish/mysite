package com.bitacademy.mysite.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bitacademy.mysite.dto.JsonResult;
import com.bitacademy.mysite.service.GalleryService;
import com.bitacademy.mysite.vo.GalleryVo;

@Controller("galleryApiController")
@RequestMapping("/api/gallery")
public class GalleryController {
	   
	   @Autowired
	   private GalleryService galleryService;
	   
	   @PostMapping()
	   @ResponseBody
	   public JsonResult add(GalleryVo galleryVo, @RequestParam("file") MultipartFile uploadfile) {
		   System.out.println(galleryVo);
		   galleryService.store(galleryVo, uploadfile);
		   return JsonResult.success(galleryVo);
	   }
	   
	   @GetMapping()
	   @ResponseBody
	   public JsonResult fetch() {
		   return JsonResult.success(galleryService.fetch());
	   }
}

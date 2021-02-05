package com.bitacademy.mysite.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bitacademy.mysite.repository.GalleryRepository;
import com.bitacademy.mysite.vo.GalleryVo;

@Service
public class GalleryService {
	private static final String SAVE_PATH = "/mysite-uploads/gallery";
	private static final String URL_BASE = "/images/gallery";
	
	@Autowired
	GalleryRepository galleryRepository;

	private String _extName(String originFilename) {
		return originFilename.substring(originFilename.lastIndexOf('.') + 1);
	}

	private String _url(String saveFilename) {
		return URL_BASE + "/" + saveFilename;
	}

	private String genSaveFilename(String extName) {
		String filename = "";
		Calendar calendar = Calendar.getInstance();
		filename += calendar.get(Calendar.YEAR);
		filename += calendar.get(Calendar.MONTH);
		filename += calendar.get(Calendar.DATE);
		filename += calendar.get(Calendar.HOUR);
		filename += calendar.get(Calendar.MINUTE);
		filename += calendar.get(Calendar.SECOND);
		filename += calendar.get(Calendar.MILLISECOND);
		filename += ("." + extName);
		return filename;
	}

	public GalleryVo store(GalleryVo galleryVo, MultipartFile multipartFile) {
		String saveFilename = genSaveFilename(_extName(multipartFile.getOriginalFilename()));
		try {
			OutputStream os = new FileOutputStream(SAVE_PATH + "/" + saveFilename);
			os.write(multipartFile.getBytes());
			os.close();

		} catch (IOException e) {
			throw new RuntimeException("file upload error:" + e);
		}
		
		galleryVo.setImageURL(_url(saveFilename));
		galleryRepository.insert(galleryVo);
		
		return galleryVo;
	}
	
	public List<GalleryVo> fetch() {
		return galleryRepository.select();
	}
}

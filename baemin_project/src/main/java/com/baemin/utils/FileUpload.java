package com.baemin.utils;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.baemin.dto.ReviewDto;

@Component
public class FileUpload {
	
	public boolean uploadReviewImg(ReviewDto reviewDto) {
		
		String today						 = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
		String uploadFolder			 = Paths.get("C:", "delivery", "upload").toString();
		String imageUploadFolder = Paths.get("reviewImg", today).toString();
		String uploadPath				 = Paths.get(uploadFolder, imageUploadFolder).toString();
		
		File dir = new File(uploadPath);
		if (dir.exists() == false) {
			dir.mkdirs();
		}
		
		UUID	 uuid					 = UUID.randomUUID();
		String reviewImgName = uuid + "_" + reviewDto.getFile().getOriginalFilename();
		
		try {
			File target = new File(uploadPath, reviewImgName);
			reviewDto.getFile().transferTo(target);
		} catch (Exception e) {
			return false;
		}
		
		reviewDto.setReviewImg("\\upload\\" + imageUploadFolder + "\\" + reviewImgName);
		
		return true;
	}
	
	public String uploadImg(MultipartFile file, String imgPathName) {
		
		String today						 = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
		String uploadForder			 = Paths.get("C:", "delivery", "upload").toString();
		String imageUploadForder = Paths.get(imgPathName, today).toString();
		String uploadPath				 = Paths.get(uploadForder, imageUploadForder).toString();
		
		File dir = new File(uploadPath);
		if (dir.exists() == false) {
			dir.mkdirs();
		}
		
		UUID	 uuid		 = UUID.randomUUID();
		String ImgName = uuid + "_" + file.getOriginalFilename();
		
		try {
			File target = new File(uploadPath, ImgName);
			file.transferTo(target);
		} catch (Exception e) {
		}
		
		return "\\upload\\" + imageUploadForder + "\\" + ImgName;
	}
	
}

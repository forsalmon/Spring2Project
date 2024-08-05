package kr.or.ddit.controller.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/test")
public class TestController {

	List<String> imageList = null;
	
	@RequestMapping(value="/test01.do", method=RequestMethod.GET)
	public String test(HttpServletRequest req, Model model) {
		
		imageList =  new ArrayList<String>();
		
		String savePath = req.getServletContext().getRealPath("/resources/image/");
		File files = new File(savePath);
		if(files.exists()) {
			File[] mFiles = files.listFiles();
			for(File f : mFiles) {
				imageList.add(f.getName());
			}
		}
		
		model.addAttribute("imageFileList", imageList);
		return "test/test01";
	}
	
	/*
		# 비동기 통신 시 단일 데이터 받는 방법
			1) 클라이언트에서 type : selectedValue로 설정하고
			      컨트롤러에서 @RequestBody String type과 같이 받는다.
			2) 클라이언트에서 data : JSON.stringify(data)로 설정하고
			      컨트롤러에서는 @RequestBody Map<String, String> map과 같이 받는다. 
	 */
	// 단일 데이터일 때 param-- map, 복수일 경우 param -- 객체
	@ResponseBody
	@RequestMapping(value="/changeImage.do", method=RequestMethod.POST)
	public ResponseEntity<List<String>> imageChange(
			@RequestBody Map<String, String> map
			){
		List<String> typeImageList = new ArrayList<String>();
		
		if(map.get("type").toString().equals("all")) {
			typeImageList = imageList;
		}else {
			for(int i = 0; i < imageList.size(); i++) {
				if(imageList.get(i).contains(map.get("type"))) {
					String image = imageList.get(i);
					typeImageList.add(image);
				}
			}
		}
		return new ResponseEntity<List<String>>(typeImageList, HttpStatus.OK);
	}
	
	
	/*
	@RequestMapping(value="/test02.do", method=RequestMethod.GET)
	public String seletValue(
			HttpServletRequest req, 
			@RequestParam("selValue") String selValue, Model model) {
		
		List<String> imageList = new ArrayList<String>();
		//String value = selValue;

		String savePath = req.getServletContext().getRealPath("/resources/image/");
		File files = new File(savePath);
		if(files.exists()) {
			
			File[] mFiles = files.listFiles();
			for(File f : mFiles) {
				String fileName = f.getName();
				String contentType = fileName.substring(fileName.lastIndexOf(".")+1);
				if (selValue == "all") {
					imageList.add(fileName);
				} else if (selValue == "jpg" && contentType.equals("jpg")) {
					imageList.add(fileName);
				} else if (selValue == "png" && contentType.equals("png")) {
					imageList.add(fileName);
				} else if (selValue == "gif" && contentType.equals("gif"))
					imageList.add(fileName);
			}
		}
		model.addAttribute("imageFileList", imageList);
		model.addAttribute("value", selValue);
		return "test/test01";
		*/
		
	}
	
	
	


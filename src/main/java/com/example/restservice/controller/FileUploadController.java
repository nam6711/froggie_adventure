package com.example.restservice.controller;

import java.io.IOException;
  
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.ExceptionHandler; 
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam; 
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.restservice.storage.StorageFileNotFoundException;
import com.example.restservice.storage.StorageService;

import java.util.logging.Logger; 

import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.JSchException;

@RestController
@RequestMapping("image")
public class FileUploadController {
	private static final Logger LOG = Logger.getLogger(FileUploadController.class.getName());

	private StorageService storageService;
 
	public FileUploadController(StorageService storageService) {
		this.storageService = storageService;
	} 

	@PostMapping("/")
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) throws JSchException, SftpException, IOException {
		LOG.info("POST " + file.getOriginalFilename());

		storageService.store(file, "");
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");

		return "redirect:/";
	}

	@PostMapping("/pfp")
	public String handleProfilePicUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) throws JSchException, SftpException, IOException {
		LOG.info("POST /pfp " + file.getOriginalFilename());
		
		storageService.store(file, "/pfp");
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");

		return "redirect:/";
	}

	@PostMapping("/posts")
	public String handlePostUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) throws JSchException, SftpException, IOException {
		LOG.info("POST /posts " + file.getOriginalFilename());
 
		storageService.store(file, "/posts");
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");

		return "redirect:/";
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}
}
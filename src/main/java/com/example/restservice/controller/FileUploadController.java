package com.example.restservice.controller;

import java.io.IOException;
import java.util.stream.Collectors;
 
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity; 
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.restservice.storage.StorageFileNotFoundException;
import com.example.restservice.storage.StorageService;

import java.util.logging.Logger; 

@RestController
@RequestMapping("image")
public class FileUploadController {
	private static final Logger LOG = Logger.getLogger(FileUploadController.class.getName());

	private StorageService storageService;
 
	public FileUploadController(StorageService storageService) {
		this.storageService = storageService;
	}

	@GetMapping("/")
	public String listUploadedFiles(Model model) throws IOException {
		LOG.info("GET /images");

		model.addAttribute("files", storageService.loadAll().map(
				path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
						"serveFile", path.getFileName().toString()).build().toUri().toString())
				.collect(Collectors.toList()));

		return "uploadForm";
	}

	@GetMapping("/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveDefault(@PathVariable String filename) {
		LOG.info("GET " + filename);

		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

	@GetMapping("/pfp/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveProfilePic(@PathVariable String filename) {
		LOG.info("GET /pfp " + filename);

		Resource file = storageService.loadAsResource("pfp/" + filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

	@GetMapping("/posts/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> servePost(@PathVariable String filename) {
		LOG.info("GET /posts " + filename);

		Resource file = storageService.loadAsResource("posts/" + filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

	@PostMapping("/")
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {
		LOG.info("POST " + file.getOriginalFilename());

		storageService.store(file, "");
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");

		return "redirect:/";
	}

	@PostMapping("/pfp")
	public String handleProfilePicUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {
		LOG.info("POST /pfp " + file.getOriginalFilename());
		
		storageService.store(file, "/pfp");
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");

		return "redirect:/";
	}

	@PostMapping("/posts")
	public String handlePostUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {
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
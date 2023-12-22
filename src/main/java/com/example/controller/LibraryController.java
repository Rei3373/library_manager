package com.example.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Library;
import com.example.entity.Log;
import com.example.service.LibraryService;
import com.example.service.LogService;
import com.example.service.LoginUser;

@Controller
@RequestMapping("library")
public class LibraryController {

	private final LibraryService libraryService;
	private final LogService logService;

	@Autowired
	public LibraryController(LibraryService libraryService, LogService logService) {
		this.libraryService = libraryService;
		this.logService = logService;
	}

	@GetMapping
	public String index(Model model) {
		List<Library> libraries = libraryService.findAllLibrary();
		model.addAttribute("libraries", libraries);
		return "library/index";
	}

	@GetMapping("/borrow")
	public String borrowingForm(@RequestParam("id") Integer id, Model model) {
		Library library = libraryService.findById(id).get();
		model.addAttribute("library", library);
		return "/library/borrowingForm";
	}

	@PostMapping("/borrow")
	public String borrow(@RequestParam("id") Integer id, @RequestParam("return_due_date") String returnDueDate, @AuthenticationPrincipal LoginUser loginUser) {
		Library library = libraryService.findById(id).get();
		library.setUserId(loginUser.getUser().getId());
		libraryService.update(library);

		Log log = new Log();
		log.setLibraryId(id);
		log.setUserId(loginUser.getUser().getId());
		log.setRentDate(LocalDateTime.now());
		log.setReturnDate(null);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		log.setReturnDueDate(LocalDateTime.parse(returnDueDate + " 00:00:00", formatter));
		log.setReturnDate(null);
		logService.create(log);
		return "redirect:/library";
	}

	@PostMapping("/return")
	public String returnBook(@RequestParam("id") Integer id, @AuthenticationPrincipal LoginUser loginUser) {
		Library library = libraryService.findById(id).get();
		library.setUserId(0);
		libraryService.update(library);

		Log log = logService.findRetrunTarget(id, loginUser.getUser().getId()).get();
		log.setReturnDate(LocalDateTime.now());
		logService.update(log);
		return "redirect:/library";
	}

	@GetMapping("/history")
	public String history(Model model, @AuthenticationPrincipal LoginUser loginUser) {
		List<Log> logs = logService.findByUserId(loginUser.getUser().getId());
		model.addAttribute("logs", logs);
		return "/library/borrowHistory";
	}

}
package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Library;
import com.example.service.LibraryService;
import com.example.service.LogService;

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
        List<Library> libraries = this.libraryService.findAllLibrary();
        model.addAttribute("libraries", libraries);
        return "library/index";
    }
    
    @GetMapping("/borrow")
    public String borrowingForm(@RequestParam("id")Integer id, Model model) {
    	Library library = libraryService.findById(id).get();
    	return "/library/borrowingForm";
    }
}
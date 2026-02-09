package com.example.smokingspotfinder.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.smokingspotfinder.service.ScrapingService;

@Controller
public class ScrapingController {
	private final ScrapingService scrapingService;
	public ScrapingController(ScrapingService ss) {
		this.scrapingService = ss;
	}

	@GetMapping("/scrape/save")
	@ResponseBody
	public String scrapeAndSave() throws IOException {
		String url = "https://www.city.osaka.lg.jp/kankyo/page/0000607135.html";
		scrapingService.scrapeAndSave(url);
		return "done";
	}

}

package com.example.smokingspotfinder.controller;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.smokingspotfinder.entity.SmokingPoint;
import com.example.smokingspotfinder.service.SmokingPointService;

@Controller
@RequestMapping("/smoking-points")
public class SmokingPointController {
	private final SmokingPointService spService;

	public SmokingPointController(SmokingPointService spService) {
		this.spService = spService;
	}

	/** メイン画面（地図とpoint一覧） */
	@GetMapping
	public String main(@RequestParam(required = false) Double lat,
			@RequestParam(required = false) Double lng, Model model) {
		if (lat != null && lng != null) {
			model.addAttribute("list", spService.getNearest20(lat, lng));
		} else {
			model.addAttribute("list", null);
		}
		model.addAttribute("latlngList", spService.findAllLatLng());
		return "smoking-point/main";
	}

	/** 詳細画面 */
	@GetMapping("/{id}")
	public String detail(@PathVariable Long id, Model model) {
		SmokingPoint sp = spService.findById(id);
		if (sp == null) {
			return "redirect:/smoking-points/";
		}
		model.addAttribute("sp", sp);
		return "smoking-point/detail";
	}

	/** 新規登録画面 */
	@GetMapping("/new")
	public String showCreateForm(Model model) {
		model.addAttribute("smokingPoint", new SmokingPoint());
		return "smoking-point/form";
	}

	@PostMapping("/new")
	public String create(@Valid @ModelAttribute SmokingPoint smokingPoint,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "smoking-point/form";
		}
		spService.register(smokingPoint);
		return "redirect:/smoking-points";
	}

	/** 編集画面 */
	@GetMapping("/{id}/edit")
	public String showSPEdit(@PathVariable Long id, Model model) {
		model.addAttribute("sp", spService.findById(id));
		model.addAttribute("mode", "edit");
		return "smoking-point/edit";
	}

	@PostMapping("/{id}/edit")
	public String edit(@Valid @ModelAttribute("sp") SmokingPoint smokingPoint,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "smoking-point/edit";
		}
		spService.update(smokingPoint);
		return "redirect:/smoking-points";
	}

	/** 削除画面 */
	@GetMapping("/{id}/delete")
	public String showSPDelete(@PathVariable Long id, Model model) {
		model.addAttribute("sp", spService.findById(id));
		model.addAttribute("mode", "delete");
		return "smoking-point/edit";
	}

	@PostMapping("/{id}/delete")
	public String delete(@Valid @ModelAttribute("sp") SmokingPoint smokingPoint,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "smoking-point/edit";
		}
		spService.delete(smokingPoint.getId());
		return "redirect:/smoking-points";
	}
}

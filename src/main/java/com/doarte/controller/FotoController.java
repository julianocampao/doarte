package com.doarte.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.doarte.storage.FotoStorageLocal;

@RestController
@RequestMapping("/fotos")
public class FotoController {

	@Autowired
	private FotoStorageLocal fotoStorage;

	@PostMapping
	public String upload(@RequestParam("files[]") MultipartFile files) {
		System.out.println(">>> files: " + files.getSize() + "content " + files.getContentType() + " name "
				+ files.getOriginalFilename());

		String response = fotoStorage.salvarArquivo(files);
		System.out.println(response);
		return response;
	}

	@GetMapping("/{nome:.*}")
	public byte[] recuperar(@PathVariable String nome) {
		return fotoStorage.recuperarFotoTemporaria(nome);
	}
}
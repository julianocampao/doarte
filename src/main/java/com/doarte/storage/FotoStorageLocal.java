package com.doarte.storage;

import static java.nio.file.FileSystems.getDefault;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FotoStorageLocal {

	public static Path local = getDefault().getPath(System.getenv("HOME"), ".doartefotos");

	private void criarPasta() {
		try {
			Files.createDirectories(this.local);

		} catch (IOException e) {
			throw new RuntimeException("Erro criando pasta para salvar foto", e);
		}
	}

	public String salvarArquivo(MultipartFile file) {
		criarPasta();
		System.out.println("Salvar arquivo chamado");
		String novoNome = null;
		if (file != null) {
			MultipartFile arquivo = file;
			novoNome = renomearArquivo(arquivo.getOriginalFilename());
			try {
				arquivo.transferTo(
						new File(this.local.toAbsolutePath().toString() + getDefault().getSeparator() + novoNome));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return novoNome;
	}

	public byte[] recuperarFotoTemporaria(String nome) {
		try {

			return Files.readAllBytes(this.local.resolve(nome));
		} catch (IOException e) {
			throw new RuntimeException("Erro lendo a foto ", e);
		}
	}

	private String renomearArquivo(String nomeOriginal) {
		String novoNome = UUID.randomUUID().toString();

		return novoNome;

	}

}

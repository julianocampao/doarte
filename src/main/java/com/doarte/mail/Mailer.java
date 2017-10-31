package com.doarte.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.doarte.model.Instituicao;
import com.doarte.model.Item;

@Component
public class Mailer {

	@Autowired
	private JavaMailSender mailSender;

	public void notificarInstituicaoItem(Instituicao instituicao, Item item) {
		SimpleMailMessage mensagem = new SimpleMailMessage();
		mensagem.setFrom("doarte@doarteapp.com");
		mensagem.setTo(instituicao.getEmail());
		mensagem.setSubject("Novo item cadastrado no portal doarte.");
		mensagem.setText("O " + item.getDescricao() + " está aguardando alguma instituição no portal doarte");

		mailSender.send(mensagem);
	}

	public void notificarUsuario(Instituicao instituicao, Item item) {
		SimpleMailMessage mensagem = new SimpleMailMessage();
		mensagem.setFrom("doarte@doarteapp.com");
		mensagem.setTo(item.getUsuario().getEmail());
		mensagem.setSubject("Seu item foi aceito por uma instituição.");

		mensagem.setText(
				"A instituição interessada nome " + instituicao.getNome() + " contato " + instituicao.getContato()
						+ " e email " + instituicao.getEmail() + " para contato e combinar a entrega");

		mailSender.send(mensagem);
	}

}
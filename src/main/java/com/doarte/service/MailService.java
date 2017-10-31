package com.doarte.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.doarte.mail.Mailer;
import com.doarte.model.Instituicao;
import com.doarte.model.Item;
import com.doarte.repository.Instituicoes;

@Service
public class MailService {

	@Autowired
	private Instituicoes instituicoes;

	@Autowired
	private Mailer mailer;

	@Async
	public void notificarInstituicoes(Item item) {
		instituicoes.findAll().forEach(e -> {
			try {
				mailer.notificarInstituicaoItem(e, item);
			} catch (Exception e2) {
				e2.printStackTrace();
			}

		});
	}
	
	@Async
	public void notificarUsuario(Instituicao instituicao,Item item) {
		
			try {
				mailer.notificarUsuario(instituicao, item);
			} catch (Exception e2) {
				e2.printStackTrace();
			}

		
	}

}

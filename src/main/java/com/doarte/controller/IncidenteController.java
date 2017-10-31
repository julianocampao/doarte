package com.doarte.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.doarte.model.Incidente;
import com.doarte.model.StatusItem;
import com.doarte.model.Usuario;
import com.doarte.repository.Cadastros;
import com.doarte.repository.Estados;
import com.doarte.repository.Incidentes;
import com.doarte.repository.Itens;

@Controller
public class IncidenteController {

	@Autowired
	private Estados estados;

	@Autowired
	private Incidentes incidentes;

	@Autowired
	private Cadastros cadastros;
	
	@Autowired
	private Itens itens;

	@GetMapping("cadastroIncidente")
	public ModelAndView cadastroIncidente(Incidente incidente) {
		ModelAndView mv = new ModelAndView("admin/cadastroIncidente");
		mv.addObject("estados", estados.findAll());
		mv.addObject("cadastro",
				cadastros.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get());
		notificacoes(mv);
		return mv;
	}
	
	public void notificacoes(ModelAndView mv) {
		if (cadastros.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
				.get() instanceof Usuario) {
			Long resultadoNotificacaoUsuario = itens.countByTipoItemAndUsuario(StatusItem.DOADO, (Usuario) cadastros
					.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get());
			mv.addObject("notificacaoUsuario", resultadoNotificacaoUsuario == null ? 0 : resultadoNotificacaoUsuario);
			mv.addObject("notificacaoLista",
					itens.findByTipoItemAndUsuario(StatusItem.DOADO,
							(Usuario) cadastros
									.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
									.get()));
		} else {
			Long resultadoNotificacaoInstituicao = itens.countByTipoItem(StatusItem.PENDENTE);
			mv.addObject("notificacaoInstituicao",
					resultadoNotificacaoInstituicao == null ? 0 : resultadoNotificacaoInstituicao);
			mv.addObject("notificacaoLista", itens.findByTipoItem(StatusItem.PENDENTE));
		}
	}

	@PostMapping("/cadastroIncidente")
	public ModelAndView cadastrar(@Valid Incidente incidente, Errors errors, RedirectAttributes attributes) {

		System.out.println(incidente);

		if (errors.hasErrors()) {
			return cadastroIncidente(incidente);
		}

		incidentes.save(incidente);

		attributes.addFlashAttribute("mensagem", "Incidente salvo com sucesso!");
		return new ModelAndView("redirect:cadastroIncidente");

	}

}

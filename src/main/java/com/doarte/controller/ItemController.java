package com.doarte.controller;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.doarte.model.Instituicao;
import com.doarte.model.Item;
import com.doarte.model.Necessidade;
import com.doarte.model.StatusItem;
import com.doarte.model.Usuario;
import com.doarte.repository.Cadastros;
import com.doarte.repository.Categorias;
import com.doarte.repository.Itens;
import com.doarte.repository.Necessidades;
import com.doarte.repository.Usuarios;
import com.doarte.service.MailService;

@Controller
public class ItemController {

	@Autowired
	private Itens itens;

	@Autowired
	private Necessidades necessidades;

	@Autowired
	private MailService mailService;

	@Autowired
	private Categorias categorias;

	@Autowired
	private Cadastros cadastros;

	@Autowired
	private Usuarios usuarios;

	@GetMapping("dashboard")
	public ModelAndView adminIndex() {
		ModelAndView mv = new ModelAndView("admin/dashboard");

		notificacoes(mv);

		mv.addObject("cadastro",
				cadastros.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get());

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

	@GetMapping("cadastroItem")
	public ModelAndView cadastroDeItem(Item item) {
		ModelAndView mv = new ModelAndView("admin/cadastroItem");
		mv.addObject("cadastro",
				cadastros.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get());
		mv.addObject("categorias", categorias.findAll());
		notificacoes(mv);
		return mv;
	}

	@GetMapping("itensDisponiveis")
	public ModelAndView listagemItens() {
		ModelAndView mv = new ModelAndView("admin/itensDisponiveis");
		mv.addObject("itens", itens.findByTipoItem(StatusItem.PENDENTE));
		mv.addObject("cadastro",
				cadastros.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get());
		notificacoes(mv);
		return mv;
	}

	@GetMapping("item/{codigo}/aceitar")
	public ModelAndView aceitarItem(@PathVariable Long codigo) {
		System.out.println(codigo);
		Item item = itens.findOne(codigo);
		item.setTipoItem(StatusItem.DOADO);
		item.setInstituicao((Instituicao) cadastros
				.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get());

		itens.save(item);

		mailService
				.notificarUsuario(
						(Instituicao) cadastros
								.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get(),
						item);

		return listagemItens();
	}

	@GetMapping("meusItens")
	public ModelAndView itensDisponiveis() {
		ModelAndView mv = new ModelAndView("admin/meusItens");
		mv.addObject("itens", itens.findByUsuario((Usuario) cadastros
				.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get()));
		mv.addObject("cadastro",
				cadastros.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get());
		notificacoes(mv);
		return mv;
	}

	@GetMapping("meusItens/cancelar/{codigo}")
	public ModelAndView cancelar(@PathVariable("codigo") Long id) {
		System.out.println(id);
		Item item = itens.findOne(id);
		item.setTipoItem(StatusItem.CANCELADO);
		itens.save(item);

		return itensDisponiveis();
	}

	@GetMapping("meusItensInstituicao")
	public ModelAndView itensDisponiveisInstituicao() {
		ModelAndView mv = new ModelAndView("admin/meusItensInstituicao");
		mv.addObject("itens", itens.findByInstituicao((Instituicao) cadastros
				.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get()));
		itens.findByInstituicao((Instituicao) cadastros
				.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get()).forEach(e -> {
					System.out.println(e.getAvaliacaoUsuario());
				});
		mv.addObject("cadastro",
				cadastros.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get());
		notificacoes(mv);
		return mv;
	}

	@PostMapping("cadastroItem")
	public ModelAndView cadastrar(@Valid Item item, Errors errors, RedirectAttributes attributes) {

		System.out.println(item);
		if (StringUtils.isBlank(item.getUrlImagem())) {
			System.out.println("é blank");
			item.setUrlImagem("usuarioDefault.png");
		}
		if (errors.hasErrors()) {
			return cadastroDeItem(item);
		}

		item.setTipoItem(StatusItem.PENDENTE);

		item.setUsuario((Usuario) cadastros
				.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get());

		item = itens.save(item);

		mailService.notificarInstituicoes(item);

		attributes.addFlashAttribute("mensagem", "Item salvo com sucesso!");
		return new ModelAndView("redirect:cadastroItem");

	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, value = "rating/instituicao")
	public @ResponseBody ResponseEntity<?> rantingInstituicao(@RequestBody Item item) {
		System.out.println(item);
		int numeroNovo = item.getAvaliacaoInstituicao();
		item = itens.findOne(item.getId());
		item.setAvaliacaoInstituicao(numeroNovo);
		itens.save(item);
		return ResponseEntity.ok(item);

	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, value = "rating/usuario")
	public @ResponseBody ResponseEntity<?> rantingUsuario(@RequestBody Item item) {
		System.out.println(item);
		int numeroNovo = item.getAvaliacaoUsuario();
		item = itens.findOne(item.getId());
		item.setAvaliacaoUsuario(numeroNovo);
		;
		itens.save(item);
		return ResponseEntity.ok(item);

	}

	@GetMapping("cadastroNecessidade")
	public ModelAndView necessidade(Necessidade necessidade) {
		ModelAndView mv = new ModelAndView("admin/CadastroNecessidade");
		mv.addObject("categorias", categorias.findAll());
		mv.addObject("cadastro",
				cadastros.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get());
		notificacoes(mv);
		return mv;
	}

	@PostMapping("cadastroNecessidade")
	public ModelAndView cadastrarNecessidade(@Valid Necessidade necessidade, Errors errors,
			RedirectAttributes attributes) {

		System.out.println(necessidade);

		if (StringUtils.isBlank(necessidade.getUrlImagem())) {

			necessidade.setUrlImagem("usuarioDefault.png");
		}

		if (errors.hasErrors()) {

			return necessidade(necessidade);
		}

		necessidade.setInstituicao(necessidade.setInstituicao((Instituicao) cadastros
				.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get()));
		necessidade = necessidades.save(necessidade);

		attributes.addFlashAttribute("mensagem", "Necessidade cadastrada com sucesso!");
		return new ModelAndView("redirect:cadastroNecessidade");

	}

}

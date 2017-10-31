package com.doarte.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.doarte.model.Categoria;
import com.doarte.model.Cidade;
import com.doarte.model.Estado;
import com.doarte.model.Instituicao;
import com.doarte.model.Item;
import com.doarte.model.Necessidade;
import com.doarte.model.Permissao;
import com.doarte.model.StatusItem;
import com.doarte.model.Usuario;
import com.doarte.model.UsuarioPermissao;
import com.doarte.repository.Categorias;
import com.doarte.repository.Cidades;
import com.doarte.repository.Estados;
import com.doarte.repository.Incidentes;
import com.doarte.repository.Instituicoes;
import com.doarte.repository.Itens;
import com.doarte.repository.Necessidades;
import com.doarte.repository.Usuarios;
import com.doarte.repository.UsuariosPermissoes;

@Controller
public class DoarteController {

	@Autowired
	private Instituicoes instituicoes;

	@Autowired
	private Necessidades necessidades;

	@Autowired
	private Incidentes incidentes;

	@Autowired
	private Usuarios usuarios;

	@Autowired
	private Estados estados;

	@Autowired
	private Cidades cidades;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UsuariosPermissoes usuariosPermissoes;

	@Autowired
	private Itens itens;

	@Autowired
	private Categorias categorias;

	@GetMapping("index")
	public ModelAndView index() {

		System.out.println(passwordEncoder.encode("123"));

		ModelAndView mv = new ModelAndView("index");
		mv.addObject("categorias", categorias.findAll());

		List<Necessidade> instuicoes = necessidades.findAll();
		System.out.println("tamanho lista " + instuicoes.size());
		if (instuicoes.size() >= 1) {
			mv.addObject("inst1", instuicoes.get(0));
		}

		if (instuicoes.size() > 1) {
			mv.addObject("inst2", instuicoes.get(1));
		}

		return mv;
	}

	@GetMapping("login")
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView("login");

		return mv;
	}

	@GetMapping("feedInstituicoes")
	public ModelAndView feedInstituicoes() {
		ModelAndView mv = new ModelAndView("feedInstituicoes");
		mv.addObject("instituicoes", instituicoes.findAll());

		return mv;
	}

	@GetMapping("feedItensDoados")
	public ModelAndView feedItensDoados() {
		ModelAndView mv = new ModelAndView("feedItensDoados");
		mv.addObject("itens", itens.findByTipoItem(StatusItem.PENDENTE));
		return mv;
	}

	@GetMapping("feedDesastres")
	public ModelAndView feedDesastres() {
		ModelAndView mv = new ModelAndView("feedDesastres");
		mv.addObject("incidentes", incidentes.findAll());

		return mv;
	}

	@GetMapping("cadastroUsuario")
	public ModelAndView cadastroUsuario(Usuario usuario) {
		ModelAndView mv = new ModelAndView("cadastroUsuario");

		if (estados.findAll().size() == 0) {

			Estado estado = new Estado();
			estado.setNome("Sergipe");
			estado.setSigla("SE");
			estado = estados.save(estado);

			Cidade cidade = new Cidade();
			cidade.setNome("Aracaju");
			cidade.setEstado(estado);

			cidades.save(cidade);
		}
		mv.addObject("estados", estados.findAll());

		return mv;
	}

	@GetMapping("cadastroInstituicao")
	public ModelAndView cadastroInstituicao(Instituicao instituicao) {
		ModelAndView mv = new ModelAndView("cadastroInstituicao");
		if (estados.findAll().size() == 0) {

			Estado estado = new Estado();
			estado.setNome("Sergipe");
			estado.setSigla("SE");
			estado = estados.save(estado);

			Cidade cidade = new Cidade();
			cidade.setNome("Aracaju");
			cidade.setEstado(estado);
			cidades.save(cidade);
		}
		mv.addObject("estados", estados.findAll());

		return mv;
	}

	@PostMapping("cadastroInstituicao")
	public ModelAndView salvar(@Valid Instituicao instituicao, Errors errors, RedirectAttributes attributes) {
		System.out.println(instituicao);
		if (StringUtils.isBlank(instituicao.getUrlImagem())) {
			System.out.println("é blank");
			instituicao.setUrlImagem("instituicaoDefault.png");
		}
		if (errors.hasErrors()) {
			return cadastroInstituicao(instituicao);
		}
		instituicao.setSenha(passwordEncoder.encode(instituicao.getSenha()));

		instituicao = instituicoes.save(instituicao);

		UsuarioPermissao usuarioPermissao = new UsuarioPermissao();
		Permissao permissao = new Permissao();
		permissao.setId(new Long(2));
		usuarioPermissao.setCadastro(instituicao);
		usuarioPermissao.setPermissao(permissao);
		usuariosPermissoes.save(usuarioPermissao);

		attributes.addFlashAttribute("mensagem", "Instituicao salva com sucesso!");
		return new ModelAndView("redirect:cadastroInstituicao");

	}

	@PostMapping("cadastroUsuario")
	public ModelAndView salvar(@Valid Usuario usuario, Errors errors, RedirectAttributes attributes) {
		System.out.println(usuario);

		if (StringUtils.isBlank(usuario.getUrlImagem())) {
			System.out.println("é blank");
			usuario.setUrlImagem("usuarioDefault.png");
		}
		if (errors.hasErrors()) {
			return cadastroUsuario(usuario);
		}
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		usuario = usuarios.save(usuario);

		UsuarioPermissao usuarioPermissao = new UsuarioPermissao();
		Permissao permissao = new Permissao();
		permissao.setId(new Long(1));
		usuarioPermissao.setCadastro(usuario);
		usuarioPermissao.setPermissao(permissao);
		usuariosPermissoes.save(usuarioPermissao);

		attributes.addFlashAttribute("mensagem", "Usuario salva com sucesso!");
		return new ModelAndView("redirect:cadastroUsuario");

	}

	@RequestMapping(method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE }, value = "cidade/{codigo}/list")
	public ResponseEntity<List<Cidade>> listarPorEstado(@PathVariable("codigo") Long codigo) {
		System.out.println(codigo);
		Estado estado = new Estado();
		estado.setId(codigo);
		return ResponseEntity.status(HttpStatus.OK).body(cidades.findByEstado(estado));
	}

	@RequestMapping(method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE }, value = "buscar/{busca}/{categoria}")
	public ResponseEntity<List<Item>> listarPorEstado(@PathVariable("busca") String busca,
			@PathVariable("categoria") String categoria) {
		System.out.println(busca);
		System.out.println(categoria);
		List<Item> itensList = new ArrayList<>();
		Categoria cat = new Categoria();
		cat.setId(new Long(categoria));
		if (busca.equals("0") && categoria.equals("0")) {
			itensList = itens.findByTipoItem(StatusItem.PENDENTE);
		}
		if (busca.equals("0") && !categoria.equals("0")) {
			itensList = itens.findByTipoItemAndCategoria(StatusItem.PENDENTE, cat);

		}
		if (!busca.equals("0") && !categoria.equals("0")) {
			itensList = itens.findByDescricaoContainingAndTipoItemAndCategoria(busca, StatusItem.PENDENTE, cat);
		}
		if (!busca.equals("0") && categoria.equals("0")) {
			itensList = itens.findByDescricaoContainingAndTipoItem(busca,StatusItem.PENDENTE);
		}

		itensList.forEach(e -> {
			System.out.println(e.getDescricao());
			e.setAvaliacaoInstituicao(null);
			e.setAvaliacaoUsuario(null);
			e.setInstituicao(null);
			e.setUsuario(null);

		});
		return ResponseEntity.status(HttpStatus.OK)
				.body(itensList);
	}

}

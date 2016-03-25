/*
 * Código-fonte do livro "Programação Java para a Web"
 * Autores: Décio Heinzelmann Luckow <decioluckow@gmail.com>
 *          Alexandre Altair de Melo <alexandremelo.br@gmail.com>
 *
 * ISBN: 978-85-7522-238-6
 * http://www.javaparaweb.com.br
 * http://www.novatec.com.br/livros/javaparaweb
 * Editora Novatec, 2010 - todos os direitos reservados
 *
 * LICENÇA: Este arquivo-fonte está sujeito a Atribuição 2.5 Brasil, da licença Creative Commons,
 * que encontra-se disponível no seguinte endereço URI: http://creativecommons.org/licenses/by/2.5/br/
 * Se você não recebeu uma cópia desta licença, e não conseguiu obtê-la pela internet, por favor,
 * envie uma notificação aos seus autores para que eles possam enviá-la para você imediatamente.
 *
 *
 * Source-code of "Programação Java para a Web" book
 * Authors: Décio Heinzelmann Luckow <decioluckow@gmail.com>
 *          Alexandre Altair de Melo <alexandremelo.br@gmail.com>
 *
 * ISBN: 978-85-7522-238-6
 * http://www.javaparaweb.com.br
 * http://www.novatec.com.br/livros/javaparaweb
 * Editora Novatec, 2010 - all rights reserved
 *
 * LICENSE: This source file is subject to Attribution version 2.5 Brazil of the Creative Commons
 * license that is available through the following URI:  http://creativecommons.org/licenses/by/2.5/br/
 * If you did not receive a copy of this license and are unable to obtain it through the web, please
 * send a note to the authors so they can mail you a copy immediately.
 *
 */
package br.com.siam.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.StreamedContent;
import org.springframework.util.DigestUtils;

import br.com.siam.dao.UsuarioRN;
import br.com.siam.entidade.Usuario;
import br.com.siam.util.UtilException;
import br.com.siam.web.util.ContextoUtil;
import br.com.siam.web.util.RelatorioUtil;

@ManagedBean(name="usuarioMB")
@RequestScoped
public class UsuarioMB extends GenericMB {

	private Usuario	    usuario	= new Usuario();
	private String	       confirmarSenha;
	private List<Usuario> lista;
	private String destinoSalvar;
	private boolean exibeCamposSenha = true;
	private StreamedContent	arquivoRetorno;
	private int	tipoRelatorio;

	public String novo() {
		this.destinoSalvar = "usuarioSucesso";
		this.usuario = new Usuario();
		this.usuario.setAtivo(true);
		return "usuario";
	}

	public String editar() {
		this.confirmarSenha = this.usuario.getSenha();
		this.exibeCamposSenha = false;
		return "/publico/usuario";
	}
	
	public void main() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("../main.jsf");
	}
	
	public void teste() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("../teste.jsf");
	}

	public String salvar() {
		FacesContext context = FacesContext.getCurrentInstance();

		String senha = this.usuario.getSenha();
		if (senha != null &&
			senha.trim().length() > 0 &&
			!senha.equals(this.confirmarSenha)) {
				FacesMessage facesMessage = new FacesMessage("A senha não foi confirmada corretamente.");
//				getLogger().error("A senha não foi confirmada corretamente.");
				context.addMessage(null, facesMessage);
				return null;
		} else {
			String senhaCripto = DigestUtils.md5DigestAsHex(senha.getBytes());
			this.usuario.setSenha(senhaCripto);
		}
		
		UsuarioRN usuarioRN = new UsuarioRN();
		usuarioRN.salvar(usuario);
		
		return this.destinoSalvar;
	}
	
	public String excluir() {
		UsuarioRN usuarioRN = new UsuarioRN();
		usuarioRN.excluir(this.usuario);
		this.lista = null;
		return null;
	}
	
	public String ativar() {
		if (this.usuario.getAtivo()) {
			this.usuario.setAtivo(false);
		} else {
			this.usuario.setAtivo(true);
		}
		UsuarioRN usuarioRN = new UsuarioRN();
		usuarioRN.salvar(this.usuario);
		return null;
	}
	
	public String atribuirPermissao(Usuario usuario, String permissao) {

		this.usuario = usuario;

		Set<String> permissoes = this.usuario.getPermissao();

		if (permissoes.contains(permissao)) {
			permissoes.remove(permissao);
		} else {
			permissoes.add(permissao);
		}
		return null;
	}
	
	public StreamedContent getArquivoRetorno() throws br.com.siam.util.UtilException {
		FacesContext context = FacesContext.getCurrentInstance();
		ContextoMB contextoMB = ContextoUtil.getContextoBean();
		String usuario = contextoMB.getUsuarioLogado().getLogin();
		String nomeRelatorioJasper = "usuarios";
		String nomeRelatorioSaida = "Usuarios";
		RelatorioUtil relatorioUtil = new RelatorioUtil();
		HashMap parametrosRelatorio = new HashMap();
		//parametrosRelatorio.put("codigoUsuario", contextoMB.getUsuarioLogado().getId());
		try {
			this.arquivoRetorno = relatorioUtil.geraRelatorio(parametrosRelatorio, nomeRelatorioJasper, nomeRelatorioSaida, this.tipoRelatorio);
		} catch (UtilException e) {
			context.addMessage(null, new FacesMessage(e.getMessage()));
			return null;
		} 
		return this.arquivoRetorno;
	}
	
	public List<Usuario> getLista() {
		if (this.lista == null) {
			UsuarioRN usuarioRN = new UsuarioRN();
			this.lista = usuarioRN.listar();
		}
		return this.lista;
	}
	
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getConfirmarSenha() {
		return confirmarSenha;
	}

	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
	}

	public void setLista(List<Usuario> lista) {
		this.lista = lista;
	}

	public String getDestinoSalvar() {
		return destinoSalvar;
	}

	public void setDestinoSalvar(String destinoSalvar) {
		this.destinoSalvar = destinoSalvar;
	}

	public boolean isExibeCamposSenha() {
		return exibeCamposSenha;
	}

	public int getTipoRelatorio() {
		return tipoRelatorio;
	}

	public void setTipoRelatorio(int tipoRelatorio) {
		this.tipoRelatorio = tipoRelatorio;
	}
}

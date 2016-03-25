package br.com.siam.dao;

import java.util.List;

import br.com.siam.entidade.Produto;

public interface ProdutoDAO {

	public void salvar(Produto produto);

	public List<Produto> listar();

}

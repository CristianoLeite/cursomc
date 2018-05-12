package com.cristiano.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cristiano.cursomc.domain.Categoria;
import com.cristiano.cursomc.domain.Cidade;
import com.cristiano.cursomc.domain.Cliente;
import com.cristiano.cursomc.domain.Endereco;
import com.cristiano.cursomc.domain.Estado;
import com.cristiano.cursomc.domain.ItemPedido;
import com.cristiano.cursomc.domain.Pagamento;
import com.cristiano.cursomc.domain.PagamentoComBoleto;
import com.cristiano.cursomc.domain.PagamentoComCartao;
import com.cristiano.cursomc.domain.Pedido;
import com.cristiano.cursomc.domain.Produto;
import com.cristiano.cursomc.domain.enums.EstadoPagamento;
import com.cristiano.cursomc.domain.enums.TipoCliente;
import com.cristiano.cursomc.repositories.CategoriaRepository;
import com.cristiano.cursomc.repositories.CidadeRepository;
import com.cristiano.cursomc.repositories.ClienteRepository;
import com.cristiano.cursomc.repositories.EnderecoRepository;
import com.cristiano.cursomc.repositories.EstadoRepository;
import com.cristiano.cursomc.repositories.ItemPedidoRepository;
import com.cristiano.cursomc.repositories.PagamentoRepository;
import com.cristiano.cursomc.repositories.PedidoRepository;
import com.cristiano.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null,"Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama mesa e banho");
		Categoria cat4 = new Categoria(null, "jardinagem");
		Categoria cat5 = new Categoria(null, "decoração");
		Categoria cat6 = new Categoria(null, "perfumaria");
		Categoria cat7 = new Categoria(null, "eletrônicos");
		
		
		Produto p1 = new Produto(null,"computador", 2000.00);
		Produto p2 = new Produto(null,"impressora", 800.00);
		Produto p3 = new Produto(null,"computador", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
					
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2,cat3,cat4,cat5,cat6,cat7));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		estadoRepository.saveAll(Arrays.asList(est1,est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@uol.com.br","399986954" , TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("996640786","998878855"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "123", "Casa", "Bairro novo", "80630000", cli1, c1);
		
		Endereco e2 = new Endereco(null, "rua Arvores", "124", "Apto 22", "Centro", "80989777", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1,e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null,sdf.parse("10/05/2018 16:17"),cli1, e1 );
		Pedido ped2 = new Pedido(null,sdf.parse("11/05/2018 10:00"),cli1, e2 );
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		
		pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
		
			
	}
	
	
}

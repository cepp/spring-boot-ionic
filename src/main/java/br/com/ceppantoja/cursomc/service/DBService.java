package br.com.ceppantoja.cursomc.service;

import br.com.ceppantoja.cursomc.domain.Categoria;
import br.com.ceppantoja.cursomc.domain.Cidade;
import br.com.ceppantoja.cursomc.domain.Cliente;
import br.com.ceppantoja.cursomc.domain.Endereco;
import br.com.ceppantoja.cursomc.domain.Estado;
import br.com.ceppantoja.cursomc.domain.ItemPedido;
import br.com.ceppantoja.cursomc.domain.Pagamento;
import br.com.ceppantoja.cursomc.domain.PagamentoComBoleto;
import br.com.ceppantoja.cursomc.domain.PagamentoComCartao;
import br.com.ceppantoja.cursomc.domain.Pedido;
import br.com.ceppantoja.cursomc.domain.Produto;
import br.com.ceppantoja.cursomc.domain.enums.EstadoPagamento;
import br.com.ceppantoja.cursomc.domain.enums.Perfil;
import br.com.ceppantoja.cursomc.domain.enums.TipoCliente;
import br.com.ceppantoja.cursomc.repositories.CategoriaRepository;
import br.com.ceppantoja.cursomc.repositories.CidadeRepository;
import br.com.ceppantoja.cursomc.repositories.ClienteRepository;
import br.com.ceppantoja.cursomc.repositories.EnderecoRepository;
import br.com.ceppantoja.cursomc.repositories.EstadoRepository;
import br.com.ceppantoja.cursomc.repositories.ItemPedidoRepository;
import br.com.ceppantoja.cursomc.repositories.PagamentoRepository;
import br.com.ceppantoja.cursomc.repositories.PedidoRepository;
import br.com.ceppantoja.cursomc.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

@Service
public class DBService {

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
    private PedidoRepository pedidoRepository;
    @Autowired
    private PagamentoRepository pagamentoRepository;
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void instatiateTestDatabase() throws ParseException {
        Categoria cat1 = new Categoria(null, "Informática");
        Categoria categoria2 = new Categoria(null, "Escritório");
        Categoria categoria3 = new Categoria(null, "Cama mesa e banho");
        Categoria categoria4 = new Categoria(null, "Eletrônicos");
        Categoria categoria5 = new Categoria(null, "Jardinagem");
        Categoria categoria6 = new Categoria(null, "Decoração");
        Categoria categoria7 = new Categoria(null, "Perfumaria");

        Produto produto1 = new Produto(null, "Computador", 2000.0);
        Produto produto2 = new Produto(null, "Impressora", 800.0);
        Produto produto3 = new Produto(null, "Mouse", 80.0);
        Produto produto4 = new Produto(null, "Mesa de escritório", 300.0);
        Produto produto5 = new Produto(null, "Toalha", 500.0);
        Produto produto6 = new Produto(null, "Colcha", 200.0);
        Produto produto7 = new Produto(null, "TV true color", 1200.0);
        Produto produto8 = new Produto(null, "Roçadeira", 800.0);
        Produto produto9 = new Produto(null, "Abajour", 100.0);
        Produto produto10 = new Produto(null, "Pendente", 180.0);
        Produto produto11 = new Produto(null, "Shampoo", 90.0);

        Produto p12 = new Produto(null,"Produto12",10.00);
        Produto p13 = new Produto(null,"Produto13",10.00);
        Produto p14 = new Produto(null,"Produto14",10.00);
        Produto p15 = new Produto(null,"Produto15",10.00);
        Produto p16 = new Produto(null,"Produto16",10.00);
        Produto p17 = new Produto(null,"Produto17",10.00);
        Produto p18 = new Produto(null,"Produto18",10.00);
        Produto p19 = new Produto(null,"Produto19",10.00);
        Produto p20 = new Produto(null,"Produto20",10.00);
        Produto p21 = new Produto(null,"Produto21",10.00);
        Produto p22 = new Produto(null,"Produto22",10.00);
        Produto p23 = new Produto(null,"Produto23",10.00);
        Produto p24 = new Produto(null,"Produto24",10.00);
        Produto p25 = new Produto(null,"Produto25",10.00);
        Produto p26 = new Produto(null,"Produto26",10.00);
        Produto p27 = new Produto(null,"Produto27",10.00);
        Produto p28 = new Produto(null,"Produto28",10.00);
        Produto p29 = new Produto(null,"Produto29",10.00);
        Produto p30 = new Produto(null,"Produto30",10.00);
        Produto p31 = new Produto(null,"Produto31",10.00);
        Produto p32 = new Produto(null,"Produto32",10.00);
        Produto p33 = new Produto(null,"Produto33",10.00);
        Produto p34 = new Produto(null,"Produto34",10.00);
        Produto p35 = new Produto(null,"Produto35",10.00);
        Produto p36 = new Produto(null,"Produto36",10.00);
        Produto p37 = new Produto(null,"Produto37",10.00);
        Produto p38 = new Produto(null,"Produto38",10.00);
        Produto p39 = new Produto(null,"Produto39",10.00);
        Produto p40 = new Produto(null,"Produto40",10.00);
        Produto p41 = new Produto(null,"Produto41",10.00);
        Produto p42 = new Produto(null,"Produto42",10.00);
        Produto p43 = new Produto(null,"Produto43",10.00);
        Produto p44 = new Produto(null,"Produto44",10.00);
        Produto p45 = new Produto(null,"Produto45",10.00);
        Produto p46 = new Produto(null,"Produto46",10.00);
        Produto p47 = new Produto(null,"Produto47",10.00);
        Produto p48 = new Produto(null,"Produto48",10.00);
        Produto p49 = new Produto(null,"Produto49",10.00);
        Produto p50 = new Produto(null,"Produto50",10.00);


        cat1.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3));
        categoria2.getProdutos().addAll(Arrays.asList(produto2, produto4));
        categoria3.getProdutos().addAll(Arrays.asList(produto5, produto6));
        categoria4.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3, produto7));
        categoria5.getProdutos().add(produto8);
        categoria6.getProdutos().addAll(Arrays.asList(produto9, produto10));
        categoria7.getProdutos().add(produto11);

        cat1.getProdutos().addAll(Arrays.asList(p12, p13, p14, p15, p16, p17, p18, p19, p20,
                p21, p22, p23, p24, p25, p26, p27, p28, p29, p30, p31, p32, p34, p35, p36, p37, p38,
                p39, p40, p41, p42, p43, p44, p45, p46, p47, p48, p49, p50));

        p12.getCategorias().add(cat1);
        p13.getCategorias().add(cat1);
        p14.getCategorias().add(cat1);
        p15.getCategorias().add(cat1);
        p16.getCategorias().add(cat1);
        p17.getCategorias().add(cat1);
        p18.getCategorias().add(cat1);
        p19.getCategorias().add(cat1);
        p20.getCategorias().add(cat1);
        p21.getCategorias().add(cat1);
        p22.getCategorias().add(cat1);
        p23.getCategorias().add(cat1);
        p24.getCategorias().add(cat1);
        p25.getCategorias().add(cat1);
        p26.getCategorias().add(cat1);
        p27.getCategorias().add(cat1);
        p28.getCategorias().add(cat1);
        p29.getCategorias().add(cat1);
        p30.getCategorias().add(cat1);
        p31.getCategorias().add(cat1);
        p32.getCategorias().add(cat1);
        p33.getCategorias().add(cat1);
        p34.getCategorias().add(cat1);
        p35.getCategorias().add(cat1);
        p36.getCategorias().add(cat1);
        p37.getCategorias().add(cat1);
        p38.getCategorias().add(cat1);
        p39.getCategorias().add(cat1);
        p40.getCategorias().add(cat1);
        p41.getCategorias().add(cat1);
        p42.getCategorias().add(cat1);
        p43.getCategorias().add(cat1);
        p44.getCategorias().add(cat1);
        p45.getCategorias().add(cat1);
        p46.getCategorias().add(cat1);
        p47.getCategorias().add(cat1);
        p48.getCategorias().add(cat1);
        p49.getCategorias().add(cat1);
        p50.getCategorias().add(cat1);

        produto1.getCategorias().add(cat1);
        produto2.getCategorias().addAll(Arrays.asList(cat1, categoria2, categoria4));
        produto3.getCategorias().addAll(Arrays.asList(cat1, categoria4));
        produto4.getCategorias().add(categoria2);
        produto5.getCategorias().add(categoria3);
        produto6.getCategorias().add(categoria3);
        produto7.getCategorias().add(categoria4);
        produto8.getCategorias().add(categoria5);
        produto9.getCategorias().add(categoria6);
        produto10.getCategorias().add(categoria6);
        produto11.getCategorias().add(categoria7);

        this.categoriaRepository.saveAll(Arrays.asList(cat1, categoria2, categoria3, categoria4, categoria5, categoria6, categoria7));
        this.produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3, produto4, produto5, produto6, produto7, produto8, produto9, produto10, produto11));
        this.produtoRepository.saveAll(Arrays.asList(p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, p22, p23, p24, p25, p26, p27, p28, p29, p30, p31, p32, p34, p35, p36, p37, p38, p39, p40, p41, p42, p43, p44, p45, p46, p47, p48, p49, p50));

        Estado estado1 = new Estado(null, "Minas Gerais");
        Estado estado2 = new Estado(null, "São Paulo");

        Cidade cidade1 = new Cidade(null, "Uberlândia", estado1);
        Cidade cidade2 = new Cidade(null, "São Paulo", estado2);
        Cidade cidade3 = new Cidade(null, "Campinas", estado2);

        estado1.getCidades().add(cidade1);
        estado2.getCidades().addAll(Arrays.asList(cidade2, cidade3));

        this.estadoRepository.saveAll(Arrays.asList(estado1, estado2));
        this.cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2, cidade3));

        Cliente cliente1 = new Cliente(null, "Maria Silva", "ceppantoja@gmail.com", "36378912377", TipoCliente.PESSOAFISICA, this.bCryptPasswordEncoder.encode("123"));
        cliente1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));

        Cliente cliente2 = new Cliente(null, "Ana Costa", "carlopina@gmail.com", "44206363005", TipoCliente.PESSOAFISICA, this.bCryptPasswordEncoder.encode("123"));
        cliente2.getTelefones().addAll(Arrays.asList("27363323", "93838393"));
        cliente2.addPerfil(Perfil.ADMIN);

        Endereco endereco1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cliente1, cidade1);
        Endereco endereco2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cliente1, cidade2);
        Endereco endereco3 = new Endereco(null, "Avenida Floriano", "2106", "Sala 800", "Centro", "38777012", cliente2, cidade2);

        cliente1.getEnderecos().addAll(Arrays.asList(endereco1, endereco2));
        cliente2.getEnderecos().add(endereco3);

        this.clienteRepository.saveAll(Arrays.asList(cliente1, cliente2));
        this.enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Pedido pedido1 = new Pedido(null, simpleDateFormat.parse("30/10/2018 00:13"), cliente1, endereco1);
        Pedido pedido2 = new Pedido(null, simpleDateFormat.parse("30/09/2018 00:13"), cliente1, endereco2);

        Pagamento pagamento1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, pedido1, 6);
        pedido1.setPagamento(pagamento1);

        Pagamento pagamento2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, pedido2, simpleDateFormat.parse("20/10/2018 00:00"), null);
        pedido2.setPagamento(pagamento2);

        cliente1.getPedidos().addAll(Arrays.asList(pedido1, pedido2));

        this.pedidoRepository.saveAll(Arrays.asList(pedido1, pedido2));
        this.pagamentoRepository.saveAll(Arrays.asList(pagamento1, pagamento2));

        ItemPedido itemPedido1 = new ItemPedido(pedido1, produto1, 0.0, 1, 2000.0);
        ItemPedido itemPedido2 = new ItemPedido(pedido1, produto3, 0.0, 2, 80.0);
        ItemPedido itemPedido3 = new ItemPedido(pedido2, produto2, 100.0, 1, 800.0);

        pedido1.getItens().addAll(Arrays.asList(itemPedido1, itemPedido2));
        pedido2.getItens().add(itemPedido3);

        produto1.getItens().add(itemPedido1);
        produto3.getItens().add(itemPedido2);
        produto2.getItens().add(itemPedido3);

        this.itemPedidoRepository.saveAll(Arrays.asList(itemPedido1, itemPedido2, itemPedido3));
    }
}

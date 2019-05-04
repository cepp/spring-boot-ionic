package br.com.ceppantoja.cursomc.service;

import br.com.ceppantoja.cursomc.domain.Categoria;
import br.com.ceppantoja.cursomc.domain.Cliente;
import br.com.ceppantoja.cursomc.domain.ItemPedido;
import br.com.ceppantoja.cursomc.domain.PagamentoComBoleto;
import br.com.ceppantoja.cursomc.domain.Pedido;
import br.com.ceppantoja.cursomc.domain.enums.EstadoPagamento;
import br.com.ceppantoja.cursomc.repositories.ClienteRepository;
import br.com.ceppantoja.cursomc.repositories.ItemPedidoRepository;
import br.com.ceppantoja.cursomc.repositories.PagamentoRepository;
import br.com.ceppantoja.cursomc.repositories.PedidoRepository;
import br.com.ceppantoja.cursomc.repositories.ProdutoRepository;
import br.com.ceppantoja.cursomc.security.UserSS;
import br.com.ceppantoja.cursomc.service.exception.AuthorizationException;
import br.com.ceppantoja.cursomc.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repo;
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private PagamentoRepository pagamentoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private BoletoService boletoService;
    @Autowired
    private EmailService emailService;

    public Pedido find(Integer id) {
        Optional<Pedido> obj = this.repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                String.format("Objeto não encontrado! Id: %d, Tipo: %s", id, Pedido.class.getName())));
    }

    public List<Pedido> findAll() {
        return this.repo.findAll();
    }

    @Transactional
    public Pedido insert(Pedido obj) {
        obj.setId(null);

        obj.setInstante(new Date());

        obj.setCliente(this.clienteRepository.getOne(obj.getCliente().getId()));
        obj.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);

        if(obj.getPagamento() instanceof PagamentoComBoleto) {
            PagamentoComBoleto pagamentoComBoleto = (PagamentoComBoleto) obj.getPagamento();
            this.boletoService.preencherDataPagamentoComBoleto(pagamentoComBoleto, obj.getInstante());
        }

        final Pedido novoObj = this.repo.save(obj);
        this.pagamentoRepository.save(novoObj.getPagamento());

        novoObj.getItens().forEach(itemPedido -> this.setItemPedido(itemPedido, novoObj));

        this.itemPedidoRepository.saveAll(novoObj.getItens());

        this.emailService.sendOrderConfirmationHtmlEmail(obj);

        return novoObj;
    }

    private void setItemPedido(ItemPedido itemPedido, Pedido obj) {
        itemPedido.setDesconto(0.0);
        itemPedido.setProduto(this.produtoRepository.findById(itemPedido.getProduto().getId()).get());
        itemPedido.setPreco(itemPedido.getProduto().getPreco());
        itemPedido.setPedido(obj);
    }

    public Page<Pedido> findByPage(Integer page, Integer linesPerPage, String orderBy, String direction) throws ObjectNotFoundException {
        UserSS user = UserService.authenticated();

        if(user == null) {
            throw new AuthorizationException("Acesso negado");
        }

        Cliente cliente = this.clienteRepository.findById(user.getId()).orElse(null);

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        return this.repo.findByCliente(cliente, pageRequest);
    }
}

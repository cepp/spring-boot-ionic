package br.com.ceppantoja.cursomc.service;

import br.com.ceppantoja.cursomc.domain.ItemPedido;
import br.com.ceppantoja.cursomc.domain.PagamentoComBoleto;
import br.com.ceppantoja.cursomc.domain.Pedido;
import br.com.ceppantoja.cursomc.domain.enums.EstadoPagamento;
import br.com.ceppantoja.cursomc.repositories.ItemPedidoRepository;
import br.com.ceppantoja.cursomc.repositories.PagamentoRepository;
import br.com.ceppantoja.cursomc.repositories.PedidoRepository;
import br.com.ceppantoja.cursomc.repositories.ProdutoRepository;
import br.com.ceppantoja.cursomc.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
    private BoletoService boletoService;

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

        return novoObj;
    }

    private void setItemPedido(ItemPedido itemPedido, Pedido obj) {
        itemPedido.setDesconto(0.0);
        itemPedido.setPreco(this.produtoRepository.findById(itemPedido.getProduto().getId()).get().getPreco());
        itemPedido.setPedido(obj);
    }
}

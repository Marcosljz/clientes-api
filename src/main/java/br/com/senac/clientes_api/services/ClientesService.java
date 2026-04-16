package br.com.senac.clientes_api.services;

import br.com.senac.clientes_api.dtos.ClientesRequestDto;
import br.com.senac.clientes_api.entidades.Clientes;
import br.com.senac.clientes_api.repositorios.ClientesRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientesService {

    private ClientesRepositorio clientesRepositorio;

    public ClientesService(ClientesRepositorio clientesRepositorio) {
        this.clientesRepositorio = clientesRepositorio;
    }

    public List<Clientes> listar() {
        return clientesRepositorio.findAll();
    }

    public Clientes criar(ClientesRequestDto cliente) {

        Clientes clientesPersist =
                this.clientesRequestDtoParaClientes(cliente);

        return clientesRepositorio.save(clientesPersist);

    }

    private Clientes clientesRequestDtoParaClientes(
            ClientesRequestDto entrada){

        Clientes saida= new Clientes();
        saida.setNome(entrada.getNome());
        saida.setDocumento(entrada.getDocumento());
        saida.setEmail(entrada.getEmail());
        saida.setIdade(entrada.getIdade());

        return saida;
    }
}
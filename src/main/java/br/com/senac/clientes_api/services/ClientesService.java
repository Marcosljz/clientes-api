package br.com.senac.clientes_api.services;

import br.com.senac.clientes_api.dtos.ClienteFiltroDto;
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

    public List<Clientes> listar(ClienteFiltroDto filtro) {
        if (filtro.getNome() != null){
            return clientesRepositorio.findByNomeContaining(filtro.getNome());
        }

        if (filtro.getEmail() != null){
            return clientesRepositorio.findByEmail(filtro.getEmail());
        }

        if (filtro.getIdade() > 0){
            return clientesRepositorio.findByIdadeGreaterThan(filtro.getIdade());
        }

        return clientesRepositorio.findAll();
    }

    public Clientes criar(ClientesRequestDto cliente) {
        Clientes clientePersist =
                this.clientesResquestDtoParaClientes(cliente);

        return clientesRepositorio.save(clientePersist);
    }

    public Clientes atualizar(
            Long id,
            ClientesRequestDto cliente) {
        if(clientesRepositorio.existsById(id)) {
            Clientes clientePersist =
                    this.clientesResquestDtoParaClientes(cliente);
            clientePersist.setId(id);

            return clientesRepositorio.save(clientePersist);
        }

        throw new RuntimeException("Cliente não encontrado");
    }

    public void deletar(Long id) {
        if(clientesRepositorio.existsById(id)) {
            clientesRepositorio.deleteById(id);
        }

        throw new RuntimeException("Cliente não encontrado");
    }

    private Clientes clientesResquestDtoParaClientes(
            ClientesRequestDto entrada) {
        Clientes saida = new Clientes();
        saida.setNome(entrada.getNome());
        saida.setDocumento(entrada.getDocumento());
        saida.setIdade(entrada.getIdade());
        saida.setEmail(entrada.getEmail());

        return saida;
    }

    public Clientes ListarById (Long id) {
        if (clientesRepositorio.existsById(id)) {
            return clientesRepositorio.findById(id).get();
        }

        throw new RuntimeException("Cliente não existe");
    }
}
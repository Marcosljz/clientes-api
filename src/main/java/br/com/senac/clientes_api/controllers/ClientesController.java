package br.com.senac.clientes_api.controllers;

import br.com.senac.clientes_api.dtos.ClientesRequestDto;
import br.com.senac.clientes_api.entidades.Clientes;
import br.com.senac.clientes_api.repositorios.ClientesRepositorio;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.image.RescaleOp;
import java.util.List;

@Controller
@RequestMapping("/clientes")
public class ClientesController {
    private ClientesRepositorio clientesRepositorio;

    public ClientesController(ClientesRepositorio clientesRepositorio) {
        this.clientesRepositorio = clientesRepositorio;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Clientes>> listar() {
        return ResponseEntity
                .ok(clientesRepositorio.findAll());
    }

    @PostMapping("/criar")
    public ResponseEntity<Clientes> criar(
            @RequestBody ClientesRequestDto cliente) {
        Clientes clientePersist = new Clientes();
        clientePersist.setNome(cliente.getNome());
        clientePersist.setEmail(cliente.getEmail());
        clientePersist.setDocumento(cliente.getDocumento());
        clientePersist.setIdade(cliente.getIdade());

        Clientes retorno = clientesRepositorio
                .save(clientePersist);

        return ResponseEntity.ok(retorno);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Clientes> atualizar(
            @RequestBody ClientesRequestDto cliente,
            @PathVariable Long id) {
        if(clientesRepositorio.existsById(id)) {
            Clientes clientePersist = new Clientes();
            clientePersist.setNome(cliente.getNome());
            clientePersist.setEmail(cliente.getEmail());
            clientePersist.setDocumento(cliente.getDocumento());
            clientePersist.setIdade(cliente.getIdade());
            clientePersist.setId(id);

            Clientes retorno = clientesRepositorio
                    .save(clientePersist);

            return ResponseEntity.ok(retorno);

        }

        return ResponseEntity.badRequest().body(null);

    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id) {
        if(clientesRepositorio.existsById(id)) {
            clientesRepositorio.deleteById(id);
            return ResponseEntity.ok(null);
        }

        return ResponseEntity.badRequest().body(null);
    }
}

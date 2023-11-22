package com.brasilvirtual.controllers;

import com.brasilvirtual.models.jogadores.Jogador;
import com.brasilvirtual.models.jogadores.JogadorRecord;
import com.brasilvirtual.models.jogadores.JogadorResponse;
import com.brasilvirtual.services.JogadoresService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@CrossOrigin("*")
@RequestMapping("/jogadores")
public class JogadorController {

    @Autowired
    private JogadoresService jogadoresService;

    @PostMapping
    public ResponseEntity<JogadorResponse> criar (@RequestBody @Valid JogadorRecord jogador, UriComponentsBuilder builder) {
        Jogador novoJogador = jogadoresService.criar(jogador.toJogador());
        var uri = builder.path("/jogadores/{id}").buildAndExpand(novoJogador.getId()).toUri();
        return ResponseEntity.created(uri).body(new JogadorResponse(novoJogador));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Jogador> buscarJogadorPorId(@PathVariable Long id) {
        var jogador = jogadoresService.buscarPorId(id);
        return ResponseEntity.ok().body(jogador);
    }

    @GetMapping
    public ResponseEntity findAll(@PageableDefault Pageable page){
        return ResponseEntity.ok(jogadoresService.findAll(page));
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable Long id){
        var newJogador = jogadoresService.buscarPorId(id);
        if(jogadoresService.existe(id)) return ResponseEntity.notFound().build();
        var jogadorAtualizado = jogadoresService.atualizar(newJogador);
        return ResponseEntity.ok(jogadorAtualizado);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deletar(@PathVariable Long id){
        if(jogadoresService.existe(id)) return ResponseEntity.notFound().build();
        jogadoresService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}


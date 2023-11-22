package com.brasilvirtual.services;

import com.brasilvirtual.models.jogadores.Jogador;
import com.brasilvirtual.repositories.JogadoresRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class JogadoresService {
    @Autowired
    private JogadoresRepository jogadoresRepository;
    @Autowired
    private ModelMapper mapper;

    public Jogador criar (Jogador usuario){
        return jogadoresRepository.save(usuario);
    }
    @Cacheable(value = "jogadoresCache", key = "#id")
    public Jogador buscarPorId(Long id){
        var jogadoresRepositoryById = jogadoresRepository.findById(id);
        return jogadoresRepositoryById.orElse(null);
    }
    public Page<Jogador> findAll(Pageable page){
        return jogadoresRepository.findAll(page);

    }
    @CacheEvict(value = "jogadoresCache", key = "#jogador.id")
    public Jogador atualizar (Jogador jogador){
        var jogadorR = jogadoresRepository.findById(jogador.getId());
        if(jogadorR.isPresent()){
            if(jogadorR.get().getNome() != null){
                jogadorR.get().setNome(jogador.getNome());
                mapper.map(jogador, jogadorR.get());
                return jogadoresRepository.save(jogadorR.get());
            }
        }
        throw new RuntimeException("Jogador não encontrado");
    }
    public void deletar (Long id){
        jogadoresRepository.deleteById(id);
    }
    public boolean existe (Long id){
        return !jogadoresRepository.existsById(id);
    }
}



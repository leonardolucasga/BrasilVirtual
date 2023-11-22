package com.brasilvirtual.services;

import com.brasilvirtual.models.mapas.Mapa;
import com.brasilvirtual.repositories.MapasRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MapasService {
   @Autowired
    private MapasRepository mapasRepository;

   @Autowired
   private ModelMapper mapper;

   public Mapa criar (Mapa mapas){
       return mapasRepository.save(mapas);
   }
    @Cacheable(value = "mapasCache", key = "#mapaId")
   public Optional<Mapa> buscarPorId(Long mapaId){
       return mapasRepository.findById(mapaId);
   }
    @Cacheable(value = "mapasCache", key = "'all'")
   public Page<Mapa> findAll(Pageable page){
       return mapasRepository.findAll(page);
   }
    @CacheEvict(value = "mapasCache", key = "#mapa.idItem")
   public Mapa atualizar(Mapa mapa){
       //Para cada atributo do mapa, se ele for diferente de null, ele vai atualizar o mapa;
       var mapa1 = mapasRepository.findById(mapa.getIdItem());
       if (mapa1.isPresent()){
          mapper.map(mapa, mapa1.get());
          return mapasRepository.save(mapa1.get());
       }
       throw new RuntimeException("Mapa não encontrado");
   }
    @CacheEvict(value = "mapasCache", key = "#idMapa")
   public void deletar (Long idMapa){
       mapasRepository.deleteById(idMapa);
   }
   public boolean existe (Long idMapa){
       return !mapasRepository.existsById(idMapa);
   }


}


package com.brasilvirtual.controllers;

import com.brasilvirtual.models.skins.Skin;
import com.brasilvirtual.models.skins.SkinRecord;
import com.brasilvirtual.models.skins.SkinResponse;
import com.brasilvirtual.services.SkinsService;
import jakarta.validation.Valid;
import org.flywaydb.core.api.callback.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/skins")
public class SkinController {

    @Autowired
    private SkinsService skinsService;

    @PostMapping
    public ResponseEntity<SkinResponse> criar(@RequestBody SkinRecord skin, UriComponentsBuilder uriBuilder) {
        Skin novaSkin = skinsService.criar(skin.toSkin());
        var uri = uriBuilder.path("/skins/{id}").buildAndExpand(novaSkin.getIdItem()).toUri();
        return ResponseEntity.created(uri).body(new SkinResponse(novaSkin));
    }

    @GetMapping
    public ResponseEntity findAll(@PageableDefault Pageable pageable){
        return ResponseEntity.ok(skinsService.findAll(pageable));
    }


    @GetMapping("/{skin}")
    public ResponseEntity findById(@PathVariable Long skin) {
        var skinAux = skinsService.buscarPorID(skin);
        if(skin == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok((skin));
    }
    @PutMapping("/{skin}")
    public ResponseEntity atualizar(@PathVariable Long skin) {
        if(skinsService.existe(skin)) return ResponseEntity.notFound().build();
        var skinAtualizada = skinsService.atualizar(new Skin(), 1);
        return ResponseEntity.ok(skinAtualizada);
    }
    @DeleteMapping("/{idSkin}")
    public ResponseEntity deletar(@PathVariable Long idSkin) {
        if(skinsService.existe(idSkin)) return ResponseEntity.notFound().build();
        skinsService.deletar(idSkin);
        return ResponseEntity.noContent().build();
    }
}





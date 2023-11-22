package com.brasilvirtual.models.skins;

public record SkinRecord(Long idItem, String nome, String imagem, double preco) {

    public Skin toSkin(){
        return new Skin(idItem(), nome(), imagem(), preco());
    }
}

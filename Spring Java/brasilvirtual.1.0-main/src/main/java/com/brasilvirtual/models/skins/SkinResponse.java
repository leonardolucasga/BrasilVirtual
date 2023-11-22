package com.brasilvirtual.models.skins;

public record SkinResponse (Long idItem, String nome, String imagem, double preco){
    public SkinResponse(Skin skin) {
        this(skin.getIdItem(), skin.getNome(), skin.getImagem(), skin.getPreco());
    }
}

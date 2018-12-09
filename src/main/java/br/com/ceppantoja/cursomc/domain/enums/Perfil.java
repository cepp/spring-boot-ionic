package br.com.ceppantoja.cursomc.domain.enums;

public enum Perfil {
    ADMIN(1, "ROLE_ADMIN"),
    CLIENTE(2, "ROLE_CLIENTE");

    private Integer codigo;
    private String descricao;

    Perfil(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Perfil toEnum(Integer codigo) {
        if(codigo == null) {
            return null;
        }

        for (Perfil item : Perfil.values()) {
            if (codigo.equals(item.getCodigo())) {
                return item;
            }
        }
        throw new IllegalArgumentException("Código inválido " + codigo);
    }
}
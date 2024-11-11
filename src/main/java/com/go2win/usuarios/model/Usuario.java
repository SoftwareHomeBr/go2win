package com.go2win.usuarios.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private Date dataNascimento;
    private String email;

    private String enderecoRua;
    private String endereconúmero;
    private String enderecocomplemento;
    private String enderecobairro;
    private String enderecocidade;
    private String enderecoestado;
    private String enderecoCEP;
    private String status;
    private Boolean ativo;
    private OffsetDateTime criacao;
    private Long criadoPorId;
    private OffsetDateTime atualizacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEnderecoRua() {
        return enderecoRua;
    }

    public void setEnderecoRua(String enderecoRua) {
        this.enderecoRua = enderecoRua;
    }

    public String getEndereconúmero() {
        return endereconúmero;
    }

    public void setEndereconúmero(String endereconúmero) {
        this.endereconúmero = endereconúmero;
    }

    public String getEnderecocomplemento() {
        return enderecocomplemento;
    }

    public void setEnderecocomplemento(String enderecocomplemento) {
        this.enderecocomplemento = enderecocomplemento;
    }

    public String getEnderecobairro() {
        return enderecobairro;
    }

    public void setEnderecobairro(String enderecobairro) {
        this.enderecobairro = enderecobairro;
    }

    public String getEnderecocidade() {
        return enderecocidade;
    }

    public void setEnderecocidade(String enderecocidade) {
        this.enderecocidade = enderecocidade;
    }

    public String getEnderecoestado() {
        return enderecoestado;
    }

    public void setEnderecoestado(String enderecoestado) {
        this.enderecoestado = enderecoestado;
    }

    public String getEnderecoCEP() {
        return enderecoCEP;
    }

    public void setEnderecoCEP(String enderecoCEP) {
        this.enderecoCEP = enderecoCEP;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public OffsetDateTime getCriacao() {
        return criacao;
    }

    public void setCriacao(OffsetDateTime criacao) {
        this.criacao = criacao;
    }

    public Long getCriadoPorId() {
        return criadoPorId;
    }

    public void setCriadoPorId(Long criadoPorId) {
        this.criadoPorId = criadoPorId;
    }

    public OffsetDateTime getAtualizacao() {
        return atualizacao;
    }

    public void setAtualizacao(OffsetDateTime atualizacao) {
        this.atualizacao = atualizacao;
    }
}

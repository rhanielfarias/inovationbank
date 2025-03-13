package inovationbank.inovationbank.Model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

@Column(nullable = false)
    private  String nome;

@Column(nullable = false, unique = true, length = 11)
    private  String cpf;

@Column(nullable = false, unique = true)
    private  String email;

    private  String telefone;
    private  String endereco;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCadastro = LocalDateTime.now();

    private  boolean ativo = true;

    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
    private  ContaBancaria contaBancaria;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public ContaBancaria getContaBancaria() {
        return contaBancaria;
    }

    public void setContaBancaria(ContaBancaria contaBancaria) {
        this.contaBancaria = contaBancaria;
    }
}

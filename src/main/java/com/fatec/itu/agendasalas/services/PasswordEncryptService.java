package com.fatec.itu.agendasalas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.exceptions.SenhaForaDoPadraoException;
import com.fatec.itu.agendasalas.interfaces.SenhaCriptografavel;

@Service
public class PasswordEncryptService implements SenhaCriptografavel{

    @Autowired
    protected PasswordEncoder cryptPasswordEncoder;

    @Override
    public String criptografarSenha(String senha) {
       return cryptPasswordEncoder.encode(senha);
    }

    public boolean matches(String senha, String senhaCriptografada) {
        return cryptPasswordEncoder.matches(senha, senhaCriptografada);
    }

    public void validarSenha (String senha){
        
        if (senha==null || senha.length()<8){
            throw new SenhaForaDoPadraoException("A senha deve ter no mínimo 8 caracteres");
        }

        boolean temMaiuscula = senha.matches(".*[A-Z].*");
        boolean temMinuscula = senha.matches(".*[a-z].*");
        boolean temNumero = senha.matches(".*[0-9].*");
        boolean temCaracterEspecial = senha.matches(".*[^a-zA-Z0-9].*");

        if (!temMaiuscula){
            throw new SenhaForaDoPadraoException("A senha deve conter ao menos uma letra maiúscula");
        }
        if (!temMinuscula){
            throw new SenhaForaDoPadraoException("A senha deve conter ao menos uma letra minúscula");
        }
        if (!temNumero){
            throw new SenhaForaDoPadraoException("A senha deve conter ao menos um número");
        }
        if (!temCaracterEspecial){
            throw new SenhaForaDoPadraoException("A senha deve conter ao menos um caractere especial");
        }
    }
}

package com.fatec.itu.agendasalas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fatec.itu.agendasalas.interfaces.SenhaCriptografavel;

public class PasswordEncryptService implements SenhaCriptografavel{

    @Autowired
    protected PasswordEncoder cryptPasswordEncoder;

    @Override
    public String criptografarSenha(String senha) {
       return cryptPasswordEncoder.encode(senha);
    }

}

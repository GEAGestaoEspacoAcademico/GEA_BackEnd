package com.fatec.itu.agendasalas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.interfaces.SenhaCriptografavel;

@Service
public class PasswordEncryptService implements SenhaCriptografavel{

    @Autowired
    protected PasswordEncoder cryptPasswordEncoder;

    @Override
    public String criptografarSenha(String senha) {
       return cryptPasswordEncoder.encode(senha);
    }

}

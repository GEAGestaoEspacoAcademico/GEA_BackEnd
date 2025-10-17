// package com.fatec.itu.agendasalas.entity;

// import java.io.Serializable;

// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.Table;

// @Entity
// @Table(name = "Notificacao")
// public class Notificacao implements Serializable {
    
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private int idNotificacao;

//     private Agendamento agendamento;
//     private String titulo;
//     private String mensagem;
//     private String dataEnvio;
//     private Usuario usuarioRemetente;
//     private Usuario[] destinatario;
//     private String canalEnvio;

//     public Notificacao(int idNotificacao, Agendamento agendamento, String titulo, String mensagem, String dataEnvio, 
//     Usuario usuarioRemetente, Usuario[] destinatario, String canalEnvio) {
        
//         this.idNotificacao = idNotificacao;
//         this.agendamento = agendamento;
//         this.titulo = titulo;
//         this.mensagem = mensagem;
//         this.dataEnvio = dataEnvio;
//         this.usuarioRemetente = usuarioRemetente;
//         this.destinatario = destinatario;
//         this.canalEnvio = canalEnvio;

//     }

//     @Override
//     public int hashCode() {
//         final int prime = 31;
//         int result = 1;
//         result = prime * result + idNotificacao;
//         return result;
//     }

//     @Override
//     public boolean equals(Object obj) {
//         if (this == obj)
//             return true;
//         if (obj == null)
//             return false;
//         if (getClass() != obj.getClass())
//             return false;
//         Notificacao other = (Notificacao) obj;
//         if (idNotificacao != other.idNotificacao)
//             return false;
//         return true;
//     }
// }

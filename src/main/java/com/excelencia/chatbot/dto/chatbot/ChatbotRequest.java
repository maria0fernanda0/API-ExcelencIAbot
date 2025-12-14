package com.excelencia.chatbot.dto.chatbot;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

public class ChatbotRequest {

    @NotBlank(message = "A mensagem não pode ser vazia")
    private String mensagem;

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;

    }
}


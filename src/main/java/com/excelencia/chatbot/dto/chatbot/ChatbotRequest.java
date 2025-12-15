package com.excelencia.chatbot.dto.chatbot;

import jakarta.validation.constraints.NotBlank;

public class ChatbotRequest {

    @NotBlank(message = "A mensagem não pode ser vazia")
    private String pergunta;

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;

    }
}


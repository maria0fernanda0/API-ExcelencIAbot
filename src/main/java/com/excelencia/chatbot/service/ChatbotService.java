package com.excelencia.chatbot.service;

import com.excelencia.chatbot.dto.chatbot.ChatbotRequest;
import com.excelencia.chatbot.dto.chatbot.ChatbotResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ChatbotService {

    @Value("${excelenciaBot.gem.api-key}")
    private String apiKey;


    private String chamarGem(String mensagem, String apiKey) {
        // Implementação da chamada real ao gem
        return "Resposta do gem para: \"" + mensagem + "\"";
    }

    /**
     * Processa a mensagem recebida do usuário.
     * Aqui você vai integrar com o seu gem real do ExcelencIA.
     */
    public ChatbotResponse processarMensagem(ChatbotRequest request) {
        // Simulação de resposta (substituir pelo seu gem)
        String respostaGerada = chamarGem(request.getMensagem(), apiKey);

        // Monta o DTO de resposta
        ChatbotResponse response = new ChatbotResponse();
        response.setResposta(respostaGerada);
        return response;
    }

    /**
     * Método simulado para gerar respostas automáticas.
     * Depois substitua pela chamada ao gem real.
     */
    private String gerarRespostaSimulada(String mensagem) {
        // Lógica simples de simulação
        if (mensagem == null || mensagem.isEmpty()) {
            return "Por favor, envie uma mensagem válida!";
        }
        return "Resposta do Chatbot para: \"" + mensagem + "\"";
    }
}

package com.excelencia.chatbot.controller;

import com.excelencia.chatbot.dto.chatbot.ChatbotRequest;
import com.excelencia.chatbot.dto.chatbot.ChatbotResponse;
import com.excelencia.chatbot.service.ChatbotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/chatbot")

public class ChatbotController {

    private final ChatbotService chatbotService;

    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    /**
     * Endpoint principal para enviar uma pergunta ao chatbot.
     * Recebe uma mensagem em JSON e retorna a resposta gerada pelo gem.
     */
    @PostMapping("/pergunta")
    public ResponseEntity<ChatbotResponse> enviarPergunta(@Valid @RequestBody ChatbotRequest request) {
        ChatbotResponse resposta = chatbotService.processarMensagem(request);
        return ResponseEntity.ok(resposta);
    }

    /**
     * Endpoint de teste rápido para verificar se a API está online.
     */
    @GetMapping("/status")
    public ResponseEntity<String> status() {
        return ResponseEntity.ok("Chatbot API está online!");
    }
}

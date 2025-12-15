package com.excelencia.chatbot.service;

import com.excelencia.chatbot.dto.chatbot.ChatbotRequest;
import com.excelencia.chatbot.dto.chatbot.ChatbotResponse;
import com.excelencia.chatbot.dto.gemini.GeminiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class ChatbotService {

    @Value("${excelenciaBot.gem.api-key}")
    private String apiKey;

    private final WebClient webClient;

    public ChatbotService(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("https://generativelanguage.googleapis.com/v1beta")
                .build();
    }

    public ChatbotResponse processarPergunta(ChatbotRequest request) {

        String respostaGemini = chamarGemini(request.getPergunta());

        ChatbotResponse response = new ChatbotResponse();
        response.setResposta(respostaGemini);
        return response;
    }

    private String chamarGemini(String pergunta) {

        Map<String, Object> body = Map.of(
                "contents", new Object[]{
                        Map.of(
                                "parts", new Object[]{
                                        Map.of("text",
                                                "Você é um assistente especializado em entrevistas de emprego. "
                                                        + "Responda de forma clara, profissional e acolhedora.\n\nPergunta: "
                                                        + pergunta)
                                }
                        )
                }
        );

        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/models/gemini-2.5-flash:generateContent")
                        .queryParam("key", apiKey)
                        .build())
                .bodyValue(body)
                .retrieve()
                .onStatus(status -> status.isError(), clientResponse -> {
                    // Trata códigos de erro (4xx, 5xx)
                    return clientResponse.bodyToMono(String.class)
                            .map(errorBody -> new RuntimeException(
                                    "Erro da API Gemini. Código: " + clientResponse.statusCode() + " | Corpo: " + errorBody));
                })
                // 2. DESERIALIZAÇÃO USANDO DTO
                .bodyToMono(GeminiResponse.class)
                // 3. EXTRAÇÃO DE TEXTO USANDO GETTERS
                .map(response -> {
                    try {
                        // Navega com segurança usando os métodos dos DTOs
                        return response.getCandidates().get(0).getContent().getParts().get(0).getText();
                    } catch (IndexOutOfBoundsException | NoSuchElementException e) {
                        throw new RuntimeException("Resposta da IA incompleta ou vazia.", e);
                    }
                })
                // 4. BLOQUEIO E TRATAMENTO DE EXCEÇÃO FINAL
                .blockOptional()
                .orElse("Não foi possível obter uma resposta válida do assistente (Verifique logs para detalhes).");

    }


        }

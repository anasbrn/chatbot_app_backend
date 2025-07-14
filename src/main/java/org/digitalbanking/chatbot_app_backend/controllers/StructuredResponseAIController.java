package org.digitalbanking.chatbot_app_backend.controllers;

import org.digitalbanking.chatbot_app_backend.outputs.ListMovies;
import org.digitalbanking.chatbot_app_backend.outputs.Movie;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StructuredResponseAIController {
    private final ChatClient chatClient;

    public StructuredResponseAIController(ChatClient.Builder builder, MessageWindowChatMemory memory) {
        this.chatClient = builder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(memory).build())
                .build();
    }

    @GetMapping("structuredResponse/chat")
    public ListMovies chat(String text) {
        String systemMessage = "You are a cinema specialist";
        return chatClient.prompt()
                .system(systemMessage)
                .user(text)
                .call()
                .entity(ListMovies.class);
    }
}

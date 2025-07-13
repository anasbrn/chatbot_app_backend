package org.digitalbanking.chatbot_app_backend.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenerateTextWithAIController {
    private final ChatClient chatClient;

    public GenerateTextWithAIController(ChatClient.Builder builder, MessageWindowChatMemory memory) {
        this.chatClient = builder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(memory).build())
                .build();
    }

    @GetMapping("generateText/chat")
    public String chat(String text) {
        return chatClient.prompt()
                .system("Answer with capital letters")
                .user(text)
                .call()
                .content();
    }
}

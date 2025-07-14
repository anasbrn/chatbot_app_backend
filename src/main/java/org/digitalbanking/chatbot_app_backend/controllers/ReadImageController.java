package org.digitalbanking.chatbot_app_backend.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ReadImageController {
    private final ChatClient chatClient;

    public ReadImageController(ChatClient.Builder builder, MessageWindowChatMemory memory) {
        this.chatClient = builder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(memory).build())
                .build();
    }

    @PostMapping(value = "image/describe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String chat(String query, MultipartFile file) throws IOException {
        String systemMessage = "Describe this image";
        byte[] imgBytes = file.getBytes();
        return chatClient.prompt()
                .system(systemMessage)
                .user(u ->
                        u.text(query).media(MediaType.IMAGE_PNG, new ByteArrayResource(imgBytes))
                )
                .call()
                .content();
    }
}

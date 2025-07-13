package org.digitalbanking.chatbot_app_backend.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FewShotPromtingAIController {
    private final ChatClient chatClient;

    public FewShotPromtingAIController(ChatClient.Builder builder, MessageWindowChatMemory memory) {
        this.chatClient = builder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(memory).build())
                .build();
    }

    @GetMapping("generateText/chat")
    public String chat(String text) {
        List<Message> messages = List.of(
                new UserMessage("I'm a software engineer"),
                new AssistantMessage("That's great"),
                new UserMessage("I have a black car"),
                new AssistantMessage("Nice, good color!"),
                new UserMessage("My hobby is traveling"),
                new AssistantMessage("It's a good hobby")
        );

        return chatClient.prompt()
                .system("Answer with capital letters")
                .messages(messages)
                .user(text)
                .call()
                .content();
    }
}

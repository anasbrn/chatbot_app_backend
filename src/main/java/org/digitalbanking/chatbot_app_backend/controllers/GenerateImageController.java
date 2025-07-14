package org.digitalbanking.chatbot_app_backend.controllers;

import org.springframework.ai.image.ImageOptions;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenerateImageController {
    private final OpenAiImageModel openAiImageModel;

    public GenerateImageController(OpenAiImageModel openAiImageModel) {
        this.openAiImageModel = openAiImageModel;
    }

    @GetMapping("generateImage")
    public String generateImage(String prompt) {
        ImageOptions imageOptions = OpenAiImageOptions.builder()
                .quality("hd")
                .build();
        ImagePrompt imagePrompt = new ImagePrompt(prompt, imageOptions);
        return openAiImageModel.call(imagePrompt).getResult().getOutput().getUrl();
    }
}

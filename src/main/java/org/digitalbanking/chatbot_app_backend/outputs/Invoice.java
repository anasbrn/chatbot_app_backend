package org.digitalbanking.chatbot_app_backend.outputs;

public record Invoice(
        String numero,
        String date,
        String total,
        String description
) {
}

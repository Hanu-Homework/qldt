package edu.hanu.qldt.dto.response;

public class MessageResponseDTO {

    private String text;

    public MessageResponseDTO() {

    }

    public MessageResponseDTO(String text) {
        this.text = text;

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

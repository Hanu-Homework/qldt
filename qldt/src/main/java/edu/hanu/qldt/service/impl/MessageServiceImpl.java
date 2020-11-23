package edu.hanu.qldt.service.impl;

import edu.hanu.qldt.dto.response.MessageResponseDTO;
import edu.hanu.qldt.model.Message;
import edu.hanu.qldt.repository.MessageRepository;
import edu.hanu.qldt.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class contains all related function implementations to the message.
 */
@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /**
     * Returns a List of Message.
     *
     * @return messages from database.
     */
    @Override
    public List<Message> findAll() {
        return messageRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Message::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Returns a Message object by id, if course exist
     * or returns a null value.
     *
     * @param id Id of the message.
     * @return a message object by id.
     * @see Message
     */
    @Override
    public Message findById(Long id) {
        return messageRepository.getOne(id);
    }

    /**
     * Creates a new message and save into the database.
     *
     * @param messageResponseDTO Submitted DTO from web application.
     * @return a new Message object.
     * @see Message
     */
    @Override
    public Message create(MessageResponseDTO messageResponseDTO) {
        return messageRepository.save(new Message(messageResponseDTO.getText()));
    }

    /**
     * Updates a message from database by id.
     *
     * @param id Id of the message.
     * @param messageResponseDTO Submitted DTO from web application.
     * @return an updated message.
     * @see Message
     */
    @Override
    public Message update(Long id, MessageResponseDTO messageResponseDTO) {
        Message message = messageRepository.getOne(id);
        message.setText(messageResponseDTO.getText());
        return messageRepository.save(message);
    }

    /**
     * Deletes a message from database by id.
     *
     * @param id Id of the message.
     */
    @Override
    public void delete(Long id) {
        Message message = messageRepository.getOne(id);
        messageRepository.delete(message);
    }
}

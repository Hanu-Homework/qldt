package edu.hanu.qldt.service;

import edu.hanu.qldt.dto.response.MessageResponseDTO;
import edu.hanu.qldt.model.Message;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This interface contains all related function definitions to the message.
 */
@Service
public interface MessageService {

    /**
     * Returns a List of Message.
     *
     * @return messages from database.
     */
    List<Message> findAll();

    /**
     * Returns a Message object by id, if course exist
     * or returns a null value.
     *
     * @param id Id of the message.
     * @return a message object by id.
     * @see Message
     */
    Message findById(Long id);

    /**
     * Creates a new message and save into the database.
     *
     * @param messageResponseDTO Submitted DTO from web application.
     * @return  a new Message object.
     * @see Message
     */
    Message create(MessageResponseDTO messageResponseDTO);

    /**
     * Updates a message from database by id.
     *
     * @param id Id of the message.
     * @param messageResponseDTO Submitted DTO from web application.
     * @return an updated message.
     * @see Message
     */
    Message update(Long id, MessageResponseDTO messageResponseDTO);

    /**
     * Deletes a message from database by id.
     *
     * @param id Id of the message.
     */
    void delete(Long id);
}

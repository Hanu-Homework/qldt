package edu.hanu.qldt.service.impl;

import edu.hanu.qldt.dto.response.RemarkResponseDTO;
import edu.hanu.qldt.model.Remark;
import edu.hanu.qldt.model.user.group.Student;
import edu.hanu.qldt.repository.RemarkRepository;
import edu.hanu.qldt.repository.user.StudentRepository;
import edu.hanu.qldt.service.RemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class contains all related function implementations to the remark.
 */
@Service
public class RemarkServiceImpl implements RemarkService {

    private final RemarkRepository remarkRepository;

    private final StudentRepository studentRepository;

    @Autowired
    public RemarkServiceImpl(RemarkRepository remarkRepository, StudentRepository studentRepository) {
        this.remarkRepository = remarkRepository;
        this.studentRepository = studentRepository;
    }

    /**
     * Returns a List of Remark by student id.
     *
     * @param student_id
     * @return Remarks from database by student id.
     */
    @Override
    public List<Remark> findAllByStudent(Long student_id) {
        return remarkRepository.findAll()
                .stream()
                .filter(remark -> remark.getStudent().getId().equals(student_id))
                .collect(Collectors.toList());
    }

    /**
     * Returns a Remark object by id, if course exist
     * or returns a null value.
     *
     * @param id Id of the Remark.
     * @return a Remark object by id.
     * @see Remark
     */
    @Override
    public Remark findById(Long id) {
        return remarkRepository.getOne(id);
    }

    /**
     * Creates a new remark and save into the database.
     *
     * @param remarkResponseDTO Submitted DTO from web application.
     * @return a new Remark object.
     * @see Remark
     */
    @Override
    public Remark create(RemarkResponseDTO remarkResponseDTO) {
        /* Finds student by id. */
        Student student = studentRepository.getOne(remarkResponseDTO.getStudentId());
        return remarkRepository.save(new Remark(
                remarkResponseDTO.getText(),
                student));
    }

    /**
     * Updates a remark from database by id.
     *
     * @param id Id of the remark.
     * @param remarkResponseDTO Submitted DTO from web application.
     * @return an updated remark.
     * @see Remark
     */
    @Override
    public Remark update(Long id, RemarkResponseDTO remarkResponseDTO) {
        Remark remark = remarkRepository.getOne(id);
        remark.setText(remarkResponseDTO.getText());
        return remarkRepository.save(remark);
    }

    /**
     * Deletes a remark from database by id.
     *
     * @param id Id of the remark.
     */
    @Override
    public void delete(Long id) {
        Remark remark = remarkRepository.getOne(id);
        remarkRepository.delete(remark);
    }
}

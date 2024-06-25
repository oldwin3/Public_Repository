package ru.itis.orisjavaproject.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.orisjavaproject.Entities.BookEntitys.Subject;
import ru.itis.orisjavaproject.Repositories.SubjectRepository;

import java.util.Optional;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public void saveSubject(String subjectName) {
        Optional<Subject> existingSubject = subjectRepository.findByName(subjectName);
        if (!existingSubject.isPresent()) {
            Subject subject = new Subject();
            subject.setName(subjectName);
            subjectRepository.save(subject);
        }
    }
}
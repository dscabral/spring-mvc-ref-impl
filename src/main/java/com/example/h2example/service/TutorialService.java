package com.example.h2example.service;

import com.example.h2example.cache.IGenericCache;
import com.example.h2example.cache.impl.GenericCache;
import com.example.h2example.model.Tutorial;
import com.example.h2example.repository.TutorialRepository;
import exception.BusinessException;
import exception.TutorialNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TutorialService {

    private final TutorialRepository tutorialRepository;
    private final GenericCache<Long, Tutorial> cache;

    public TutorialService(TutorialRepository tutorialRepository,
                           GenericCache<Long, Tutorial> cache) {
        this.tutorialRepository = tutorialRepository;
        this.cache = cache;
    }

    public List<Tutorial> getAllTutorials(String title) {
        List<Tutorial> tutorials = new ArrayList<>();
        if (title == null) {
            tutorialRepository.findAll().forEach(tutorials::add);
        } else {
            tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);
        }
        return tutorials;
    }

    public Tutorial getTutorialById(long id) {
        return this.cache.get(id).orElseGet(() ->
                this.getTutorialByIdFromRepository(id));
    }

    public Tutorial getTutorialByIdFromRepository(Long id) {
        Tutorial tutorial = this.tutorialRepository.findById(id).orElseThrow(() -> new TutorialNotFoundException("non existing tutorial"));
        this.cache.put(tutorial.getId(), tutorial);
        return tutorial;
    }

    @Transactional
    public Tutorial createTutorial(Tutorial tutorial) {
        return Optional.ofNullable(tutorialRepository.save(tutorial)).orElseThrow(
                () -> new BusinessException("Error creating tutorial")
        );
    }

    public Tutorial updateTutorial(long id, Tutorial tutorial) {
        Optional<Tutorial> data = Optional.ofNullable(tutorialRepository.findById(id)).orElseThrow(
                () -> new EntityNotFoundException("Non existing tutorial")
        );
        Tutorial toUpdate = Tutorial.builder()
                .id(id)
                .title(tutorial.getTitle())
                .description(tutorial.getDescription())
                .published(tutorial.isPublished())
                .build();
        return tutorialRepository.save(toUpdate);
    }

    public void deleteTutorial(long id) {
        tutorialRepository.deleteById(id);
    }

    public void deleteAllTutorials() {
        tutorialRepository.deleteAll();
    }

    public List<Tutorial> findByPublished() {
        return tutorialRepository.findByPublished(true);
    }
}

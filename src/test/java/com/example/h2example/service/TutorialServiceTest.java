package com.example.h2example.service;

import com.example.h2example.model.Tutorial;
import com.example.h2example.repository.TutorialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class TutorialServiceTest {

    @Mock
    private TutorialRepository tutorialRepository;

    @InjectMocks
    private TutorialService tutorialService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTutorial() {
        Tutorial tutorial = Tutorial.builder()
                .description("Java")
                .title("A Java tutorial")
                .published(false)
                .build();
        when(tutorialRepository.save(tutorial)).thenReturn(tutorial);
        assertEquals(tutorial, tutorialService.createTutorial(tutorial));
    }
}
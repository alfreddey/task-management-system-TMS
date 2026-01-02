package org.example.services;

import org.example.interfaces.ReportCalculator;
import org.example.interfaces.repositories.ProjectRepository;
import org.example.interfaces.views.ProjectView;
import org.example.models.*;
import org.example.utils.exceptions.ProjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

    private ProjectRepository<Project> mockRepo;
    private ProjectView<Project> mockView;
    private ReportCalculator mockCalculator;
    private ProjectService<Project> service;

    @BeforeEach
    void setUp() {
        mockRepo = mock(ProjectRepository.class);
        mockView = mock(ProjectView.class);
        mockCalculator = mock(ReportCalculator.class);
        service = new ProjectService<>(mockRepo, mockView, mockCalculator);
    }

    @Test
    void testAddNewProject() {
        Project project = new SoftwareProject("Proj1", "Desc", 3, 1000, null);
        service.addNewProject(project);
        verify(mockRepo, times(1)).add(project);
    }

    @Test
    void testViewProjects() {
        service.viewProjects();
        verify(mockView, times(1)).viewProjects(mockRepo);
    }

    @Test
    void testViewProject() throws ProjectNotFoundException {
        Project project = new SoftwareProject("Proj2", "Desc", 2, 500, null);
        when(mockRepo.getById("P001")).thenReturn(project);
        when(mockCalculator.calculateTaskProgressionRate(project.getTasks())).thenReturn(75.0);

        service.viewProject("P001");

        verify(mockRepo, times(1)).getById("P001");
        verify(mockView, times(1)).viewProject(project);
        verify(mockCalculator, times(1)).calculateTaskProgressionRate(project.getTasks());
    }

    @Test
    void testViewProjectThrowsExceptionIfNotFound() throws ProjectNotFoundException {
        when(mockRepo.getById("NON_EXISTENT")).thenThrow(new ProjectNotFoundException("Not found"));

        assertThrows(ProjectNotFoundException.class, () -> service.viewProject("NON_EXISTENT"));
    }

    @Test
    void testViewByType() {
        ProjectType type = ProjectType.SOFTWARE;
        service.viewByType(type);

        ArgumentCaptor<java.util.function.Predicate<Project>> predicateCaptor =
                ArgumentCaptor.forClass(java.util.function.Predicate.class);

        verify(mockView, times(1)).viewBy(predicateCaptor.capture(), eq(mockRepo));

        // test predicate logic
        Project proj = new SoftwareProject("Test", "Desc", 1, 100, null);
        assertTrue(predicateCaptor.getValue().test(proj));
    }

    @Test
    void testViewByBudgetRange() {
        double min = 100;
        double max = 500;
        service.viewByBudgetRange(min, max);

        ArgumentCaptor<java.util.function.Predicate<Project>> predicateCaptor =
                ArgumentCaptor.forClass(java.util.function.Predicate.class);

        verify(mockView, times(1)).viewBy(predicateCaptor.capture(), eq(mockRepo));

        Project p1 = new SoftwareProject("P1", "Desc", 1, 150, null);
        Project p2 = new SoftwareProject("P2", "Desc", 1, 50, null);

        assertTrue(predicateCaptor.getValue().test(p1));
        assertFalse(predicateCaptor.getValue().test(p2));
    }

    @Test
    void testGetProjectRepository() {
        assertEquals(mockRepo, service.getProjectRepository());
    }
}

package org.example.interfaces.repositories;

import org.example.models.*;
import org.example.repositories.ListBasedTaskRepository;
import org.example.repositories.MapBasedProjectRepository;
import org.example.utils.exceptions.ProjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class ProjectRepositoryTest {

    private ProjectRepository<Project> repo;
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        // Use a dummy TaskRepository since Project needs it
        taskRepository = new ListBasedTaskRepository();

        repo = new MapBasedProjectRepository<>();
    }

    @Test
    void testAddAndSize() {
        Project project = new SoftwareProject("Project1", "Desc1", 5, 1000, taskRepository);
        repo.add(project);
        assertEquals(1, repo.size());
    }

    @Test
    void testGetById() throws ProjectNotFoundException {
        Project project = new SoftwareProject("Project2", "Desc2", 3, 500, taskRepository);
        repo.add(project);

        Project retrieved = repo.getById(project.getId());
        assertEquals(project.getName(), retrieved.getName());
        assertEquals(project.getId(), retrieved.getId());
    }

    @Test
    void testGetWithPredicate() throws ProjectNotFoundException {
        Project project = new SoftwareProject("Project3", "Desc3", 2, 200, taskRepository);
        repo.add(project);

        Project retrieved = repo.get(p -> p.getName().equals("Project3"));
        assertEquals(project.getId(), retrieved.getId());
    }

    @Test
    void testGetThrowsExceptionIfNotFound() {
        assertThrows(ProjectNotFoundException.class, () -> repo.getById("NON_EXISTENT"));
        assertThrows(ProjectNotFoundException.class, () -> repo.get(p -> p.getName().equals("NON_EXISTENT")));
    }

    @Test
    void testFilterReturnsMatchingProjects() throws ProjectNotFoundException {
        Project p1 = new SoftwareProject("A", "Desc", 2, 100, taskRepository);
        Project p2 = new SoftwareProject("B", "Desc", 3, 200, taskRepository);
        repo.add(p1);
        repo.add(p2);

        ProjectRepository<Project> filtered = repo.filter(p -> p.getTeamSize() >= 3);
        assertEquals(1, filtered.size());
        assertEquals("B", filtered.getById(p2.getId()).getName());
    }

    @Test
    void testRemoveProject() throws ProjectNotFoundException {
        Project project = new SoftwareProject("RemoveMe", "Desc", 1, 50, taskRepository);
        repo.add(project);

        Project removed = repo.remove(p -> p.getName().equals("RemoveMe"));
        assertEquals(project.getId(), removed.getId());
        assertEquals(0, repo.size());
    }

    @Test
    void testForEachIteration() {
        Project p1 = new SoftwareProject("Iter1", "Desc", 1, 100, taskRepository);
        Project p2 = new SoftwareProject("Iter2", "Desc", 2, 200, taskRepository);
        repo.add(p1);
        repo.add(p2);

        AtomicBoolean found = new AtomicBoolean(false);
        repo.forEach(p -> {
            if (p.getName().equals("Iter1")) found.set(true);
        });

        assertTrue(found.get());
    }
}

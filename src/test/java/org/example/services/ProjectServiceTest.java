package org.example.services;

import org.example.models.Project;
import org.example.models.SoftwareProject;
import org.example.utils.exceptions.ProjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectServiceTest {
    ProjectService projectService;

    @BeforeEach
    @Test
    void setUp() {
        projectService = ProjectService.getService();
    }

    @Test
    void testAddProject() {
        Project softwareProject = new SoftwareProject("IR Software", "sdesc", 12, 43);


        assertDoesNotThrow(() -> {
            projectService.addProject(softwareProject);
            projectService.getProjectById(softwareProject.getId());
        });
    }

    @Test
    void testGetProjectById() {
        Project softwareProject = new SoftwareProject("Projectssdf", "sdesc", 102, 43);


        assertDoesNotThrow(() -> {
            projectService.addProject(softwareProject);
            projectService.getProjectById(softwareProject.getId());
        });
    }

    @Test
    void testAddSoftwareProject() {
        assertDoesNotThrow(() -> {
            projectService.addSoftwareProject("Projectssdf", "sdesc", 102, 43);
            projectService.getProjectById("P001");
        });
    }

    @Test
    void testAddHardwareProject() {
        assertDoesNotThrow(() -> {
            projectService.addHardwareProject("Projectssdf", "sdesc", 102, 43, 2342.3);
            projectService.getProjectById("P001");
        });
    }
}
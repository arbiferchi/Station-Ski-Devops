package tn.esprit.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.repositories.ICourseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CourseServicesImplTest {

    @InjectMocks
    private CourseServicesImpl courseServices;

    @Mock
    private ICourseRepository courseRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void retrieveAllCourses() {
        // Arrange
        List<Course> courses = new ArrayList<>();
        courses.add(new Course()); // Add mock Course objects as needed
        when(courseRepository.findAll()).thenReturn(courses);

        // Act
        List<Course> result = courseServices.retrieveAllCourses();

        // Assert
        assertEquals(1, result.size());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    void addCourse() {
        // Arrange
        Course course = new Course();
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        // Act
        Course result = courseServices.addCourse(course);

        // Assert
        assertNotNull(result);
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void updateCourse() {
        // Arrange
        Course course = new Course();
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        // Act
        Course result = courseServices.updateCourse(course);

        // Assert
        assertNotNull(result);
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void retrieveCourse() {
        // Arrange
        Long courseId = 1L;
        Course course = new Course();
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        // Act
        Course result = courseServices.retrieveCourse(courseId);

        // Assert
        assertNotNull(result);
        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    void retrieveCourse_notFound() {
        // Arrange
        Long courseId = 1L;
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // Act
        Course result = courseServices.retrieveCourse(courseId);

        // Assert
        assertNull(result);
        verify(courseRepository, times(1)).findById(courseId);
    }
}

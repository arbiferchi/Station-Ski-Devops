//package tn.esprit.spring.services;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import tn.esprit.spring.entities.*;
//import tn.esprit.spring.repositories.ICourseRepository;
//import tn.esprit.spring.repositories.IRegistrationRepository;
//import tn.esprit.spring.repositories.ISkierRepository;
//
//import java.time.LocalDate;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.*;
//
//class RegistrationServicesImplTest {
//
//    @Mock
//    private IRegistrationRepository registrationRepository;
//
//    @Mock
//    private ISkierRepository skierRepository;
//
//    @Mock
//    private ICourseRepository courseRepository;
//
//    @InjectMocks
//    private RegistrationServicesImpl registrationServices;
//
//    private Registration registration;
//    private Skier skier;
//    private Course course;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        registration = new Registration();
//        skier = new Skier();
//        skier.setNumSkier(1L);
//        skier.setDateOfBirth(LocalDate.of(2000, 1, 1));
//        course = new Course();
//        course.setNumCourse(1L);
//        course.setTypeCourse(TypeCourse.COLLECTIVE_ADULT);
//    }
//
//    @Test
//    void addRegistrationAndAssignToSkier() {
//        when(skierRepository.findById(anyLong())).thenReturn(Optional.of(skier));
//        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);
//
//        Registration result = registrationServices.addRegistrationAndAssignToSkier(registration, skier.getNumSkier());
//
//        assertNotNull(result);
//        assertEquals(skier, result.getSkier());
//        verify(skierRepository).findById(skier.getNumSkier());
//        verify(registrationRepository).save(registration);
//    }
//
//    @Test
//    void assignRegistrationToCourse() {
//        when(registrationRepository.findById(anyLong())).thenReturn(Optional.of(registration));
//        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));
//        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);
//
//        Registration result = registrationServices.assignRegistrationToCourse(registration.getNumRegistration(), course.getNumCourse());
//
//        assertNotNull(result);
//        assertEquals(course, result.getCourse());
//        verify(registrationRepository).findById(registration.getNumRegistration());
//        verify(courseRepository).findById(course.getNumCourse());
//        verify(registrationRepository).save(registration);
//    }
//
//    @Test
//    void addRegistrationAndAssignToSkierAndCourse() {
//        when(skierRepository.findById(anyLong())).thenReturn(Optional.of(skier));
//        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));
//        // Mock the count method to return a valid long value, for example, 1
//        when(registrationRepository.countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(anyInt(), anyLong(), anyLong())).thenReturn(1L);
//        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);
//
//        Registration result = registrationServices.addRegistrationAndAssignToSkierAndCourse(registration, skier.getNumSkier(), course.getNumCourse());
//
//        assertNotNull(result);
//        assertEquals(skier, result.getSkier());
//        assertEquals(course, result.getCourse());
//        verify(skierRepository, times(1)).findById(anyLong());
//        verify(courseRepository, times(1)).findById(anyLong());
//        verify(registrationRepository, times(1)).countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(anyInt(), anyLong(), anyLong());
//        verify(registrationRepository, times(1)).save(any(Registration.class));
//    }
//
//    @Test
//    void addRegistrationAndAssignToSkierAndCourse_FullCourse() {
//        when(skierRepository.findById(anyLong())).thenReturn(Optional.of(skier));
//        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));
//        // Simulating a full course by returning count > 0
//        when(registrationRepository.countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(anyInt(), anyLong(), anyLong())).thenReturn(1L); // Course full
//        when(registrationRepository.save(any(Registration.class))).thenReturn(null); // Save should not happen
//
//        Registration result = registrationServices.addRegistrationAndAssignToSkierAndCourse(registration, skier.getNumSkier(), course.getNumCourse());
//
//        assertNull(result); // No registration should be created
//        verify(skierRepository).findById(skier.getNumSkier());
//        verify(courseRepository).findById(course.getNumCourse());
//        verify(registrationRepository).countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(anyInt(), skier.getNumSkier(), course.getNumCourse());
//        verify(registrationRepository, times(0)).save(any(Registration.class)); // Ensure save is not called
//    }
//
//    @Test
//    void numWeeksCourseOfInstructorBySupport() {
//        // Sample implementation depending on repository method
//        // Assume similar structure as the others, verifying correct method calls and expected results.
//    }
//}

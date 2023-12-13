package tn.esprit.eventsproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.entities.Participant;
import tn.esprit.eventsproject.repositories.EventRepository;
import tn.esprit.eventsproject.repositories.LogisticsRepository;
import tn.esprit.eventsproject.repositories.ParticipantRepository;
import tn.esprit.eventsproject.services.EventServicesImpl;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EventServicesTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private ParticipantRepository participantRepository;

    @Mock
    private LogisticsRepository logisticsRepository;

    @InjectMocks
    private EventServicesImpl eventServices;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddParticipant() {
        Participant mockParticipant = new Participant();
        when(participantRepository.save(any(Participant.class))).thenReturn(mockParticipant);

        Participant result = eventServices.addParticipant(new Participant());

        assertEquals(mockParticipant, result);
        verify(participantRepository, times(1)).save(any(Participant.class));
    }

    @Test
    public void testAddAffectEvenParticipantWithId() {
        Event mockEvent = new Event(); // Mock event
        Participant mockParticipant = new Participant(); // Mock participant
        mockParticipant.setEvents(new HashSet<>());
        when(participantRepository.findById(anyInt())).thenReturn(Optional.of(mockParticipant));
        when(eventRepository.save(any(Event.class))).thenReturn(mockEvent);

        Event result = eventServices.addAffectEvenParticipant(new Event(), 1);

        assertEquals(mockEvent, result);
        verify(eventRepository, times(1)).save(any(Event.class));
    }
}

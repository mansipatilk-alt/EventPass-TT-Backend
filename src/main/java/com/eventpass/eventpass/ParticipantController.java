
package com.eventpass.eventpass;

import com.eventpass.eventpass.entity.Participant;
import com.eventpass.eventpass.entity.Ticket;
import com.eventpass.eventpass.repository.ParticipantRepository;
import com.eventpass.eventpass.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ParticipantController {

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @PostMapping("/register")
    public Participant registerParticipant(@RequestBody Participant participant) {

        Participant savedParticipant = participantRepository.save(participant);

        Ticket ticket = new Ticket();
        ticket.setTicketId(
                "EP-" + UUID.randomUUID().toString()
                        .substring(0, 8).toUpperCase());
        ticket.setParticipantName(savedParticipant.getName());
        ticket.setEventName(savedParticipant.getEventName());
        ticket.setUsed(false);

        ticketRepository.save(ticket);

        savedParticipant.setTicketId(ticket.getTicketId());

        return savedParticipant;
    }
}

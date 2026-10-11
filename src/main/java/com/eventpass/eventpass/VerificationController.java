
package com.eventpass.eventpass;

import com.eventpass.eventpass.entity.Ticket;
import com.eventpass.eventpass.repository.TicketRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class VerificationController {

    private final TicketRepository ticketRepository;

    public VerificationController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @GetMapping("/verify")
    public ResponseEntity<Map<String, Object>> verifyTicket(
            @RequestParam String ticketId) {

        Map<String, Object> response = new LinkedHashMap<>();

        Optional<Ticket> ticketOptional =
                ticketRepository.findByTicketId(ticketId);

        if (ticketOptional.isEmpty()) {
            response.put("valid", false);
            response.put("status", "INVALID TICKET");
            response.put("message", "Entry denied. Ticket not found.");
            return ResponseEntity.ok(response);
        }

        Ticket ticket = ticketOptional.get();

        if (ticket.isUsed()) {
            response.put("valid", false);
            response.put("status", "ALREADY USED");
            response.put("message", "Entry denied. This ticket has already been used.");
            response.put("ticketId", ticket.getTicketId());
            response.put("participantName", ticket.getParticipantName());
            response.put("eventName", ticket.getEventName());
            return ResponseEntity.ok(response);
        }

        ticket.setUsed(true);
        ticketRepository.save(ticket);

        response.put("valid", true);
        response.put("status", "CHECKED IN");
        response.put("message", "Valid ticket. Entry allowed.");
        response.put("ticketId", ticket.getTicketId());
        response.put("participantName", ticket.getParticipantName());
        response.put("eventName", ticket.getEventName());

        return ResponseEntity.ok(response);
    }
}

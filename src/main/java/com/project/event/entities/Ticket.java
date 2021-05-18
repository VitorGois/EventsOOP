package com.project.event.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "ticket")
@Entity
public class Ticket implements Serializable {

    enum TicketType {
        FREE('0'), PAID('1');

        private char value;

        TicketType(char value) {
            this.value = value;
        }

        public char getValue() {
            return this.value;
        }
    }

    private static final long serialVersionUID = 1L;

    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NonNull private TicketType type;
    @NonNull private Instant date;
    @NonNull private Double price;

    @ManyToOne
    @JoinColumn(name = "attendee_id")
    private Attendee attendee;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

}
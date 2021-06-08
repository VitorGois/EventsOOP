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

    private static final long serialVersionUID = 1L;

    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NonNull
    @Enumerated(EnumType.STRING)
    private TicketType type;

    @NonNull
    private Instant date;

    @NonNull
    private Double price;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "attendee_id")
    private Attendee attendee;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

}

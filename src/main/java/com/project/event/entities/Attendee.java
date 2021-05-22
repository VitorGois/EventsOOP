package com.project.event.entities;

import com.project.event.dtos.attendee.AttendeeInsertDTO;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "attendee")
@PrimaryKeyJoinColumn(name = "USER_ID")
public class Attendee extends BaseUser {

    @NonNull
    private Double balance;

    @OneToMany(mappedBy = "attendee")
    @NonNull
    private List<Ticket> tickets = new ArrayList<>();

    public Attendee(String name, String email, Double balance) {
        super(name, email);
        this.balance = balance;
    }

    public Attendee(AttendeeInsertDTO newAttendee) {
        super(newAttendee.getName(), newAttendee.getEmail());
        this.balance = newAttendee.getBalance();
    }
}

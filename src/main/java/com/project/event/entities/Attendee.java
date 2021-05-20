package com.project.event.entities;

import lombok.*;

import javax.persistence.*;

import com.project.event.dtos.attendee.AttendeeInsertDTO;

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

    @NonNull private Double balance;

    @OneToMany(mappedBy = "attendee")
    @NonNull private List<Ticket> tickets = new ArrayList<>();

    public Attendee(@NonNull String name, @NonNull String email, Double balance) {
        super(name, email);
        this.balance = balance;
    }
    public Attendee(AttendeeInsertDTO newAttendee){
        super(newAttendee.getName(),newAttendee.getEmail());
        this.balance = newAttendee.getBalance();
    }
}

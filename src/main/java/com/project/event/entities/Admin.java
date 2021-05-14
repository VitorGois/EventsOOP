package com.project.event.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "admin")
@Entity
@PrimaryKeyJoinColumn(name = "user_id")
public class Admin extends BaseUser {

    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    @NonNull private String phoneNumber;

    @OneToMany(mappedBy = "admin")
    @NonNull @Setter(AccessLevel.NONE) private List<Event> events = new ArrayList<>();

    public Admin(@NonNull String name, @NonNull String email, @NonNull String phoneNumber) {
        super(name, email);
        this.phoneNumber = phoneNumber;
    }

    public void addEvent(Event event) {
        this.events.add(event);
    }

}

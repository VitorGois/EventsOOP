package com.project.event.entities;

import com.project.event.dtos.admin.AdminInsertDto;
import com.project.event.dtos.admin.AdminUpdateDto;
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

    @NonNull private String phoneNumber;

    @OneToMany(mappedBy = "admin")
    @NonNull @Setter(AccessLevel.NONE) private List<Event> events = new ArrayList<>();

    public Admin(@NonNull String name, @NonNull String email, @NonNull String phoneNumber) {
        super(name, email);
        this.phoneNumber = phoneNumber;
    }

    public Admin(AdminInsertDto adminInsertDto) {
        super(adminInsertDto.getName(), adminInsertDto.getEmail());
        this.phoneNumber = adminInsertDto.getPhoneNumber();
    }

    public void addEvent(Event event) {
        this.events.add(event);
    }

}

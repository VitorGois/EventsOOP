package com.project.event.entities;

import lombok.*;

import javax.persistence.*;

import com.project.event.dtos.place.PlaceInsertDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "place")
@Entity
public class Place implements Serializable {

    private static final long serialVersionUID = 1L;

    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NonNull private String name;
    @NonNull private String address;

    @ManyToMany(mappedBy = "places")
    @Setter(AccessLevel.NONE) private List<Event> events = new ArrayList<>();

    public void addEvent(Event event) {
        this.events.add(event);
    }

    public Place(PlaceInsertDTO newPlace){
        this.name=newPlace.getName();
        this.address = newPlace.getAddress();
    }

}

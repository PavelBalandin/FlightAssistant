package flightassistant.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table
@ToString(of = {"id", "price", "numberOfSeats", "pilots", "plane", "startPoint", "endPoint"})
@EqualsAndHashCode(of = {"id"})
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long price;
    private Long numberOfSeats;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;

    @OneToOne
    @JoinColumn(name = "palne_id", referencedColumnName = "id")
    private Plane plane;

    @ManyToMany
    @JoinTable(name = "flight_pilot",
            joinColumns = {@JoinColumn(name = "flight_id")},
            inverseJoinColumns = {@JoinColumn(name = "pilot_id")})
    private Set<Pilot> pilots = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "startingPoint_id", referencedColumnName = "id")
    private City startingPoint;

    @OneToOne
    @JoinColumn(name = "finishPoint_id", referencedColumnName = "id")
    private City finishPoint;

    @OneToMany(mappedBy = "flight", orphanRemoval = true)
    private List<MyOrder> orders;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Long numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public Set<Pilot> getPilots() {
        return pilots;
    }

    public void setPilots(Set<Pilot> pilots) {
        this.pilots = pilots;
    }

    public City getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(City startingPoint) {
        this.startingPoint = startingPoint;
    }

    public City getFinishPoint() {
        return finishPoint;
    }

    public void setFinishPoint(City finishPoint) {
        this.finishPoint = finishPoint;
    }

}

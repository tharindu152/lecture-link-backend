package lk.ac.iit.lecturer.entity;

import jakarta.persistence.*;
import lk.ac.iit.lecturer.enums.Language;
import lk.ac.iit.lecturer.enums.LecturerStatus;
import lk.ac.iit.lecturer.enums.TimePreference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "lecturers", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Getter
@Setter
@NoArgsConstructor
public class Lecturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String division;

    @Column(length = 1500)
    private String mapsLocation;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(length = 15)
    private String contactNo;

    private Integer currentRating;
    private Integer ratingsReceived;
    private BigDecimal hourlyPayRate;
    private String preference;

    @Enumerated(EnumType.STRING)
    private LecturerStatus status = LecturerStatus.ACTIVE;

    private Boolean isAssigned = false;

    @Enumerated(EnumType.STRING)
    private Language language;

    private Integer lecturingExperience;
    private String fieldOfWork;

    @Enumerated(EnumType.STRING)
    private TimePreference timePreference;

    private boolean subscribed;
    private Long instituteId;

    @Column(length = 400)
    private String pictureUrl;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdOn;

    @UpdateTimestamp
    private LocalDateTime updatedOn;
}

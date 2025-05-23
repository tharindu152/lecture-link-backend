package lk.ac.iit.lecture_link.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "lecturer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lecturer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 255)
    private String name;
    @Column(nullable = false, length = 500)
    private String division;
    @Column(length = 1500)
    private String mapsLocation;
    @Column(nullable = false, length = 255)
    private String email;
    @Column(nullable = false, length = 255)
    private String password;
    @Column(length = 15, name = "contact_no")
    private String contactNo;
    @Column()
    @Min(0)
    @Max(5)
    private Integer currentRating;
    @Column()
    private Integer ratingsReceived;
    @Column()
    private BigDecimal hourlyPayRate;
    @Column(length = 255)
    private String preference;
    @Column(columnDefinition = "ENUM('ACTIVE','INACTIVE')")
    private String status;
    @Column(name = "is_assigned")
    private Boolean isAssigned;
    @Column(columnDefinition = "ENUM('ENGLISH','SINHALA','TAMIL')")
    private String language;
    @Column()
    private Integer lecturingExperience;
    @Column(length = 255)
    private String fieldOfWork;
    @Column(columnDefinition = "ENUM('WEEKDAY','WEEKEND','FLEXIBLE')")
    private String timePreference;
    @Column()
    private boolean subscribed;
    @CreationTimestamp
    @Column(name = "created_on", updatable = false)
    private LocalDateTime createdOn;
    @UpdateTimestamp
    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "lecturer")
    private List<Subject> subjects;

    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "lecturer", cascade = CascadeType.ALL)
    private List<Qualification> qualifications;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Setter(AccessLevel.NONE)
    @OneToOne(mappedBy = "lecturer", cascade = CascadeType.ALL)
    private Picture picture;

    public void setPicture(Picture profilePic) {
        if (profilePic != null) profilePic.setLecturer(this);
        this.picture = profilePic;
    }
}

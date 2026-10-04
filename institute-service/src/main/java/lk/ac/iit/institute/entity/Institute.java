package lk.ac.iit.institute.entity;

import jakarta.persistence.*;
import lk.ac.iit.institute.enums.InstituteStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "institutes", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Getter
@Setter
@NoArgsConstructor
public class Institute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 500)
    private String division;

    @Column(length = 1500)
    private String mapsLocation;

    @Column(length = 15)
    private String telephone;

    @Column(length = 1000)
    private String description;

    private Integer currentRating;
    private Integer ratingsReceived;
    private boolean subscribed;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InstituteStatus status = InstituteStatus.ACTIVE;

    @Column(length = 400)
    private String logoUrl;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdOn;

    @UpdateTimestamp
    private LocalDateTime updatedOn;
}

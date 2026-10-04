package lk.ac.iit.lecturer.entity;

import jakarta.persistence.*;
import lk.ac.iit.lecturer.enums.QualificationLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "qualifications")
@Getter
@Setter
@NoArgsConstructor
public class Qualification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String awardingBody;

    @Column(nullable = false)
    private Integer durationInDays;

    private String discipline;

    @Column(nullable = false)
    private LocalDate completedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QualificationLevel level;

    @Column(nullable = false)
    private Long lecturerId;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdOn;

    @UpdateTimestamp
    private LocalDateTime updatedOn;
}

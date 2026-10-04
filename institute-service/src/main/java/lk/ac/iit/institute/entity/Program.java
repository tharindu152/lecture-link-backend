package lk.ac.iit.institute.entity;

import jakarta.persistence.*;
import lk.ac.iit.institute.enums.Language;
import lk.ac.iit.institute.enums.ProgramLevel;
import lk.ac.iit.institute.enums.TimePreference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "programs")
@Getter
@Setter
@NoArgsConstructor
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProgramLevel level;

    private Integer durationInDays;
    private Integer studentCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Language language;

    private String batchId;
    private BigDecimal hourlyPayRate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TimePreference timePreference;

    @Column(nullable = false)
    private Long instituteId;

    @ElementCollection
    @CollectionTable(name = "program_subject_ids", joinColumns = @JoinColumn(name = "program_id"))
    @Column(name = "subject_id", nullable = false)
    private Set<Long> subjectIds = new LinkedHashSet<>();

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdOn;

    @UpdateTimestamp
    private LocalDateTime updatedOn;
}

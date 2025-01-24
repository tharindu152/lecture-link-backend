package lk.ac.iit.lecture_link.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "program")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Program implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false, length = 255)
  private String name;
  @Column(length = 1000)
  private String description;
  @Column(nullable = false, columnDefinition = "ENUM('MSC','BSC','HND','PGD')")
  private String level;
  @Column(name = "duration_in_days")
  private Integer durationInDays;
  @Column(name = "student_count")
  private Integer studentCount;
  @Column(name = "batch_id")
  private String batchId;
  @Column(nullable = false)
  private BigDecimal payment;
  @CreationTimestamp
  @Column(name = "created_on", updatable = false)
  private LocalDateTime createdOn;
  @UpdateTimestamp
  @Column(name = "updated_on")
  private LocalDateTime updatedOn;
  @ManyToOne
  @JoinColumn(name = "institute_id", referencedColumnName = "id", nullable = false, unique = true)
  private Institute institute;
  @ManyToMany
  @JoinTable(
      name = "program_subject",
      joinColumns = @JoinColumn(name = "program_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "subject_id", referencedColumnName = "id")
  )
  private Set<Subject> subjects;
}
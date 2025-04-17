package lk.ac.iit.lecture_link.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "subject")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subject implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false, length = 255)
  private String name;
  @Column(nullable = false, name = "no_of_credits")
  @Min(0)
  @Max(4)
  private Integer noOfCredits;
  @Column(length = 1000)
  private String description;
  @Column(nullable = false, name = "is_assigned")
  private Boolean isAssigned;
  @CreationTimestamp
  @Column(name = "created_on", updatable = false)
  private LocalDateTime createdOn;
  @UpdateTimestamp
  @Column(name = "updated_on")
  private LocalDateTime updatedOn;

  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @ManyToMany(mappedBy = "subjects")
  @JsonIgnore
  private Set<Program> programs;

  @ManyToOne
  @JsonIgnore
  @JoinColumn(name = "lecturer_id", referencedColumnName = "id")
  private Lecturer lecturer;
}

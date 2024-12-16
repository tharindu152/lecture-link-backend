package lk.ac.iit.lecture_link.entity;

import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
  @ManyToMany(mappedBy = "subjects")
  private Set<Program> programs;
  @ManyToOne
  @JoinColumn(name = "lecturer_id", referencedColumnName = "id")
  private Lecturer lecturer;
}


package lk.ac.iit.lecture_link.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "qualification")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Qualification implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false, length = 255)
  private String name;
  @Column(nullable = false, length = 255, name = "awarding_body")
  private String awardingBody;
  @Column(nullable = false, length = 50, name = "duration_in_days" )
  private Integer durationInDays;
  @Column(length = 255)
  private String discipline;
  @Column(nullable = false, name = "completed_at")
  private Date completedAt;
  @Column(nullable = false, columnDefinition = "ENUM('MSC','BSC','HND','PGD')")
  private String level;
  @CreationTimestamp
  @Column(name = "created_on", updatable = false)
  private LocalDateTime createdOn;
  @UpdateTimestamp
  @Column(name = "updated_on")
  private LocalDateTime updatedOn;

  @ManyToOne
  @JsonIgnore
  @JoinColumn(name = "lecturer_id", referencedColumnName = "id", nullable = false)
  private Lecturer lecturer;
}


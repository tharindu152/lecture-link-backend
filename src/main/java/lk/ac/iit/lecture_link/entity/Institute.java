package lk.ac.iit.lecture_link.entity;

import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "institute")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Institute implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false, length = 255)
  private String name;
  @Column(nullable = false, length = 255)
  private String password;
  @Column(nullable = false, length = 255)
  private String email;
  @Column(length = 15)
  private String telephone;
  @Column(name = "ugc_reg_no", length = 100)
  private String ugcRegNo;
  @Column(length = 1000)
  private String description;
  @Column(length = 500)
  private String review;
  @Column(nullable = false)
  private boolean subscribed;
  @Column(length = 1000)
  private String logo;
  @Column(nullable = false, columnDefinition = "ENUM('ACTIVE','INACTIVE')")
  private String status;
  @CreationTimestamp
  @Column(name = "created_on", updatable = false)
  private LocalDateTime createdOn;
  @UpdateTimestamp
  @Column(name = "updated_on")
  private LocalDateTime updatedOn;
  @OneToMany(mappedBy = "institute")
  private List<Program> programs;
}
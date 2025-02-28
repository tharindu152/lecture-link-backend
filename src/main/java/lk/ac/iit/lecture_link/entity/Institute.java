package lk.ac.iit.lecture_link.entity;

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
  @Column(nullable = false, length = 500)
  private String district;
  @Column(length = 15)
  private String telephone;
  @Column(name = "ugc_reg_no", length = 100)
  private String ugcRegNo;
  @Column(length = 1000)
  private String description;
  @Column()
  @Min(0)
  @Max(5)
  private Integer rating;
  @Column()
  private boolean subscribed;
  @Column(nullable = false, columnDefinition = "ENUM('ACTIVE','INACTIVE')")
  private String status;
  @CreationTimestamp
  @Column(name = "created_on", updatable = false)
  private LocalDateTime createdOn;
  @UpdateTimestamp
  @Column(name = "updated_on")
  private LocalDateTime updatedOn;

  @Setter(AccessLevel.NONE)
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @OneToMany(mappedBy = "institute", cascade = CascadeType.ALL)
  private Set<Program> programs;

  @Setter(AccessLevel.NONE)
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @OneToOne(mappedBy = "institute", cascade = CascadeType.ALL)
  private Logo logo;

  public void setLogo(Logo institueLogo) {
    if (institueLogo != null) institueLogo.setInstitute(this);
    this.logo = institueLogo;
  }
}
package lk.ac.iit.lecture_link.entity;

import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    private String address;
    @Column(nullable = false, length = 255)
    private String email;
    @Column(nullable = false, length = 255)
    private String password;
    @Column(name = "d_o_b")
    private LocalDateTime dateOfBirth;
    @Column(length = 15, name = "contact_no")
    private String contactNo;
    @Column(length = 1000)
    private String review;
    @Column(nullable = false, length = 100, name = "pay_rate")
    private BigDecimal payRate;
    @Column(length = 255)
    private String preference;
    @Column(length = 1000)
    private String profilePic;
    @Column(nullable = false, columnDefinition = "ENUM('ACTIVE','INACTIVE')")
    private String status;
    @Column(name = "is_assigned")
    private Boolean isAssigned;
    @Column(length = 255)
    private String languages;
    @CreationTimestamp
    @Column(name = "created_on", updatable = false)
    private LocalDateTime createdOn;
    @UpdateTimestamp
    @Column(name = "updated_on")
    private LocalDateTime updatedOn;
    @OneToMany(mappedBy = "lecturer", cascade = CascadeType.ALL)
    private List<Subject> subjects;
    @OneToMany(mappedBy = "lecturer", cascade = CascadeType.ALL)
    private List<Qualification> qualifications;
}

package lk.ac.iit.lecture_link.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "logo")
public class Logo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "logo_path", length = 400)
    private String logoPath;

    @OneToOne
    @JoinColumn(name = "institute_id", referencedColumnName = "id")
    private Institute institute;

    public Logo(String picturePath) {
        this.logoPath = picturePath;
    }
}

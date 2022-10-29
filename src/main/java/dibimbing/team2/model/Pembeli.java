package dibimbing.team2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dibimbing.team2.model.oauth.User;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "pembeli")
@Where(clause = "deleted_date is null")
public class Pembeli extends AbstractDate implements Serializable {
    //GenerationType.AUTO : nextvall all tabel sequense
    // GenerationType.IDENTITY : nextvall per tabel sequense
    @Id
    @Column(name = "id_pembeli")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private User user;

    @Column(name = "alamat", columnDefinition = "TEXT")
    private String alamat;

    @Column(name = "kota", length = 50)
    private String kota;

    @Column(name = "propinsi", length = 50)
    private String propinsi;

    @Column(name = "negara", length = 50)
    private String negara;

    @Column(name = "kd_pos", length = 10)
    private String kodepos;

    @Column(name = "no_hp", length = 20)
    private String noHp;


}

package dibimbing.team2.model;

import dibimbing.team2.model.oauth.User;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "pembayaran")
@Where(clause = "deleted_date is null")
public class Pembayaran extends  AbstractDate implements Serializable {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private User user;

    @OneToOne(targetEntity = Transaksi.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_transaksi", referencedColumnName = "id")
    private Transaksi transaksi;

    @Column(name = "total_bayar")
    private Double totalBayar;

    private String noOrder;

    private String buktiTrf;

    private String status;
    //Menunggu, lunas

}


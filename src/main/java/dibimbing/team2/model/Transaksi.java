package dibimbing.team2.model;

import dibimbing.team2.model.oauth.User;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "transaksi")
@Where(clause = "deleted_date is null")
public class Transaksi extends AbstractDate implements Serializable {
    //GenerationType.AUTO : nextvall all tabel sequense
    // GenerationType.IDENTITY : nextvall per tabel sequense

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_barang")
    Barang barang;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_pembeli")
    User pembeli;

    @Column(name = "nama")
    private String nama;

    private Double harga;

    private Integer qty;

    private Double totalHarga;

    private String status;

    @Column(name = "order_report")
    private String report;

}


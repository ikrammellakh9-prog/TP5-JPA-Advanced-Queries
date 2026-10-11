package ma.enset.ikram.tp5.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "participants_tp5")
public class Participant implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email_pro", nullable = false, unique = true)
    private String emailPro;

    @Column(name = "nom_complet", nullable = false)
    private String nomComplet;

    @Column(name = "departement")
    private String departement;

    public Participant() {}
    public Participant(String emailPro, String nomComplet, String departement) {
        this.emailPro = emailPro;
        this.nomComplet = nomComplet;
        this.departement = departement;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmailPro() { return emailPro; }
    public void setEmailPro(String emailPro) { this.emailPro = emailPro; }
    public String getNomComplet() { return nomComplet; }
    public void setNomComplet(String nomComplet) { this.nomComplet = nomComplet; }
    public String getDepartement() { return departement; }
    public void setDepartement(String departement) { this.departement = departement; }
}
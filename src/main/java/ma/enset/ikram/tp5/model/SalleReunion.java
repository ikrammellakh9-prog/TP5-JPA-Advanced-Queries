package ma.enset.ikram.tp5.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "salles_reunion_tp5")
@NamedQueries({
        @NamedQuery(name = "SalleReunion.findByCapaciteMin",
                query = "SELECT s FROM SalleReunion s WHERE s.capacite >= :capMin ORDER BY s.capacite ASC"),
        @NamedQuery(name = "SalleReunion.countTotal",
                query = "SELECT COUNT(s) FROM SalleReunion s")
})
public class SalleReunion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code_salle", nullable = false, unique = true, length = 30)
    private String codeSalle;

    @Column(name = "nom_salle", nullable = false)
    private String nomSalle;

    @Column(nullable = false)
    private int capacite;

    @Column(name = "localisation_aile")
    private String localisation;

    @Column(name = "prix_heure")
    private Double prixHeure;

    @OneToMany(mappedBy = "salle", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ReservationCreneau> reservations = new HashSet<>();

    public SalleReunion() {}
    public SalleReunion(String codeSalle, String nomSalle, int capacite, String localisation, Double prixHeure) {
        this.codeSalle = codeSalle;
        this.nomSalle = nomSalle;
        this.capacite = capacite;
        this.localisation = localisation;
        this.prixHeure = prixHeure;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodeSalle() { return codeSalle; }
    public void setCodeSalle(String codeSalle) { this.codeSalle = codeSalle; }
    public String getNomSalle() { return nomSalle; }
    public void setNomSalle(String nomSalle) { this.nomSalle = nomSalle; }
    public int getCapacite() { return capacite; }
    public void setCapacite(int capacite) { this.capacite = capacite; }
    public String getLocalisation() { return localisation; }
    public void setLocalisation(String localisation) { this.localisation = localisation; }
    public Double getPrixHeure() { return prixHeure; }
    public void setPrixHeure(Double prixHeure) { this.prixHeure = prixHeure; }
    public Set<ReservationCreneau> getReservations() { return reservations; }
    public void setReservations(Set<ReservationCreneau> reservations) { this.reservations = reservations; }
}
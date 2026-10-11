package ma.enset.ikram.tp5.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations_creneau_tp5")
public class ReservationCreneau implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "intitule_reunion", nullable = false)
    private String intitule;

    @Column(name = "date_debut", nullable = false)
    private LocalDateTime dateDebut;

    @Column(name = "date_fin", nullable = false)
    private LocalDateTime dateFin;

    @Column(name = "nb_participants")
    private int nbParticipants;

    @Column(name = "statut_validation")
    private String statut = "VALIDE";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salle_id", nullable = false)
    private SalleReunion salle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organisateur_id", nullable = false)
    private Participant organisateur;

    public ReservationCreneau() {}
    public ReservationCreneau(String intitule, LocalDateTime dateDebut, LocalDateTime dateFin, int nbParticipants, SalleReunion salle, Participant organisateur) {
        this.intitule = intitule;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nbParticipants = nbParticipants;
        this.salle = salle;
        this.organisateur = organisateur;
        this.statut = "VALIDE";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getIntitule() { return intitule; }
    public void setIntitule(String intitule) { this.intitule = intitule; }
    public LocalDateTime getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDateTime dateDebut) { this.dateDebut = dateDebut; }
    public LocalDateTime getDateFin() { return dateFin; }
    public void setDateFin(LocalDateTime dateFin) { this.dateFin = dateFin; }
    public int getNbParticipants() { return nbParticipants; }
    public void setNbParticipants(int nbParticipants) { this.nbParticipants = nbParticipants; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    public SalleReunion getSalle() { return salle; }
    public void setSalle(SalleReunion salle) { this.salle = salle; }
    public Participant getOrganisateur() { return organisateur; }
    public void setOrganisateur(Participant organisateur) { this.organisateur = organisateur; }
}
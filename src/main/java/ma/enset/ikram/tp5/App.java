package ma.enset.ikram.tp5;

import ma.enset.ikram.tp5.model.Participant;
import ma.enset.ikram.tp5.model.ReservationCreneau;
import ma.enset.ikram.tp5.model.SalleReunion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println("  TP5 JPA - REQUETES AVANCEES JPQL ET CRITERIA API        ");
        System.out.println("               (IKRAM MELLAKH - ENSET)                   ");
        System.out.println("==========================================================");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ikramTP5_PU");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Participant p1 = new Participant("ikram.m@tech.ma", "Ikram Mellakh", "Ingenierie Logicielle");
            Participant p2 = new Participant("karim.a@tech.ma", "Karim Alami", "DevOps & Cloud");
            em.persist(p1);
            em.persist(p2);

            SalleReunion s1 = new SalleReunion("SR-NEWTON", "Salle Isaac Newton", 50, "Aile Nord", 120.0);
            SalleReunion s2 = new SalleReunion("SR-TURING", "Salle Alan Turing", 30, "Aile Sud", 85.0);
            SalleReunion s3 = new SalleReunion("SR-EULER", "Salle Leonhard Euler", 15, "Etage 2", 50.0);
            em.persist(s1);
            em.persist(s2);
            em.persist(s3);

            ReservationCreneau r1 = new ReservationCreneau("Design Pattern & Clean Architecture",
                    LocalDateTime.now().plusDays(1).withHour(9).withMinute(0),
                    LocalDateTime.now().plusDays(1).withHour(11).withMinute(0),
                    35, s1, p1);
            ReservationCreneau r2 = new ReservationCreneau("Session CI/CD GitHub Actions",
                    LocalDateTime.now().plusDays(2).withHour(14).withMinute(0),
                    LocalDateTime.now().plusDays(2).withHour(16).withMinute(0),
                    25, s2, p2);
            ReservationCreneau r3 = new ReservationCreneau("Brainstorming Sprint 4",
                    LocalDateTime.now().plusDays(3).withHour(10).withMinute(0),
                    LocalDateTime.now().plusDays(3).withHour(12).withMinute(0),
                    10, s3, p1);
            em.persist(r1);
            em.persist(r2);
            em.persist(r3);

            em.getTransaction().commit();
            System.out.println("-> Donnees de test inserees avec succes.");

            System.out.println("\n[1] REQUETES JPQL AGREGATION (COUNT, AVG, MAX)");
            Object[] stats = (Object[]) em.createQuery(
                    "SELECT COUNT(s), AVG(s.capacite), MAX(s.prixHeure) FROM SalleReunion s"
            ).getSingleResult();
            System.out.println("   * Nombre total de salles: " + stats[0]);
            System.out.println("   * Capacite moyenne: " + String.format("%.2f", stats[1]) + " personnes");
            System.out.println("   * Tarif horaire maximal: " + stats[2] + " DH");

            System.out.println("\n[2] JPQL AVEC GROUP BY & JOINTURE");
            List<Object[]> statsParSalle = em.createQuery(
                    "SELECT s.nomSalle, COUNT(r), AVG(r.nbParticipants) FROM ReservationCreneau r JOIN r.salle s GROUP BY s.nomSalle"
            ).getResultList();
            for (Object[] row : statsParSalle) {
                System.out.println("   * Salle: " + row[0] + " | Nb Reservations: " + row[1] + " | Participants Moy: " + row[2]);
            }

            System.out.println("\n[3] PAGINATION JPA (setFirstResult / setMaxResults)");
            List<SalleReunion> pageSalles = em.createQuery("SELECT s FROM SalleReunion s ORDER BY s.capacite DESC", SalleReunion.class)
                    .setFirstResult(0)
                    .setMaxResults(2)
                    .getResultList();
            System.out.println("   * Page 1 (2 elements max) :");
            for (SalleReunion s : pageSalles) {
                System.out.println("     - " + s.getNomSalle() + " (Capacite: " + s.getCapacite() + ")");
            }

            System.out.println("\n[4] NAMED QUERY TEST (SalleReunion.findByCapaciteMin)");
            List<SalleReunion> namedResult = em.createNamedQuery("SalleReunion.findByCapaciteMin", SalleReunion.class)
                    .setParameter("capMin", 25)
                    .getResultList();
            System.out.println("   * Salles trouvees avec capacite >= 25 : " + namedResult.size());
            for (SalleReunion s : namedResult) {
                System.out.println("     - " + s.getNomSalle() + " (" + s.getCapacite() + ")");
            }

            System.out.println("\n[5] CRITERIA API (Requete Dynamique Type-Safe)");
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<ReservationCreneau> cq = cb.createQuery(ReservationCreneau.class);
            Root<ReservationCreneau> root = cq.from(ReservationCreneau.class);

            Predicate condPart = cb.greaterThanOrEqualTo(root.get("nbParticipants"), 20);
            Predicate condStatut = cb.equal(root.get("statut"), "VALIDE");
            cq.select(root).where(cb.and(condPart, condStatut)).orderBy(cb.desc(root.get("nbParticipants")));

            List<ReservationCreneau> criteriaRes = em.createQuery(cq).getResultList();
            System.out.println("   * Reservations Criteria (participants >= 20) : " + criteriaRes.size());
            for (ReservationCreneau r : criteriaRes) {
                System.out.println("     - " + r.getIntitule() + " (" + r.getNbParticipants() + " pers.)");
            }

        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
        } finally {
            em.close();
            emf.close();
            System.out.println("\n==========================================================");
            System.out.println("  EXECUTION DU TP5 TERMINEE AVEC SUCCES (BUILD SUCCESS)");
            System.out.println("==========================================================");
        }
    }
}
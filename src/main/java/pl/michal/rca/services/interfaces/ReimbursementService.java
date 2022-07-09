package pl.michal.rca.services.interfaces;

import pl.michal.rca.models.Reimbursement;

import java.util.List;

public interface ReimbursementService {
    List<Reimbursement> findAll();

    List<Reimbursement> findAllForUser(int userId);
    Reimbursement createStartingReimbursement(String username, int id, String acceptance);

    void update(Reimbursement reimbursement);

    void updateAcceptance(int reimbursementId, String acceptance);
}

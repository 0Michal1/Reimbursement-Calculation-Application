package pl.michal.rca.services.database;

import pl.michal.rca.models.Reimbursement;
import pl.michal.rca.repositories.ReimbursementRepository;

import java.util.List;

public class ReimbursementService implements pl.michal.rca.services.interfaces.ReimbursementService {
    private ReimbursementRepository reimbursementRepository = new ReimbursementRepository();
    @Override
    public List<Reimbursement> findAll() {
        List<Reimbursement> reimbursements = reimbursementRepository.findAll();
        return reimbursementRepository.findAll();
    }

    @Override
    public List<Reimbursement> findAllForUser(int userId) {
        return reimbursementRepository.findAllUserReimbursements(userId);
    }

    @Override
    public Reimbursement createStartingReimbursement(String username, int id, String acceptance) {
        return reimbursementRepository.createStartingReimbursement(username, id, acceptance);
    }

    @Override
    public void update(Reimbursement reimbursement) {
        reimbursementRepository.update(reimbursement);
    }

    @Override
    public void updateAcceptance(int reimbursementId, String acceptance) {
        reimbursementRepository.updateAcceptance(reimbursementId,acceptance);
    }
}

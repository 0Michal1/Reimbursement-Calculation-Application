package pl.michal.rca.services.database;

import pl.michal.rca.models.Receipt;
import pl.michal.rca.repositories.ReceiptRepository;

import java.util.List;

public class ReceiptService implements pl.michal.rca.services.interfaces.ReceiptService {
    private ReceiptRepository receiptRepository = new ReceiptRepository();
    @Override
    public Receipt create(Receipt receipt) {
        return receiptRepository.create(receipt);
    }

    @Override
    public List<Receipt> findAllByReimbursementsID(int reimbursementsID) {
        return receiptRepository.findAllReimbursementReceipts(reimbursementsID);
    }
}

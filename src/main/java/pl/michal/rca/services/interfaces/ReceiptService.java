package pl.michal.rca.services.interfaces;

import pl.michal.rca.models.Receipt;

import java.util.List;

public interface ReceiptService {
    Receipt create(Receipt receipt);

    List<Receipt> findAllByReimbursementsID(int reimbursementsID);
}

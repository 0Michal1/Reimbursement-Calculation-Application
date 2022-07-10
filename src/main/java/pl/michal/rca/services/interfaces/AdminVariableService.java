package pl.michal.rca.services.interfaces;

import pl.michal.rca.models.AdminVariable;

import java.util.List;

public interface AdminVariableService {

    List<AdminVariable> findAll();

    List<AdminVariable> findAllByTypeActive(String type);


    AdminVariable read(Integer adminVariableId);

    AdminVariable readByName(String name);

    List<AdminVariable> findAllByType(String type);

    void updateValue(int variableId, double value);

    AdminVariable create(AdminVariable receiptType);
    void updateState(int id, String state);


    String readNameById(int typeId);
}

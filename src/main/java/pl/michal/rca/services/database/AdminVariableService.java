package pl.michal.rca.services.database;

import pl.michal.rca.models.AdminVariable;
import pl.michal.rca.repositories.AdminVariableRepository;

import java.util.List;

public class AdminVariableService implements pl.michal.rca.services.interfaces.AdminVariableService {
    private AdminVariableRepository adminVariableRepository = new AdminVariableRepository();

    @Override
    public List<AdminVariable> findAll() {
        return adminVariableRepository.findAll();
    }

    @Override
    public List<AdminVariable> findAllByTypeActive(String type) {
        return adminVariableRepository.findAllTypeVariablesActive(type);
    }

    @Override
    public AdminVariable read(Integer adminVariableId) {
        return adminVariableRepository.read(adminVariableId);
    }

    @Override
    public AdminVariable readByName(String name) {
        return adminVariableRepository.readByName(name);
    }

    @Override
    public List<AdminVariable> findAllByType(String type) {
        return adminVariableRepository.findAllTypeVariables(type);
    }

    @Override
    public void updateValue(int variableId, double value) {
        adminVariableRepository.updateValue(variableId, value);
    }

    @Override
    public AdminVariable create(AdminVariable receiptType) {
        return adminVariableRepository.create(receiptType);
    }

    public void updateState(int id, String state) {
        adminVariableRepository.updateState(id, state);
    }
}

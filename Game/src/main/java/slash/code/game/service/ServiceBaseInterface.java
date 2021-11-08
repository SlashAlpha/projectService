package slash.code.game.service;

import slash.code.game.model.BaseEntity;

import java.util.List;
import java.util.UUID;

public interface ServiceBaseInterface<T extends BaseEntity, ID extends UUID> {
    T save(T t);

    List<T> findAll();

    void deleteById(ID id);

    T getOne(ID id);

}

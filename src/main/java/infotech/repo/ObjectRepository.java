package infotech.repo;

import infotech.domain.DataBaseObject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.LinkedList;

public interface ObjectRepository extends CrudRepository<DataBaseObject, Long> {

    @Query(value = "select * from objects where key=:key", nativeQuery = true)
    DataBaseObject findByKey(@Param("key") String key);

    @Query(value = "select * from objects", nativeQuery = true)
    LinkedList<DataBaseObject> getListOfObjects();

}

package infotech.repo;

import infotech.domain.DataBaseObject;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.LinkedList;

public interface ObjectRepository extends CrudRepository<DataBaseObject, Long> {

    @Query(value = "select * from objects where key=:key", nativeQuery = true)
    DataBaseObject findByKey(@Param("key") String key);

    @Query(value = "select * from objects", nativeQuery = true)
    LinkedList<DataBaseObject> getListOfObjects();

    @Query(value = "select exists (select * from objects where key=:key)", nativeQuery = true)
    boolean existsByKey(@Param("key") String key);

    @Query(value = "select creation_datetime from objects where key=:key", nativeQuery = true)
    Timestamp getTimestampByKey(@Param("key") String key);

    @Transactional
    @Modifying
    @Query(value = "delete from objects where delete_datetime<=current_timestamp", nativeQuery = true)
    void deleteAllDeadRows();

}

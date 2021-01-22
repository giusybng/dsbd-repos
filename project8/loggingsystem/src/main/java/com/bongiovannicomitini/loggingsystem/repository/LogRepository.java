package com.bongiovannicomitini.loggingsystem.repository;

import com.bongiovannicomitini.loggingsystem.model.Log;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface LogRepository extends MongoRepository<Log, ObjectId> {

    List<Log> findByKey(String key);

    @Query("{ 'key' : ?0, 'value.service' : ?1}")
    List<Log> findByService(String key, String service);

    //search logs through key with timestamp in range unixTimestampStart and unixTimestampEnd
    @Query("{ 'key': ?0, 'value.timestamp' : { $gte: ?1, $lte: ?2 } }")
    List<Log> findLogsByKeyAndValue(String key, long startTs, long endTs);

    //search logs through key and service with timestamp in range unixTimestampStart and unixTimestampEnd
    @Query("{ 'key': ?0, 'value.service' : ?1, 'value.timestamp' : { $gte: ?2, $lte: ?3 } }")
    List<Log> findLogsByKeyAndValueAndService(String key, String service, long startTs, long endTs);

    //search logs through key and service with timestamp in range unixTimestampStart and unixTimestampEnd with dbStatus : down
    @Query("{ 'key': ?0, 'value.service' : ?1, 'value.timestamp' : { $gte: ?3, $lte: ?4 }, 'value.status.dbStatus' : ?2}")
    List<Log> findLogsByKeyAndValueAndServiceAndDbStatus(String key, String service, String dbStatus, long startTs, long endTs);

    //search logs through key and service with timestamp in range unixTimestampStart and unixTimestampEnd with serviceStatus : down
    @Query("{ 'key': ?0, 'value.service' : ?1, 'value.timestamp' : { $gte: ?3, $lte: ?4 }, 'value.status.serviceStatus' : ?2}")
    List<Log> findLogsByKeyAndValueAndServiceAndServiceStatus(String key, String service, String serviceStatus, long startTs, long endTs);

    //search logs through key and service with timestamp in range unixTimestampStart and unixTimestampEnd with serverUnavailable not null
    @Query("{ 'key': ?0, 'value.service' : ?1, 'value.timestamp' : { $gte: ?2, $lte: ?3 }, 'value.status.serverUnavailable' : {$ne:null}}")
    List<Log> findLogsByKeyAndValueAndServiceAndServerStatus(String key, String service, long startTs, long endTs);
}

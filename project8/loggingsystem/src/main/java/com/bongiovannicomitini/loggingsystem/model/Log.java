package com.bongiovannicomitini.loggingsystem.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Map;

@Document(collection = "logs")
public class Log implements Serializable {
    /*
        _id : ObjectId
        key : String
        value = Map<String, Object>
    */
    @Id
    private ObjectId _id;

    private String key;

    private Map<String, Object> value;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Map<String, Object> getValue() {
        return value;
    }

    public void setValue(Map<String, Object> value) {
        this.value = value;
    }

    @JsonCreator
    public Log(String key, Map<String, Object> value) {
        this.key = key;
        this.value = value;
    }

    @JsonCreator
    public Log() {
    }

}
package infotech.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "OBJECTS")
public class DataBaseObject {
    @Id
    @Column(unique = true)
    private String key;
    @Column
    private String attributes;
    @Column
    private Integer ttl;

    public static class Builder{
        private DataBaseObject dbObject;

        public Builder(){
            dbObject = new DataBaseObject();
        }

        public Builder withKey(String key){
            dbObject.setKey(key);
            return this;
        }

        public Builder withAttributes(String attributes){
            dbObject.setAttributes(attributes);
            return this;
        }
    }
}

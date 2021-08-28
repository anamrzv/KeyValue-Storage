package infotech.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "OBJECTS")
public class DataBaseObject {
    @Id
    @Column(unique = true)
    private String key;
    @Column
    private String attributes;
    @Column
    private LocalTime ttl;
    @Column(name = "creation_datetime")
    private LocalDateTime creationDateTime;
    @Column(name = "delete_datetime")
    private LocalDateTime deleteDateTime;

    public static class Builder {
        private DataBaseObject dbObject;

        public Builder() {
            dbObject = new DataBaseObject();
        }

        public Builder withKey(String key) {
            dbObject.setKey(key);
            return this;
        }

        public Builder withAttributes(String attributes) {
            dbObject.setAttributes(attributes);
            return this;
        }

        public Builder withTTL(String time) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            dbObject.setTtl(LocalTime.parse(time, formatter));
            return this;
        }

        public Builder withCreationTime(LocalDateTime date) {
            dbObject.setCreationDateTime(date);
            return this;
        }

        public Builder withDeleteTime() {
            LocalDateTime creation = dbObject.getCreationDateTime();
            LocalTime timeToLive = dbObject.getTtl();
            LocalDateTime deleteTime = creation.plusMinutes(timeToLive.getMinute()).plusHours(timeToLive.getHour());
            dbObject.setDeleteDateTime(deleteTime);
            return this;
        }

        public DataBaseObject build() {
            return dbObject;
        }
    }

    @JsonIgnore
    public String getDeleteDateTimeAsString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy числа в HH:mm");
        return deleteDateTime.format(formatter);
    }

}

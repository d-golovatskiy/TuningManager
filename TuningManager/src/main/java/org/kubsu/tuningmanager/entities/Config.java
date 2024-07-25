package org.kubsu.tuningmanager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Timestamp;


@Entity
@Getter
@Table(name = "Configs")
public class Config  {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name="param")
    String param;


    @Column(name="value")
    String value;

    @Column(name = "date")
    Timestamp date;


    @JsonIgnore
    @ManyToOne(optional = false, targetEntity = Sys.class)
    @JoinColumn(name = "sys_id", referencedColumnName = "id", insertable=false, updatable=false )
    Sys sys;

    public Config() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }
}

package org.kubsu.tuning.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "ConfigureTasks")
public class ConfigureTask {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @Column(name = "forDate")
    Timestamp date;


    @JsonIgnore
    @ManyToOne(optional = false, targetEntity = SysMeas.class)
    @JoinColumn(name = "sys_meas_id", referencedColumnName = "id", insertable=false, updatable=false )
    Sys sys;

    public ConfigureTask() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }
}

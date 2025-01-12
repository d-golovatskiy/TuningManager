package org.kubsu.tuning.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name= "tasks")
public class TaskToCollect {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name")
    String name;

    @Column(name="sys_meas_id")
    Long sysMeasId;

    @Column(name="status")
    String status;

    @Column(name= "use_affects_scheme")
    boolean useAffectsScheme;

    @Column(name = "date_start")
    Timestamp dateStart;

    @Column(name="date_end")
    Timestamp dateEnd;

    @Column(name = "data_source")
    String dataSource;

    @Column(name = "collecting_status")
    String collectingStatus;

    @ManyToOne(optional = false, targetEntity = SysMeas.class)
    @JoinColumn(name = "sys_meas_id", referencedColumnName = "id", insertable=false, updatable=false )
    SysMeas sysMeas;

    @OneToMany(mappedBy = "taskToCollect")
    private List<AffectException> affectExceptions;




}

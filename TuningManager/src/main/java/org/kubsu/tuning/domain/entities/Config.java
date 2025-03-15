package org.kubsu.tuning.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@Table(name = "configs")
@NoArgsConstructor
public class Config  {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name="description")
    String description;

    @Column(name="creation_timestamp")
    Timestamp creationTimestamp;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "sys_id", referencedColumnName = "id")
    Sys sys;

    @OneToMany(mappedBy = "config", cascade = CascadeType.ALL)
    List<ParamAndValue> params = new ArrayList<>();
}

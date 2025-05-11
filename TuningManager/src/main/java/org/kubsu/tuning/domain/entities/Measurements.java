package org.kubsu.tuning.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.influxdb.annotations.Measurement;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "measurements")
public class Measurements {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name")
    String name;

    @Column(name="description")
    String description;

    @Column(name = "field_name")
    String fieldName;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @OneToMany(mappedBy = "measurements", cascade = CascadeType.ALL)
    private List<MeasAndInfluxTagValues> measAndInfluxTagValues = new ArrayList<>();
}

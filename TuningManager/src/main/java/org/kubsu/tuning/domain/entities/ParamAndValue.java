package org.kubsu.tuning.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "params_and_values")
@NoArgsConstructor
@Data
public class ParamAndValue {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name="name")
    String name;

    @Column(name = "value")
    String value;

    @Column(name = "creation_timestamp")
    Timestamp creationTimestamp;

    @Column(name = "min_value")
    Double minValue;

    @Column(name = "max_value")
    Double maxValue;

    @Column(name = "step")
    Double step;

    @ToString.Exclude
    @JsonProperty( value = "config", access = JsonProperty.Access.WRITE_ONLY)
    @SerializedName("config")
    @ManyToOne
    @JoinColumn(name = "config_id", referencedColumnName = "id")
    Config config;

    @OneToMany(mappedBy = "param")
    List<ConfigChangeLog> configChangeLogs;
}

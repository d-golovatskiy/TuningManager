package org.kubsu.tuning.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kubsu.tuning.domain.TaskToConfigure;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "requests_to_configure")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestToConfigure {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty( value = "config", access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "system_id", referencedColumnName = "id")
    private Sys sys;

    @Column(name = "configuring_period_start_date")
    private Timestamp configuringPeriodStartDate;

    @Column(name = "configuring_period_end_date")
    private Timestamp configuringPeriodEndDate;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "requestToConfigure", cascade = CascadeType.ALL)
    private List<ConfigureRequestMeasurements> configureRequestMeasurements = new ArrayList<>();

    @Transient
    private TaskToConfigure taskToConfigure;
}

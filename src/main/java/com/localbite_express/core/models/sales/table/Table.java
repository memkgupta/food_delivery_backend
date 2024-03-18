package com.localbite_express.core.models.sales.table;

import com.fasterxml.jackson.annotation.JsonFilter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "_table")
@JsonFilter("tableFilter")
public class Table {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private TableStatus status;
    @OneToMany(mappedBy = "table", cascade = CascadeType.ALL)
    private List<TableOrder> orders;
}
enum TableStatus {
    AVAILABLE,
    OCCUPIED,
    RESERVED,
    OUT_OF_SERVICE
}
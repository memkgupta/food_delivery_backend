package com.localbite_express.core.models.sales.table;

import com.fasterxml.jackson.annotation.JsonFilter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonFilter("tableOrderFilter")
public class TableOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "table_id")
    private Table table;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<TableOrderItem> orderItems;
    @Column(name = "order_start_time")
    private LocalDateTime orderStartTime;
    @Column(name = "order_finish_time")
    private LocalDateTime orderFinishTime;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

}
 enum OrderStatus {
    OPEN,
    IN_PROGRESS,
    COMPLETED,
    CANCELED
}

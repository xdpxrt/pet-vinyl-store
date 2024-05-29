package ru.xdpxrt.vinyl.order.item.model;

import jakarta.persistence.*;
import lombok.*;
import ru.xdpxrt.vinyl.order.model.Order;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "order_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;
    @Column(nullable = false, name = "unit_id")
    private Long recordId;
    @Column(nullable = false)
    private Integer quantity;
}
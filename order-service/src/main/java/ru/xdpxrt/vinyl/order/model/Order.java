package ru.xdpxrt.vinyl.order.model;

import jakarta.persistence.*;
import lombok.*;
import ru.xdpxrt.vinyl.cons.OrderStatus;
import ru.xdpxrt.vinyl.order.item.model.Item;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime created;
    @Column(nullable = false, name = "customer_id")
    private Long customerId;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @OneToMany(mappedBy = "order")
    private List<Item> items;
}
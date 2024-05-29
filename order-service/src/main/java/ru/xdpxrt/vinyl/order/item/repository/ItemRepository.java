package ru.xdpxrt.vinyl.order.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.xdpxrt.vinyl.order.item.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
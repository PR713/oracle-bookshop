package org.example.bookshop.repository;

import org.example.bookshop.entity.GameDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameDetailRepository extends JpaRepository<GameDetail, Long> {
}

package com.davifs92.weigthtracker.repository;

import com.davifs92.weigthtracker.entities.Weight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeightRepository extends JpaRepository<Weight, Long> {
}

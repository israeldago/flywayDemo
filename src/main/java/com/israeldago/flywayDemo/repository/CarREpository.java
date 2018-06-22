package com.israeldago.flywayDemo.repository;

import com.israeldago.flywayDemo.entities.CarBIS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CarREpository extends JpaRepository<CarBIS, Integer> {
}

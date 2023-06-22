package com.orange.repository;

import com.orange.model.Cliper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CliperRepository extends JpaRepository<Cliper, Long> {

    public Cliper findByIdentifier(String identifier);

}

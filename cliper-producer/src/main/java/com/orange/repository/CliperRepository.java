package com.orange.repository;

import com.orange.model.Cliper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan("com.orange.model.*")
@Repository
public interface CliperRepository extends JpaRepository<Cliper, Long> {

    public Cliper findByIdentifier(String identifier);

}

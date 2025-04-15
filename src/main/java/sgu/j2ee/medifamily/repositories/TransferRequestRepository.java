package sgu.j2ee.medifamily.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import sgu.j2ee.medifamily.entities.TransferRequest;

public interface TransferRequestRepository extends JpaRepository<TransferRequest, Long> {
}

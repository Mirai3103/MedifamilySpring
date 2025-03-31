package sgu.j2ee.medifamily.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import sgu.j2ee.medifamily.entities.FileDocument;

public interface FileRepository extends JpaRepository<FileDocument, UUID> {
}
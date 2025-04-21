package sgu.j2ee.medifamily.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import sgu.j2ee.medifamily.entities.ProfileDocument;

@Repository
public interface ProfileDocumentRepository
		extends JpaRepository<ProfileDocument, Long>, JpaSpecificationExecutor<ProfileDocument> {
	List<ProfileDocument> findByProfileId(Long profileId);
}

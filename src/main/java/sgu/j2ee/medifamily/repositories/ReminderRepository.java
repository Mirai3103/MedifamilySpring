package sgu.j2ee.medifamily.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sgu.j2ee.medifamily.entities.Reminder;

@RepositoryRestResource(collectionResourceRel = "reminders", path = "reminders")
public interface ReminderRepository extends PagingAndSortingRepository<Reminder, Long> {
}

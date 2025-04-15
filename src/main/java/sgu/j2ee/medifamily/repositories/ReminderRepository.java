package sgu.j2ee.medifamily.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import sgu.j2ee.medifamily.entities.Reminder;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
}

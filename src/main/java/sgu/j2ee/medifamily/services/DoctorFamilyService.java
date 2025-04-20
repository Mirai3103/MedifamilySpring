package sgu.j2ee.medifamily.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sgu.j2ee.medifamily.repositories.FamilyDoctorRepository;

@Service
@RequiredArgsConstructor
public class DoctorFamilyService {
    private final FamilyDoctorRepository familyDoctorRepository;
}

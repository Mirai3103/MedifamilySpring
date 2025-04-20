package sgu.j2ee.medifamily.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sgu.j2ee.medifamily.services.DoctorFamilyService;

@RestController
@RequestMapping("/api/doctor-family")
@RequiredArgsConstructor
public class DoctorFamilyController {
    private final DoctorFamilyService familyService;
}

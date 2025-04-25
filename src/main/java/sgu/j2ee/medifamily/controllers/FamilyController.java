package sgu.j2ee.medifamily.controllers;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.dtos.FamilyDoctorDto;
import sgu.j2ee.medifamily.dtos.family.CreateFamilyRequest;
import sgu.j2ee.medifamily.dtos.family.FamilyDTO;
import sgu.j2ee.medifamily.entities.FamilyDoctor;
import sgu.j2ee.medifamily.exceptions.UnAuthorizedException;
import sgu.j2ee.medifamily.mappers.FamilyDoctorMapper;
import sgu.j2ee.medifamily.mappers.IFamilyMapper;
import sgu.j2ee.medifamily.services.DoctorFamilyService;
import sgu.j2ee.medifamily.services.FamilyService;
import sgu.j2ee.medifamily.services.UserDetailsServiceImpl;

@RestController
@RequestMapping("/api/family")
@RequiredArgsConstructor
public class FamilyController {
    private final FamilyService familyService;
    private final AuditorAware<String> auditorAware;
    private final IFamilyMapper familyMapper;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final DoctorFamilyService doctorFamilyService;
    private final FamilyDoctorMapper familyDoctorMapper;

    @GetMapping(path = "/{id:[0-9]+}")
    public ResponseEntity<FamilyDTO> getFamily(@PathVariable Long id) {
        var family = familyService.getFamilyById(id);
        family.getOwner().setFamilyMembers(null);
        family.getOwner().setUser(null);
        return ResponseEntity.ok(familyMapper.toDTO(family));
    }

    @GetMapping("/@me")
    public ResponseEntity<List<FamilyDTO>> getMyFamily() {
        var currentUserId = auditorAware.getCurrentAuditor().orElseThrow();
        var family = familyService.getFamiliesByUserId(Long.parseLong(currentUserId));
        return ResponseEntity.ok(familyMapper.familiesToFamilyDTOs(family));
    }

    @PostMapping("")
    public ResponseEntity<FamilyDTO> createFamily(@RequestBody CreateFamilyRequest family) {
        var currentUserId = auditorAware.getCurrentAuditor().orElseThrow(UnAuthorizedException::new);
        family.setCreatedBy(Long.parseLong(currentUserId));
        return ResponseEntity.ok(familyMapper.toDTO(familyService.createFamily(family)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FamilyDTO> updateFamily(@RequestBody CreateFamilyRequest family, @PathVariable String id) {
        return ResponseEntity.ok(familyMapper.toDTO(familyService.updateFamily(Long.parseLong(id), family)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFamily(@PathVariable String id) {
        familyService.deleteFamily(Long.parseLong(id));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/managed/@me")
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<PagedModel<FamilyDoctorDto>> getManagedFamilies(@RequestParam(required = false, defaultValue = "") FamilyDoctor.Status status, @RequestParam(required = false) @ParameterObject @PageableDefault(size = 10) Pageable pageable) {
        if (pageable == null) {
            pageable = Pageable.unpaged();
        }
        var currentDoctorId = userDetailsServiceImpl.getCurrentUser().getDoctor().getId();
        var list = doctorFamilyService.getFamiliesByDoctor(currentDoctorId, status, pageable);
        return ResponseEntity.ok(new PagedModel<>(list.map(familyDoctorMapper::toDto
        )));
    }
}

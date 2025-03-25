package sgu.j2ee.medifamily.controllers;

import java.util.List;

import org.springframework.data.domain.AuditorAware;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.dtos.CreateFamilyRequest;
import sgu.j2ee.medifamily.entities.Family;
import sgu.j2ee.medifamily.services.FamilyService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/family")
@RequiredArgsConstructor
public class FamilyController {
    private final FamilyService familyService;
        private final AuditorAware<String> auditorAware;


    @GetMapping("/{id}")
    public ResponseEntity<Family> getFamily(@RequestParam Long id) {
        return ResponseEntity.ok(familyService.getFamilyById(id));
    }

    @GetMapping("/me")
    public ResponseEntity<List<Family>> getMyFamily() {
        var currentUserId =auditorAware.getCurrentAuditor().orElseThrow();
        return ResponseEntity.ok(familyService.getFamiliesByUserId(Long.parseLong(currentUserId)));
    }

    @PostMapping("")
    public ResponseEntity<Family> createFamily(@RequestBody CreateFamilyRequest family) {
        return ResponseEntity.ok(familyService.createFamily(family));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Family> updateFamily(@RequestBody Family family) {
        return ResponseEntity.ok(familyService.updateFamily(family));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFamily(@RequestParam Long id) {
        familyService.deleteFamily(id);
        return ResponseEntity.ok().build();
    }
}

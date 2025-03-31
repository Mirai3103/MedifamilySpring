package sgu.j2ee.medifamily.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.dtos.family.AddMemberToFamilyRequest;
import sgu.j2ee.medifamily.entities.FamilyMember;
import sgu.j2ee.medifamily.services.FamilyMemberService;

@RestController
@RequestMapping("/api/family/{id}/members")
@RequiredArgsConstructor
public class FamilyMemberController {
	private final FamilyMemberService familyMemberService;

	@GetMapping("")
	public ResponseEntity<List<FamilyMember>> getMembersByFamilyId(
			@PathVariable(name = "id") Long familyId) {
		return ResponseEntity.ok(familyMemberService.getMembersByFamilyId(familyId));
	}

	@PostMapping("")
	public ResponseEntity<FamilyMember> addMemberToFamily(
			@RequestBody @Valid AddMemberToFamilyRequest familyMember,
			@PathVariable(name = "id") Long id) {
		familyMember.setFamilyId(id);
		return ResponseEntity.ok(familyMemberService.addMemberToFamily(familyMember));
	}
}

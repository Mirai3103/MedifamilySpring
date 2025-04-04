package sgu.j2ee.medifamily.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.dtos.family.AddMemberToFamilyRequest;
import sgu.j2ee.medifamily.dtos.family.FamilyMemberDTO;
import sgu.j2ee.medifamily.mappers.IFamilyMemberMapper;
import sgu.j2ee.medifamily.services.FamilyMemberService;

@RestController
@RequestMapping("/api/family/{id}/members")
@RequiredArgsConstructor
public class FamilyMemberController {
	private final FamilyMemberService familyMemberService;
	private final IFamilyMemberMapper familyMemberMapper;

	@GetMapping("")
	public ResponseEntity<List<FamilyMemberDTO>> getMembersByFamilyId(
			@PathVariable(name = "id") Long familyId) {
		return ResponseEntity.ok(
				familyMemberMapper.toDTOs(familyMemberService.getMembersByFamilyId(familyId)));

	}

	@PostMapping("")
	public ResponseEntity<FamilyMemberDTO> addMemberToFamily(
			@RequestBody @Valid AddMemberToFamilyRequest familyMember,
			@PathVariable(name = "id") Long id) {
		familyMember.setFamilyId(id);
		return ResponseEntity.ok(familyMemberMapper.toDTO(familyMemberService.addMemberToFamily(familyMember)));
	}

	@DeleteMapping("/{memberId}")
	public ResponseEntity<Void> deleteMemberFromFamily(
			@PathVariable(name = "id") Long familyId,
			@PathVariable(name = "memberId") Long memberId) {
		familyMemberService.removeMemberFromFamily(familyId, memberId);
		return ResponseEntity.ok().build();
	}

	@GetMapping("{memberId}")
public ResponseEntity<FamilyMemberDTO> getFamilyMemberById(@PathVariable(name = "id") Long id,
			@PathVariable(name = "memberId") Long memberId) {
		return ResponseEntity.ok(familyMemberMapper.toDTO(familyMemberService.getFamilyMemberById(id, memberId)));
	}

}

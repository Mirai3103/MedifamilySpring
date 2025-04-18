package sgu.j2ee.medifamily.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import sgu.j2ee.medifamily.dtos.VaccinationDto;
import sgu.j2ee.medifamily.dtos.VaccinationFilterDTO;
import sgu.j2ee.medifamily.dtos.VaccinationSpecs;
import sgu.j2ee.medifamily.entities.Vaccination;
import sgu.j2ee.medifamily.mappers.VaccinationMapper;
import sgu.j2ee.medifamily.repositories.FamilyRepository;
import sgu.j2ee.medifamily.repositories.ProfileRepository;
import sgu.j2ee.medifamily.repositories.VaccinationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VaccinationService {
    private final VaccinationRepository vaccinationRepository;
    private final VaccinationMapper vaccinationMapper;
    private final FamilyRepository familyRepository;
    private final ProfileRepository profileRepository;
    private final VaccinationMapper mapper;

    public Page<VaccinationDto> filterVaccinations(VaccinationFilterDTO filter, Pageable pageable) {
        Specification<Vaccination> spec = Specification.where(null);

        if (filter.getProfileId() != null) {
            spec = spec.and(VaccinationSpecs.byProfileId(filter.getProfileId()));
        }

        if (filter.getKeyword() != null && !filter.getKeyword().isBlank()) {
            spec = spec.and(VaccinationSpecs.keywordLike(filter.getKeyword()));
        }

        if (filter.getFromDate() != null && filter.getToDate() != null) {
            spec = spec.and(VaccinationSpecs.vaccinationBetween(filter.getFromDate(), filter.getToDate()));
        }

        // xử lý sort
        Sort sort = Sort.by(Sort.Direction.fromString(filter.getSortDirection()), filter.getSortBy());
        Pageable finalPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        var page = vaccinationRepository.findAll(spec, finalPageable);
        return page.map(vaccinationMapper::toDto);
    }

    public VaccinationDto getVaccination(Long id) {
        return vaccinationRepository.findById(id)
                .map(vaccinationMapper::toDto)
                .orElse(null);
    }

    public VaccinationDto createVaccination(VaccinationDto vaccination) {
        var entity = vaccinationMapper.toEntity(vaccination);
        var profile = profileRepository.findById(vaccination.getProfileId());
        if (profile.isEmpty()) {
            return null;
        }
        entity.setProfile(profile.get());
        return vaccinationMapper.toDto(vaccinationRepository.save(entity));
    }

    public VaccinationDto updateVaccination(Long id, VaccinationDto vaccination) {
        var existingVaccination = vaccinationRepository.findById(id);
        if (existingVaccination.isEmpty()) {
            return null;
        }
        vaccinationMapper.partialUpdate(vaccination, existingVaccination.get());
        return vaccinationMapper.toDto(vaccinationRepository.save(existingVaccination.get()));
    }

    @Transactional

    public void deleteVaccination(Long id) {
        var vaccination = vaccinationRepository.findById(id);
        if (vaccination.isEmpty()) {
            return;
        }
        vaccinationRepository.delete(vaccination.get());
    }

}

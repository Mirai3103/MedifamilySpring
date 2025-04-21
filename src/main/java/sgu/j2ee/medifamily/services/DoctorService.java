package sgu.j2ee.medifamily.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.dtos.DoctorSearchRequest;
import sgu.j2ee.medifamily.dtos.DoctorSpecification;
import sgu.j2ee.medifamily.dtos.user.DoctorDTO;
import sgu.j2ee.medifamily.entities.Doctor;
import sgu.j2ee.medifamily.exceptions.NotFoundException;
import sgu.j2ee.medifamily.mappers.IDoctorMapper;
import sgu.j2ee.medifamily.repositories.DoctorRepository;

@Service
@RequiredArgsConstructor
public class DoctorService {
	private final DoctorRepository doctorRepository;
	private final IDoctorMapper doctorMapper;

	public Page<DoctorDTO> searchWithFilter(DoctorSearchRequest request, Pageable pageable) {
		Page<Doctor> result = doctorRepository.findAll(DoctorSpecification.withFilters(request), pageable);
		return result.map(doctorMapper::toDTO);
	}

	public DoctorDTO getOne(Long id) {
		Doctor doctor = doctorRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Không tìm thấy bác sĩ với id: " + id));
		return doctorMapper.toDTO(doctor);
	}

	public DoctorDTO create(DoctorDTO doctorDTO) {
		Doctor doctor = doctorMapper.doctorDTOToDoctor(doctorDTO);
		Doctor saved = doctorRepository.save(doctor);
		return doctorMapper.toDTO(saved);
	}

	public DoctorDTO updatePartial(Long id, DoctorDTO doctorDTO) {
		Doctor doctor = doctorRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Không tìm thấy bác sĩ với id: " + id));

		doctorMapper.partialUpdate(doctorDTO, doctor);

		Doctor updated = doctorRepository.save(doctor);
		return doctorMapper.toDTO(updated);
	}

}
